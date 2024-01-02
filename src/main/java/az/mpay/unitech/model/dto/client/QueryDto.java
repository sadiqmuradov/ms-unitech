package az.mpay.unitech.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class QueryDto {

    private ParkDto park;

    public static QueryDto create(String parkId) {
        return new QueryDto(ParkDto.builder()
                                   .id(parkId)
                                   .build());
    }

    public static QueryDto create(String profileId, String parkId) {
        return new QueryDto(ParkDto.builder()
                                   .driverProfile(DriverProfileReqDto.builder()
                                                                    .id(Collections.singletonList(profileId))
                                                                    .build())
                                   .id(parkId)
                                   .build());
    }
}
