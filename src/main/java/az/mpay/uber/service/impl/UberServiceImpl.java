package az.mpay.uber.service.impl;

import az.mpay.uber.client.MpayClient;
import az.mpay.uber.client.UberClient;
import az.mpay.uber.exception.CoinNotFoundException;
import az.mpay.uber.exception.InsufficientBalanceException;
import az.mpay.uber.exception.MultipleAccountsException;
import az.mpay.uber.exception.ParkAlreadyExistsException;
import az.mpay.uber.exception.ProfileNotFoundException;
import az.mpay.uber.exception.TransactionDeclinedException;
import az.mpay.uber.exception.UnauthorizedException;
import az.mpay.uber.exception.UserNotFoundException;
import az.mpay.uber.exception.ValidationException;
import az.mpay.uber.model.dto.client.CoinDto;
import az.mpay.uber.model.dto.client.ContactDto;
import az.mpay.uber.model.dto.client.QueryDto;
import az.mpay.uber.model.dto.client.UberDriverProfileDto;
import az.mpay.uber.model.dto.client.UserDto;
import az.mpay.uber.model.entity.DriverProfile;
import az.mpay.uber.model.entity.Park;
import az.mpay.uber.model.entity.TransactionLog;
import az.mpay.uber.model.mapper.UberMapper;
import az.mpay.uber.model.request.client.CoinTransferReq;
import az.mpay.uber.model.request.client.CreateTransactionReq;
import az.mpay.uber.model.request.client.DriverProfilesReq;
import az.mpay.uber.model.request.server.CreateParkRequest;
import az.mpay.uber.model.request.server.RedeemRequest;
import az.mpay.uber.model.request.server.UpdateProfileRequest;
import az.mpay.uber.model.response.client.DriverProfilesResp;
import az.mpay.uber.model.response.client.UserInfoResp;
import az.mpay.uber.model.response.server.BaseResponse;
import az.mpay.uber.model.response.server.CheckUserResp;
import az.mpay.uber.model.response.server.DriverProfileResp;
import az.mpay.uber.repository.DriverProfileRepo;
import az.mpay.uber.repository.ParkRepo;
import az.mpay.uber.repository.TransactionLogRepo;
import az.mpay.uber.service.UberService;
import az.mpay.uber.util.DurationUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static az.mpay.uber.constant.Constant.AZN;
import static az.mpay.uber.constant.Constant.COIN_NOT_FOUND;
import static az.mpay.uber.constant.Constant.MPAY_TOKEN_PREFIX;
import static az.mpay.uber.constant.Constant.USER_NOT_FOUND;
import static az.mpay.uber.constant.Constant.code;
import static az.mpay.uber.constant.Constant.partner_service_manual;
import static az.mpay.uber.constant.enums.Status.created;
import static az.mpay.uber.constant.enums.Status.declined;
import static az.mpay.uber.constant.enums.Status.exception;
import static az.mpay.uber.constant.enums.Status.processed;
import static az.mpay.uber.constant.enums.Status.processing;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
public class UberServiceImpl implements UberService {

    @Value("${transaction-log.processing-interval}")
    private String processingInterval;

    @Value("${mpay.token}")
    private String mpayToken;

    @Value("${mpay.gate-serial}")
    private String gateSerial;

    @Value("${mpay.gate-token}")
    private String gateToken;

    @Value("${mpay.park-token}")
    private String parkToken;

//    @Value("${mpay.bank-details.iban}")
//    private String iban;
//
//    @Value("${mpay.bank-details.bic}")
//    private String bic;
//
//    @Value("${mpay.bank-details.swift}")
//    private String swift;

    private final ParkRepo parkRepo;
    private final UberMapper mapper;
    private final MpayClient mpayClient;
    private final UberClient uberClient;
    private final DriverProfileRepo profileRepo;
    private final TransactionLogRepo logRepo;


    @Override
    public BaseResponse createPark(String token, CreateParkRequest request) {
        log.info("Create park is called. Request: {}", request);

        checkMpayToken(token);

        Optional<Park> parkOpt = parkRepo.findById(request.getId());
        if (parkOpt.isPresent()) {
            throw new ParkAlreadyExistsException("Park '%s' already exists", request.getId());
        }

        parkRepo.save(mapper.fromRequest(request));

        return BaseResponse.success();
    }

