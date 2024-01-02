package az.mpay.unitech.model.request.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CoinTransferReq {

    private String senderCoin;
    private String recipientCoin;
    private BigDecimal amount;
    private String description;
}
