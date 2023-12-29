package az.mpay.uber.model.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ParkDto {

    private String id;

    @JsonProperty("driver_profile")
    private DriverProfileReqDto driverProfile;
}
