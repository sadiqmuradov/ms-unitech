package az.mpay.uber.model.dto.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(NON_EMPTY)
public class DriverProfileDto {

    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<String> phoneNumbers;
}
