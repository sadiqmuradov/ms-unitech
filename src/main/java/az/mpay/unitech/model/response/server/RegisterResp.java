package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class RegisterResp extends BaseResponse {

    private UserDto user;

    private RegisterResp(UserDto dto) {
        super("ok", "processed successfully");
        user = dto;
    }

    public static RegisterResp of(UserDto dto) {
        return new RegisterResp(dto);
    }
}
