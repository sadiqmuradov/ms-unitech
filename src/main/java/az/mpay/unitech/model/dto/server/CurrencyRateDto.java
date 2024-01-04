package az.mpay.unitech.model.dto.server;

import az.mpay.unitech.constant.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateDto {

    private Currency from;
    private Currency to;
    private BigDecimal rate;
}
