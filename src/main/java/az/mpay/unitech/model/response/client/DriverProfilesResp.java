package az.mpay.unitech.model.response.client;

import az.mpay.unitech.model.dto.client.UberDriverProfileDto;
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
public class DriverProfilesResp {

    @JsonProperty("driver_profiles")
    private List<UberDriverProfileDto> profiles;
}
