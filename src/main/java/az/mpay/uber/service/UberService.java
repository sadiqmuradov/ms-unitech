package az.mpay.uber.service;

import az.mpay.uber.model.request.server.CreateParkRequest;
import az.mpay.uber.model.request.server.RedeemRequest;
import az.mpay.uber.model.request.server.UpdateProfileRequest;
import az.mpay.uber.model.response.server.BaseResponse;
import az.mpay.uber.model.response.server.CheckUserResp;
import az.mpay.uber.model.response.server.DriverProfileResp;

public interface UberService {

    BaseResponse createPark(String token, CreateParkRequest request);
    CheckUserResp checkUser(String token);
    BaseResponse redeem(String token, RedeemRequest request);
    DriverProfileResp updateProfile(String token, String profileId, UpdateProfileRequest request);
    DriverProfileResp getProfile(String token, String profileId);
}
