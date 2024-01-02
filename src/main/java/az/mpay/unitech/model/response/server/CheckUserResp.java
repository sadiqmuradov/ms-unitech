package az.mpay.unitech.model.response.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static az.mpay.unitech.constant.Constant.ok;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(NON_EMPTY)
public class CheckUserResp extends BaseResponse {

    private Boolean uberUser;

    private CheckUserResp(Boolean uberUser) {
        super(ok, "processed successfully");
        this.uberUser = uberUser;
    }

    public static CheckUserResp of(Boolean uberUser) {
        return new CheckUserResp(uberUser);
    }
}
