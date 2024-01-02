package az.mpay.unitech.model.response.client;

import az.mpay.unitech.model.response.server.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserCoinSerialResp extends BaseResponse {

    private String serial;
}