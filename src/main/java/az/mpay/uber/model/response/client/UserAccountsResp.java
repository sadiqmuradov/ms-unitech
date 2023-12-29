package az.mpay.uber.model.response.client;

import az.mpay.uber.model.response.server.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserAccountsResp extends BaseResponse {

    private List<String> phoneNumbers;
}
