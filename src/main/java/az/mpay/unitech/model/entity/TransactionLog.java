package az.mpay.unitech.model.entity;

import az.mpay.unitech.constant.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "transaction_log")
public class TransactionLog {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_log_seq")
    @SequenceGenerator(name = "transaction_log_seq", sequenceName = "transaction_log_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Currency status;

    @Column(nullable = false)
    private Boolean redeemed;

    @Column(columnDefinition = "text")
    private String errorDetails;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Getter
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