    @Override
    public DriverProfileResp updateProfile(String token, String profileId, UpdateProfileRequest request) {
        log.info("Update profile is called ...");

        checkParkToken(token);

        Optional<DriverProfile> profileOpt = profileRepo.findById(profileId);
        if (profileOpt.isEmpty()) {
            throw new ProfileNotFoundException("Driver profile '%s' not found", profileId);
        }

        DriverProfile profile = profileOpt.get();
        if (isEmpty(profile.getPhones())) {
            profile.setPhones(new ArrayList<>());
        }

        profile.getPhones().add(request.getPhoneNumber());

        return DriverProfileResp.of(mapper.toDto(profileRepo.save(profile)));
    }

    @Override
    public DriverProfileResp getProfile(String token, String profileId) {
        log.info("Get profile is called ...");

        checkParkToken(token);

        Optional<DriverProfile> profileOpt = profileRepo.findById(profileId);
        if (profileOpt.isEmpty()) {
            throw new ProfileNotFoundException("Driver profile '%s' not found", profileId);
        }

        return DriverProfileResp.of(mapper.toDto(profileOpt.get()));
    }

    @Override
    public CheckUserResp checkUser(String token) {

        log.info("Check user is called.");

        getUberProfile(getUserPhone(token));

        return CheckUserResp.of(true);
    }

    private DriverProfile getUberProfile(String phoneNumber) {

//        phoneNumber = "994508592465";
        List<DriverProfile> profiles = profileRepo.findAllByPhonesContains("+".concat(phoneNumber));
        if (isEmpty(profiles)) {
            throw new UserNotFoundException("Uber user '%s' not found", "+".concat(phoneNumber));
        } else if (profiles.size() > 1) {
            throw new MultipleAccountsException("Uber user '%s' multiple profiles conflict", phoneNumber);
        }

        return profiles.get(0);
    }

