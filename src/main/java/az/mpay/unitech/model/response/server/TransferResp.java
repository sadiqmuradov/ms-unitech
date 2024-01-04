package az.mpay.unitech.model.response.server;

import az.mpay.unitech.model.dto.server.TransferDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TransferResp extends BaseResponse {

    private List<TransferDto> transfers;

    private TransferResp(List<TransferDto> dtoList) {
        super("ok", "processed successfully");
        transfers = dtoList;
    }

    public static TransferResp of(List<TransferDto> dtoList) {
        return new TransferResp(dtoList);
    }
}
