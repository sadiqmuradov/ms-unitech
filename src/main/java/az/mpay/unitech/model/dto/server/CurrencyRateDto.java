package az.mpay.unitech.model.dto.server;

import az.mpay.unitech.constant.enums.Currency;
import az.mpay.unitech.constant.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class CurrencyRateDto {

    private Currency from;
    private Currency to;
    private BigDecimal rate;
}
