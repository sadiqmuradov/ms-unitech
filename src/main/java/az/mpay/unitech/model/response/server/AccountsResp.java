package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.AccountDto;
import az.mpay.unitech.model.dto.server.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

import static az.mpay.unitech.constant.Constant.ok;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class AccountsResp extends BaseResponse {

    private List<AccountDto> accounts;

    private AccountsResp(List<AccountDto> dtoList) {
        super(ok, "processed successfully");
        accounts = dtoList;
    }

    public static AccountsResp of(List<AccountDto> dtoList) {
        return new AccountsResp(dtoList);
    }
}
