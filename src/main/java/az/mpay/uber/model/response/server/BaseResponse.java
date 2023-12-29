package az.mpay.uber.model.response.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static az.mpay.uber.constant.Constant.ok;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(NON_EMPTY)
public class BaseResponse {

    private String status;
    private String message;

    public static BaseResponse success() {
        return BaseResponse.builder()
                .status(ok)
                .message("processed successfully")
                .build();
    }
}
