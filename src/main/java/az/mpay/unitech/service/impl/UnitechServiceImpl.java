package az.mpay.unitech.service.impl;

import az.mpay.unitech.exception.*;
import az.mpay.unitech.model.dto.server.LoginDto;
import az.mpay.unitech.model.dto.server.TransferDto;
import az.mpay.unitech.model.entity.*;
import az.mpay.unitech.model.mapper.UnitechMapper;
import az.mpay.unitech.model.request.server.UserRequest;
import az.mpay.unitech.model.request.server.TransferRequest;
import az.mpay.unitech.model.response.server.*;
import az.mpay.unitech.repository.*;
import az.mpay.unitech.service.UnitechService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static az.mpay.unitech.constant.enums.Status.FAILED;
import static az.mpay.unitech.constant.enums.Status.SUCCESS;

@Slf4j
@RequiredArgsConstructor
@Service
public class UnitechServiceImpl implements UnitechService {

    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final AccountRepo accountRepo;
    private final TransferRepo transferRepo;

    private final UnitechMapper mapper;

    @Override
    public RegisterUserResp registerUser(UserRequest request) {

        log.info("Register user is called. Request: {}", request);

        if (userRepo.findByPin(request.getPin()).isPresent()) {
            throw new UserAlreadyExistsException("User with pin '%s' already registered", request.getPin());
        }

        return RegisterUserResp.of(mapper.toDto(userRepo.save(mapper.fromRequest(request))));
    }

    @Override
    public LoginResp login(UserRequest request) {

        log.info("Login is called. Request: {}", request);

        if (userRepo.findByPin(request.getPin()).isEmpty()) {
            throw new UserNotFoundException("User with pin '%s' not found", request.getPin());
        }

        Token authToken = tokenRepo.save(Token.builder()
                                              .pin(request.getPin())
                                              .token(UUID.randomUUID().toString().replace("-", ""))
                                              .build());

        return LoginResp.of(LoginDto.builder()
                                    .token(authToken.getToken())
                                    .build());
    }

    @Override
    public AccountsResp getUserAccounts(Long userId) {

        log.info("Get user accounts is called. User id: {}", userId);

        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User '%s' not found", userId);
        }

        return AccountsResp.of(mapper.toDtoList(userOpt.get().getAccounts()));
    }

    @Override
    public TransferResp transfer(Long userId, List<TransferRequest> request) {

        log.info("Transfer is called. User id: {}", userId);

        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User '%s' not found", userId);
        }

        List<TransferDto> transfers = new ArrayList<>();

        request.forEach(transferRequest -> {
            Transfer transfer = mapper.fromRequest(transferRequest);

            if (transferRequest.getSenderAccountId().equals(transferRequest.getReceiverAccountId())) {
                saveErrorTransfer(
                        transfer,
                        "Same accounts. Sender account: '%s', receiver account '%s'",
                        transferRequest.getSenderAccountId(), transferRequest.getReceiverAccountId()
                );
            } else {
                Optional<Account> senderAccountOpt = userOpt.get()
                        .getAccounts()
                        .stream()
                        .filter(account -> account.getId().equals(transferRequest.getSenderAccountId()))
                        .findFirst();

                Optional<Account> receiverAccountOpt = accountRepo.findById(transferRequest.getReceiverAccountId());

                if (senderAccountOpt.isPresent()) {
                    if (receiverAccountOpt.isPresent()) {
                        if (receiverAccountOpt.get().getActive()) {
                            if (senderAccountOpt.get().getCcy().equals(receiverAccountOpt.get().getCcy())) {
                                makeTransfer(senderAccountOpt.get(),
                                        receiverAccountOpt.get(),
                                        transferRequest.getAmount(),
                                        transfer);
                            } else {
                                saveErrorTransfer(
                                        transfer,
                                        "Different currencies. Sender ccy: '%s', receiver ccy '%s'",
                                        senderAccountOpt.get().getCcy(), receiverAccountOpt.get().getCcy()
                                );
                            }
                        } else {
                            saveErrorTransfer(
                                    transfer,
                                    "Receiver account '%s' not active",
                                    transferRequest.getReceiverAccountId()
                            );
                        }
                    } else {
                        saveErrorTransfer(
                                transfer,
                                "Receiver account '%s' not found",
                                transferRequest.getReceiverAccountId()
                        );
                    }
                } else {
                    saveErrorTransfer(
                            transfer,
                            "User account '%s' not found",
                            transferRequest.getSenderAccountId()
                    );
                }
            }

            transfers.add(mapper.toDto(transferRepo.save(transfer)));
        });

        return TransferResp.of(transfers);
    }

    private void makeTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount,
                              Transfer transfer) {
        if (senderAccount.getBalance().compareTo(amount) >= 0) {
            senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
            receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
            accountRepo.save(senderAccount);
            accountRepo.save(receiverAccount);
            transfer.setStatus(SUCCESS);
        } else {
            saveErrorTransfer(
                    transfer,
                    "Sender account '%s' balance not sufficient. Balance: %s, amount: %s",
                    senderAccount.getId(), senderAccount.getBalance(), amount
            );
        }
    }

    private void saveErrorTransfer(Transfer transfer, String errorText, Object... args) {
        transfer.setStatus(FAILED);
        String errorDetails = String.format(errorText, args);
        transfer.setErrorDetails(errorDetails);
        log.warn(errorDetails);
    }
}
