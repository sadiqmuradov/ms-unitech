package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.AccountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AccountsResp extends BaseResponse {

    private List<AccountDto> accounts;

    private AccountsResp(List<AccountDto> dtoList) {
        super("ok", "processed successfully");
        accounts = dtoList;
    }

    public static AccountsResp of(List<AccountDto> dtoList) {
        return new AccountsResp(dtoList);
    }
}
