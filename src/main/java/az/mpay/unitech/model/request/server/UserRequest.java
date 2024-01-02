package az.mpay.unitech.model.request.server;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserRequest {

    @NotBlank
    private String pin;

    @NotBlank
    private String password;
}
