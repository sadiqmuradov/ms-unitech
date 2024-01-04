package az.mpay.unitech.model.entity;

import az.mpay.unitech.constant.enums.Currency;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_rate_seq")
    @SequenceGenerator(name = "currency_rate_seq", sequenceName = "currency_rate_id_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency from;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency to;

    @Column(nullable = false)
    private BigDecimal rate;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Getter
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
