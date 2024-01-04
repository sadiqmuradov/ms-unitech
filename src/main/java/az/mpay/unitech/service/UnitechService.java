package az.mpay.unitech.service;

import az.mpay.unitech.model.request.server.CurrencyRateRequest;
import az.mpay.unitech.model.request.server.TransferRequest;
import az.mpay.unitech.model.request.server.UserRequest;
import az.mpay.unitech.model.response.server.*;

import java.util.List;

public interface UnitechService {

    RegisterResp registerUser(UserRequest request);

    LoginResp login(UserRequest request);

    AccountsResp getUserAccounts(Long userId);

    TransferResp transfer(Long userId, List<TransferRequest> request);

    CurrencyRateResp getCurrencyRate(Long userId, CurrencyRateRequest request);
}
