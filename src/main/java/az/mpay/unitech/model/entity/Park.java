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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@ToString(exclude = "profiles")
@Entity
@Table(name = "park")
public class Park {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    private String id;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String apiKey;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "park", fetch = FetchType.LAZY)
    private List<DriverProfile> profiles;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;
}
