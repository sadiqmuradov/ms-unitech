package az.mpay.uber.model.request.client;

import az.mpay.uber.model.dto.client.QueryDto;
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
