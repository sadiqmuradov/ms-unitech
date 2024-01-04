package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.CurrencyRateDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CurrencyRateResp extends BaseResponse {

    private CurrencyRateDto currencyRate;

    private CurrencyRateResp(CurrencyRateDto dto) {
        super("ok", "processed successfully");
        currencyRate = dto;
    }

    public static CurrencyRateResp of(CurrencyRateDto dto) {
        return new CurrencyRateResp(dto);
    }
}
