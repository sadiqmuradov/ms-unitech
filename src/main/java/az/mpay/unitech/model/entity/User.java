package az.mpay.unitech.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Account> accounts;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Getter
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
