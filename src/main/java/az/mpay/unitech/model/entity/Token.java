package az.mpay.unitech.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_token")
public class Token {

    private static final long serialVersionUID = -5784998468015412179L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    @SequenceGenerator(name = "token_seq", sequenceName = "token_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private String token;

    @Getter
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Getter
    private OffsetDateTime expiredAt;
}
