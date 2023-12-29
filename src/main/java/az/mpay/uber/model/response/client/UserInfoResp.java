package az.mpay.uber.model.response.client;

import az.mpay.uber.model.dto.client.UserDto;
import az.mpay.uber.model.response.server.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserInfoResp extends BaseResponse {

    private UserDto user;
}
