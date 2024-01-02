package az.mpay.unitech.model.request.client;

import az.mpay.unitech.model.dto.client.QueryDto;
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
public class DriverProfilesReq {

    private Integer limit;
    private Integer offset;
    private QueryDto query;
}
