package az.mpay.uber.model.response.client;

import az.mpay.uber.model.dto.client.CoinDto;
import az.mpay.uber.model.response.server.BaseResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class WalletInfoResp extends BaseResponse {

    private List<CoinDto> coins;
}
