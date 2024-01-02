package az.mpay.unitech.model.request.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserCoinSerialReq {

    private String login;
    private String currencyCode;
}
