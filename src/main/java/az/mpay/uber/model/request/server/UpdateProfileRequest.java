package az.mpay.uber.model.request.server;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@ToString
public class UpdateProfileRequest {

    @NotBlank
    private String phoneNumber;
}
