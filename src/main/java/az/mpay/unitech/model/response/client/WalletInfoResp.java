package az.mpay.unitech.model.response.client;

import az.mpay.unitech.model.dto.client.CoinDto;
import az.mpay.unitech.model.response.server.BaseResponse;
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
