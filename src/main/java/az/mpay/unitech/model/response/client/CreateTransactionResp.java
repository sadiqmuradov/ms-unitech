package az.mpay.unitech.model.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@ToString
public class CreateTransactionResp {

    @JsonProperty("event_at")
    private OffsetDateTime eventAt;

    @JsonProperty("park_id")
    private String parkId;

    @JsonProperty("driver_profile_id")
    private String driverProfileId;

    @JsonProperty("category_id")
    private String categoryId;

    private BigDecimal amount;

    @JsonProperty("currency_code")
    private String currencyCode;

    private String description;
}
