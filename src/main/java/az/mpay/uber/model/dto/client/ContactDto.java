package az.mpay.uber.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ContactDto {

    private String phoneNumber;
    private Boolean phoneVerified;
    private String email;
    private Boolean emailVerified;
}
