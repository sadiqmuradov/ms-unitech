package az.mpay.unitech.controller;

import az.mpay.unitech.model.request.server.UserRequest;
import az.mpay.unitech.model.request.server.TransferRequest;
import az.mpay.unitech.model.request.server.UpdateProfileRequest;
import az.mpay.unitech.model.response.server.BaseResponse;
import az.mpay.unitech.model.response.server.CheckUserResp;
import az.mpay.unitech.model.response.server.DriverProfileResp;
import az.mpay.unitech.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static az.mpay.unitech.constant.URL.API_V1;
import static az.mpay.unitech.constant.URL.CHECK_USER;
import static az.mpay.unitech.constant.URL.DRIVER_PROFILES;
import static az.mpay.unitech.constant.URL.PARKS;
import static az.mpay.unitech.constant.URL.TOPUP;
import static az.mpay.unitech.constant.URL.VAR_PROFILE_ID;

@Slf4j
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping(API_V1)
public class UberController {

    private final AuthService uberService;

    @Operation(summary = "Create new park")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK - processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - processing failed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - processing failed"),
            @ApiResponse(responseCode = "409", description = "Conflict - processing failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - processing failed")
    })
    @PostMapping(PARKS)
    public BaseResponse createPark(@RequestHeader("Authorization") String token,
                                   @RequestBody @NotNull @Valid UserRequest request) {

        return uberService.createPark(token, request);
    }

    @Operation(summary = "Update driver profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK - processed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - processing failed"),
            @ApiResponse(responseCode = "404", description = "Not Found - processing failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - processing failed")
    })
    @PatchMapping(DRIVER_PROFILES + "/" + VAR_PROFILE_ID)
    public DriverProfileResp updateProfile(@RequestHeader("Authorization") String token,
                                           @PathVariable String profileId,
                                           @RequestBody @NotNull @Valid UpdateProfileRequest request) {

        return uberService.updateProfile(token, profileId, request);
    }

    @Operation(summary = "Get driver profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK - processed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - processing failed"),
            @ApiResponse(responseCode = "404", description = "Not Found - processing failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - processing failed")
    })
    @GetMapping(DRIVER_PROFILES + "/" + VAR_PROFILE_ID)
    public DriverProfileResp getProfile(@RequestHeader("Authorization") String token,
                                        @PathVariable String profileId) {

        return uberService.getProfile(token, profileId);
    }

    @Operation(summary = "Check Uber user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK - processed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - processing failed"),
            @ApiResponse(responseCode = "404", description = "Not Found - processing failed"),
            @ApiResponse(responseCode = "409", description = "Conflict - processing failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - processing failed")
    })
    @GetMapping(CHECK_USER)
    public CheckUserResp checkUser(@RequestHeader("Authorization") String token) {

        return uberService.checkUser(token);
//        throw new UnsupportedOperationException("Operation not supported");
    }

    @Operation(summary = "Redeem Uber account balance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK - processed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - processing failed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - processing failed"),
            @ApiResponse(responseCode = "404", description = "Not Found - processing failed"),
            @ApiResponse(responseCode = "409", description = "Conflict - processing failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - processing failed")
    })
    @PostMapping(TOPUP)
    public BaseResponse redeem(@RequestHeader("Authorization") String token,
                               @RequestBody @NotNull @Valid TransferRequest request) {

        return uberService.redeem(token, request);
//        throw new UnsupportedOperationException("Operation not supported");
    }
}
