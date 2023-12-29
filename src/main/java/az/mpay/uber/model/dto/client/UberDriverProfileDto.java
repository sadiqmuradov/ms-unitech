package az.mpay.uber.model.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UberDriverProfileDto {

    private List<AccountDto> accounts;

    @JsonProperty("driver_profile")
    private UberProfileDto profile;
}