    @Override
    public BaseResponse redeem(String token, RedeemRequest request) {

        log.info("Redeem is called. Request: {}", request);

        try {
//            long start = System.currentTimeMillis();

//            log.info("Getting MPAY user phone number ...");
            String phoneNumber = getUserPhone(token);
            log.info("MPAY user phone number: {}", phoneNumber);
//            String phoneNumber = "994508592465";

//            long end = System.currentTimeMillis();
//            log.info("Elapsed time 1 in milli seconds: " + (end-start));

            TransactionLog transactionLog = logRepo.save(TransactionLog.builder()
                                                                       .phoneNumber(phoneNumber)
                                                                       .amount(request.getAmount())
                                                                       .status(created)
                                                                       .redeemed(false)
                                                                       .build());
//            end = System.currentTimeMillis();
//            log.info("Elapsed time 2 in milli seconds: " + (end-start));

            Duration interval = DurationUtils.parse(processingInterval, "PT2M");
//            log.info("Updating transaction log status ...");
            int rowCount = logRepo.updateStatus(phoneNumber,
                                                declined,
                                                OffsetDateTime.now().minus(interval),
                                                declined,
                                                processing,
                                                transactionLog.getId());
            log.info("Updated transaction log status row count: {}", rowCount);

//            end = System.currentTimeMillis();
//            log.info("Elapsed time 3 in milli seconds: " + (end-start));

            TransactionLog finalLog = logRepo.getReferenceById(transactionLog.getId());
//            TransactionLog finalLog = logRepo.findById(transactionLog.getId()).orElseThrow(
//                    () -> new TransactionLogNotFoundException(
//                            "Uber: transaction log '%s' not found", String.valueOf(transactionLog.getId())
//                    )
//            );

            if (finalLog.getStatus() == declined) {
                finalLog.setErrorDetails("Transaction declined due to concurrent processing");
                logRepo.save(finalLog);
                throw new TransactionDeclinedException("Uber: transaction declined due to concurrent processing");
            }

            //Step 1: Get Uber user profile
//            log.info("Getting Uber user profile from db ...");
            DriverProfile profile = getUberProfile(phoneNumber);

//            end = System.currentTimeMillis();
//            log.info("Elapsed time 4 in milli seconds: " + (end-start));

//            log.info("Getting Uber user profile from service ...");
            DriverProfilesResp driverProfilesResp = uberClient.getDriverProfiles(
                    profile.getPark().getClientId(),
                    profile.getPark().getApiKey(),
                    DriverProfilesReq.builder()
                            .query(QueryDto.create(profile.getId(), profile.getPark().getId()))
                            .build()
            );

//            end = System.currentTimeMillis();
//            log.info("Elapsed time 5 in milli seconds: " + (end-start));

            //Step 2: Check Uber user balance
//            log.info("Checking Uber user balance ...");
            UberDriverProfileDto profileDto = driverProfilesResp.getProfiles().get(0);
            if (request.getAmount().compareTo(profileDto.getAccounts().get(0).getBalance()) > 0) {
                finalLog.setErrorDetails("Uber balance is insufficient");
                finalLog.setStatus(declined);
                logRepo.save(finalLog);
                throw new InsufficientBalanceException("Uber balance is insufficient");
            }

            //Step 3: Get Uber park user MPAY coin
//            log.info("Getting Uber park user MPAY coin ...");
            CoinDto parkCoin =
                    mpayClient.getCoins(MPAY_TOKEN_PREFIX.concat(parkToken))
                            .getCoins()
                            .stream()
                            .filter(coin -> AZN.equals(coin.getIssuer().getCurrency()))
                            .findFirst()
                            .orElseThrow(() -> {
                                finalLog.setErrorDetails("Park user MPAY coin not found");
                                finalLog.setStatus(exception);
                                logRepo.save(finalLog);
                                return new CoinNotFoundException("Uber: Park user MPAY coin not found");
                            });

            //Step 4: Check Uber park user coin balance
//            log.info("Checking Uber park user coin balance ...");
            if (request.getAmount().compareTo(parkCoin.getAvailableAmount()) > 0) {
                finalLog.setErrorDetails("Park user balance is insufficient");
                finalLog.setStatus(declined);
                logRepo.save(finalLog);
                throw new InsufficientBalanceException("Uber: Park user balance is insufficient");
            }

            //Step 5: Make transfer from park coin to gate coin
//            log.info("Making transfer from park coin '{}' to gate coin '{}' ...", parkCoin.getSerial(), gateSerial);
            mpayClient.makeTransfer(
                    MPAY_TOKEN_PREFIX.concat(gateToken),
                    CoinTransferReq.builder()
                            .senderCoin(parkCoin.getSerial())
                            .recipientCoin(gateSerial)
                            .amount(request.getAmount())
                            .description(
                                    String.format(
                                            "Redeem Uber user '%s' balance, amount: %s AZN",
                                            phoneNumber,
                                            request.getAmount()
                                    )
                            )
                            .build()
            );

            //Step 6: Create transaction on Uber user account
            String amount = String.valueOf(request.getAmount().multiply(new BigDecimal(-1)));
            String description = String.format("Redeem from driver '%s' balance", profile.getId());
            String idempotencyToken = UUID.randomUUID().toString().replace("-", "");
            log.info("Creating transaction on Uber user account ...");
            uberClient.createTransaction(profile.getPark().getClientId(),
                    profile.getPark().getApiKey(),
                    idempotencyToken,
                    CreateTransactionReq.builder()
                            .amount(amount)
                            .categoryId(partner_service_manual)
                            .description(description)
                            .driverProfileId(profile.getId())
                            .parkId(profile.getPark().getId())
                            .build());

            finalLog.setRedeemed(true);

//            end = System.currentTimeMillis();
//            log.info("Elapsed time 6 in milli seconds: " + (end-start));

//            //Step 3: Get MPAY user coin serial
//            log.info("Getting MPAY user coin serial ...");
//            String coinSerial =
//                    mpayClient.getCoins(MPAY_TOKEN_PREFIX.concat(token))
//                              .getCoins()
//                              .stream()
//                              .filter(coin -> AZN.equals(coin.getIssuer().getCurrency()))
//                              .map(CoinDto::getSerial)
//                              .findFirst()
//                              .orElseThrow(() -> {
//                                  finalLog.setErrorDetails("MPAY user coin not found");
//                                  finalLog.setStatus(exception);
//                                  logRepo.save(transactionLog);
//                                  return new CoinNotFoundException("Uber: MPAY user coin not found");
//                              });
//
//            end = System.currentTimeMillis();
//            log.info("Elapsed time 7 in milli seconds: " + (end-start));
//
//            //Step 4: Top-up MPAY user coin
//            try {
//                String fullName = profile.getFirstName() + " " + profile.getLastName();
//                log.info("Increasing MPAY user coin '{}' balance ...", coinSerial);
//                mpayClient.topupUserCoin(MPAY_TOKEN_PREFIX.concat(mpayToken),
//                                         CoinTopupReq.builder()
//                                                     .coin(coinSerial)
//                                                     .amount(request.getAmount())
//                                                     .bankDetails(BankDetailsDto.builder()
//                                                                                .fullName(fullName)
//                                                                                .iban(iban)
//                                                                                .bic(bic)
//                                                                                .swift(swift)
//                                                                                .build())
//                                                     .build());
//
//                end = System.currentTimeMillis();
//                log.info("Elapsed time 8 in milli seconds: " + (end-start));
//            } catch (FeignException.FeignClientException.BadRequest e) {
//                finalLog.setErrorDetails("MPAY user coin balance topup exception");
//                finalLog.setStatus(exception);
//                logRepo.save(finalLog);
//                throw e;
//            }

            finalLog.setStatus(processed);
            logRepo.save(finalLog);

            return BaseResponse.success();
        } catch (FeignException.FeignClientException.BadRequest ex) {
            Map<String, Object> exMap = new JSONObject(ex.contentUTF8()).toMap();
            if (USER_NOT_FOUND.equals(exMap.get(code))) {
                log.error("MPAY user not found");
                throw new UserNotFoundException("Uber: MPAY user not found");
            } else if (COIN_NOT_FOUND.equals(exMap.get(code))) {
                log.error("Mpay user coin not found");
                throw new CoinNotFoundException("Uber: MPAY user coin not found");
            }

            String validationTxt = "Validation failed";
            if (exMap.get(code) != null) {
                validationTxt += String.format(". Response code: %s", exMap.get(code));
            }

            log.error(validationTxt);
            throw new ValidationException("Uber: ".concat(validationTxt));
        } catch (FeignException.FeignClientException.Unauthorized ex) {
            log.error("Authorization failed");
            throw new UnauthorizedException("Uber: Authorization failed");
        }
    }

