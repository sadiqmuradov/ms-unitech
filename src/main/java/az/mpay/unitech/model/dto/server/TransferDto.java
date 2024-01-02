package az.mpay.unitech.model.dto.server;

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
public class TransferDto {

    private Long senderAccountId;
    private Long receiverAccountId;
    private BigDecimal amount;
    private Status status;
    private String errorDetails;
}
