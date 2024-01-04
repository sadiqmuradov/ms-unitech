package az.mpay.unitech.controller;

import az.mpay.unitech.model.request.server.CurrencyRateRequest;
import az.mpay.unitech.model.request.server.TransferRequest;
import az.mpay.unitech.model.request.server.UserRequest;
import az.mpay.unitech.model.response.server.*;
import az.mpay.unitech.service.AuthService;
import az.mpay.unitech.service.UnitechService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static az.mpay.unitech.constant.URL.*;

@Slf4j
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping(API_V1)
public class UnitechController {

    private final UnitechService unitechService;
    private final AuthService authService;

    @Operation(summary = "Register user")
    @ApiResponses({
            @ApiResponse(responseCode = "200 - OK", description = "Registered user"),
            @ApiResponse(responseCode = "400 - Bad Request", description = "Client validation errors"),
            @ApiResponse(responseCode = "409 - Conflict", description = "Already registered user exception"),
            @ApiResponse(responseCode = "500 - Internal Server Error", description = "Internal error exception")
    })
    @PostMapping(USERS)
    public RegisterResp registerUser(@RequestBody @Valid UserRequest request) {

        return unitechService.registerUser(request);
    }

    @Operation(summary = "User login")
    @ApiResponses({
            @ApiResponse(responseCode = "200 - OK", description = "Auth token"),
            @ApiResponse(responseCode = "400 - Bad Request", description = "Client validation errors"),
            @ApiResponse(responseCode = "404 - Not Found", description = "User not found exception"),
            @ApiResponse(responseCode = "500 - Internal Server Error", description = "Internal error exception")
    })
    @PostMapping(LOGIN)
    public LoginResp login(@RequestBody @Valid UserRequest request) {

        return unitechService.login(request);
    }

    @Operation(summary = "Get user accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200 - OK", description = "Retrieved accounts"),
            @ApiResponse(responseCode = "400 - Bad Request", description = "Client validation errors"),
            @ApiResponse(responseCode = "404 - Not Found", description = "User not found exception"),
            @ApiResponse(responseCode = "500 - Internal Server Error", description = "Internal error exception")
    })
    @GetMapping(ACCOUNTS)
    public AccountsResp userAccounts(@RequestHeader("Authorization") String token) {

        return unitechService.getUserAccounts(authService.checkUser(token));
    }

    @Operation(summary = "Make transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "200 - OK", description = "Transfers completed"),
            @ApiResponse(responseCode = "400 - Bad Request", description = "Client validation errors"),
            @ApiResponse(responseCode = "404 - Not Found", description = "User not found exception"),
            @ApiResponse(responseCode = "500 - Internal Server Error", description = "Internal error exception")
    })
    @PostMapping(TRANSFER)
    public TransferResp transfer(@RequestHeader("Authorization") String token,
                                 @RequestBody @Valid List<TransferRequest> request) {

        return unitechService.transfer(authService.checkUser(token), request);
    }

    @Operation(summary = "Get selected currency rate")
    @ApiResponses({
            @ApiResponse(responseCode = "200 - OK", description = "Retrieved currency rate"),
            @ApiResponse(responseCode = "400 - Bad Request", description = "Client validation errors"),
            @ApiResponse(responseCode = "404 - Not Found", description = "User not found exception"),
            @ApiResponse(responseCode = "500 - Internal Server Error", description = "Internal error exception")
    })
    @PostMapping(GET_CURRENCY_RATE)
    public CurrencyRateResp currencyRate(@RequestHeader("Authorization") String token,
                                         @RequestBody @Valid CurrencyRateRequest request) {

        return unitechService.getCurrencyRate(authService.checkUser(token), request);
    }
}
