package az.mpay.uber.model.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@ToString
public class CoinDto {

    private String serial;
    private String name;
    private BigDecimal amount;
    private BigDecimal availableAmount;
    private BigDecimal futureAmount;
    private BigDecimal heldAmount;
    private BigDecimal creditLimit;
    private BigDecimal totalAmount;
    private IssuerDto issuer;
}
