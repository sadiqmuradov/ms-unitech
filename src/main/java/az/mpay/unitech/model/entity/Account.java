package az.mpay.unitech.model.entity;

import az.mpay.unitech.constant.enums.Currency;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "account")
@Where(clause = "deleted = false")
public class Account {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Currency ccy;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Getter
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
