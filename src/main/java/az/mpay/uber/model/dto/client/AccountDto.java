package az.mpay.uber.model.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AccountDto {

    private BigDecimal balance;

    @JsonProperty("balance_limit")
    private BigDecimal balanceLimit;

    private String currency;

    private String id;

    @JsonProperty("last_transaction_date")
    private OffsetDateTime lastTransactionDate;

    private String type;
}
