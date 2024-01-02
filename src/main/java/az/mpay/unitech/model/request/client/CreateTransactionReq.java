package az.mpay.unitech.model.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateTransactionReq {

    private String amount;

    @JsonProperty("category_id")
    private String categoryId;

    private String description;

    @JsonProperty("driver_profile_id")
    private String driverProfileId;

    @JsonProperty("park_id")
    private String parkId;
}
