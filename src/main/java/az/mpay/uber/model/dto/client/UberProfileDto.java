package az.mpay.uber.model.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UberProfileDto {

    private String id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    private List<String> phones;

    @JsonProperty("driver_license")
    private DriverLicenseDto driverLicense;

    @JsonProperty("work_status")
    private String workStatus;

    @JsonProperty("hire_date")
    private OffsetDateTime hireDate;

    @JsonProperty("fire_date")
    private OffsetDateTime fireDate;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

    @JsonProperty("modified_date")
    private OffsetDateTime modifiedDate;

    @JsonProperty("park_id")
    private String parkId;
}
