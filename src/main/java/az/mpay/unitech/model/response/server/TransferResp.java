package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.AccountDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import static az.mpay.unitech.constant.Constant.ok;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class TransferResp extends BaseResponse {

    private List<AccountDto> accounts;

    private TransferResp(List<AccountDto> dtoList) {
        super(ok, "processed successfully");
        accounts = dtoList;
    }

    public static TransferResp of(List<AccountDto> dtoList) {
        return new TransferResp(dtoList);
    }
}
