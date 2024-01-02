package az.mpay.unitech.model.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class IssuerDto {

    private String id;
    private String currency;
}
