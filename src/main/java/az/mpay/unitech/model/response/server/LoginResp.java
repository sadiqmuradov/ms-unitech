package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.LoginDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class LoginResp extends BaseResponse {

    private LoginDto login;

    private LoginResp(LoginDto dto) {
        super("ok", "processed successfully");
        login = dto;
    }

    public static LoginResp of(LoginDto dto) {
        return new LoginResp(dto);
    }
}
