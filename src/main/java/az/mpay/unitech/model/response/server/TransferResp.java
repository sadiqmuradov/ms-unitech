package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.AccountDto;
import az.mpay.unitech.model.dto.server.TransferDto;
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
public class TransferResp extends BaseResponse {

    private List<TransferDto> transfers;

    private TransferResp(List<TransferDto> dtoList) {
        super(ok, "processed successfully");
        transfers = dtoList;
    }

    public static TransferResp of(List<TransferDto> dtoList) {
        return new TransferResp(dtoList);
    }
}
