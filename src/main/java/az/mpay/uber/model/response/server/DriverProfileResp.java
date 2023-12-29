package az.mpay.uber.model.response.server;

import az.mpay.uber.model.dto.server.DriverProfileDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static az.mpay.uber.constant.Constant.ok;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(NON_EMPTY)
public class DriverProfileResp extends BaseResponse {

    private DriverProfileDto profile;

    private DriverProfileResp(DriverProfileDto dto) {
        super(ok, "processed successfully");
        profile = dto;
    }

    public static DriverProfileResp of(DriverProfileDto dto) {
        return new DriverProfileResp(dto);
    }
}