    private String getUserPhone(String token) {
        try {
            return Optional.ofNullable(mpayClient.getUser(MPAY_TOKEN_PREFIX.concat(token)))
                           .map(UserInfoResp::getUser)
                           .map(UserDto::getContact)
                           .map(ContactDto::getPhoneNumber)
                           .filter(StringUtils::isNotBlank)
                           .orElseThrow(() -> new UserNotFoundException("Uber: MPAY user not found"));
        } catch (FeignException.FeignClientException.BadRequest ex) {
            Map<String, Object> exMap = new JSONObject(ex.contentUTF8()).toMap();
            if (USER_NOT_FOUND.equals(exMap.get(code))) {
                log.error("MPAY user not found");
                throw new UserNotFoundException("Uber: MPAY user not found");
            }
            log.error("MPAY validation failed. Response code: {}", exMap.get(code));
            throw new ValidationException(
                    "Uber: MPAY validation failed. Response code: %s", String.valueOf(exMap.get(code))
            );
        } catch (FeignException.FeignClientException.Unauthorized ex) {
            log.error("Authorization failed");
            throw new UnauthorizedException("Uber: MPAY authorization failed");
        }
    }

    private void checkMpayToken(String token) {
        if (!mpayToken.equals(token)) {
            log.error("Authorization failed");
            throw new UnauthorizedException("Authorization failed");
        }
    }

    private void checkParkToken(String token) {
        if (!parkToken.equals(token)) {
            log.error("Authorization failed");
            throw new UnauthorizedException("Authorization failed");
        }
    }
}
