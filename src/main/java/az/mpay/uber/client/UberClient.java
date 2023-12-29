package az.mpay.uber.client;

import az.mpay.uber.model.request.client.CreateTransactionReq;
import az.mpay.uber.model.request.client.DriverProfilesReq;
import az.mpay.uber.model.response.client.CreateTransactionResp;
import az.mpay.uber.model.response.client.DriverProfilesResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${uber.url}", name = "uber")
public interface UberClient {

    @PostMapping("/v1/parks/driver-profiles/list")
    DriverProfilesResp getDriverProfiles(@RequestHeader("X-Client-ID") String clientId,
                                         @RequestHeader("X-API-Key") String apiKey,
                                         @RequestBody DriverProfilesReq driverProfilesReq);

    @PostMapping("/v2/parks/driver-profiles/transactions")
    CreateTransactionResp createTransaction(@RequestHeader("X-Client-ID") String clientId,
                                            @RequestHeader("X-API-Key") String apiKey,
                                            @RequestHeader("X-Idempotency-Token") String idempotencyToken,
                                            @RequestBody CreateTransactionReq createTransactionReq);
}
