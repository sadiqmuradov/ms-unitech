package az.mpay.uber.model.request.client;

import az.mpay.uber.model.dto.client.BankDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class CoinTopupReq {

    private String coin;
    private BigDecimal amount;
    private BankDetailsDto bankDetails;
}
