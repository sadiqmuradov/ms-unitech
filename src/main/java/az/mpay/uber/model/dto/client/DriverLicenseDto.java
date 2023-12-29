package az.mpay.uber.model.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class DriverLicenseDto {

    private String country;

    @JsonProperty("expiration_date")
    private OffsetDateTime expirationDate;

    @JsonProperty("issue_date")
    private OffsetDateTime issueDate;

    @JsonProperty("normalized_number")
    private String normalizedNumber;

    private String number;
}
