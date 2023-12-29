package az.mpay.uber.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class BankDetailsDto {

    private String fullName;
    private String iban;
    private String bic;
    private String swift;
}
