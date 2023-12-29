package az.mpay.uber.client;

import az.mpay.uber.model.request.client.CoinTransferReq;
import az.mpay.uber.model.request.client.UserCoinSerialReq;
import az.mpay.uber.model.response.client.UserCoinSerialResp;
import az.mpay.uber.model.response.client.UserInfoResp;
import az.mpay.uber.model.response.client.WalletInfoResp;
import az.mpay.uber.model.response.server.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${mpay.url}", name = "mpay")
public interface MpayClient {

    @GetMapping("/api/v1/users")
    UserInfoResp getUser(@RequestHeader("Authorization") String token);

    @PostMapping("/api/v1/emanat-coin")
    UserCoinSerialResp getCoinSerial(@RequestHeader("Authorization") String token,
                                     @RequestBody UserCoinSerialReq serialRequest);

    @PostMapping("/api/v1/transfers")
    BaseResponse makeTransfer(@RequestHeader("Authorization") String token,
                              @RequestBody CoinTransferReq transferReq);

    @GetMapping("/api/v1/coins")
    WalletInfoResp getCoins(@RequestHeader("Authorization") String token);
//
//    @PostMapping("/api/v1/bank-top-ups")
//    BaseResponse topupUserCoin(@RequestHeader("Authorization") String token,
//                               @RequestBody CoinTopupReq transferReq);
}
