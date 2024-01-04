package az.mpay.unitech.model.request.server;

import az.mpay.unitech.constant.enums.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor
public class CurrencyRateRequest {

    @NotNull
    private Currency from;

    @NotNull
    private Currency to;
}
