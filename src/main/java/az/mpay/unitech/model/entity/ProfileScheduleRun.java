package az.mpay.unitech.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@ToString(exclude = "profiles")
@Entity
@Table(name = "profile_schedule_run")
public class ProfileScheduleRun {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_schedule_run_seq")
    @SequenceGenerator(name = "profile_schedule_run_seq", sequenceName = "profile_schedule_run_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime startedAt;

    @Column(nullable = false)
    private OffsetDateTime uberRequestTime;

    @Column(nullable = false)
    private OffsetDateTime uberResponseTime;

    @Column(nullable = false)
    private OffsetDateTime saveStartedAt;

    private OffsetDateTime saveFinishedAt;

    private OffsetDateTime finishedAt;

    @Column(nullable = false)
    private Integer reqOffset;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleRun", fetch = FetchType.LAZY)
    private List<DriverProfile> profiles;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;
}
