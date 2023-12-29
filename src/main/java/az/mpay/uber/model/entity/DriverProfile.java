package az.mpay.uber.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "driver_profile")
public class DriverProfile {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    private String id;

    @Column(nullable = false)
    private BigDecimal balance;

    private BigDecimal balanceLimit;

    @Column(nullable = false)
    private String currency;

    private OffsetDateTime lastTransactionDate;

    private String type;

    private String firstName;

    private String lastName;

    private String middleName;

    @ElementCollection
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name="profile_id", nullable = false))
    @Column(name = "phone")
    @OrderColumn(name = "idx")
    private List<String> phones;

    @Column(name = "dl_country")
    private String driverLicenseCountry;

    @Column(name = "dl_expiration_date")
    private OffsetDateTime driverLicenseExpirationDate;

    @Column(name = "dl_issue_date")
    private OffsetDateTime driverLicenseIssueDate;

    @Column(name = "dl_normalized_number")
    private String driverLicenseNormalizedNumber;

    @Column(name = "dl_number")
    private String driverLicenseNumber;

    private String workStatus;

    private OffsetDateTime hireDate;

    private OffsetDateTime fireDate;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProfileScheduleRun scheduleRun;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Park park;

    @Getter
    @CreationTimestamp
    private OffsetDateTime dbCreatedAt;

    @Getter
    @UpdateTimestamp
    private OffsetDateTime dbUpdatedAt;
}
