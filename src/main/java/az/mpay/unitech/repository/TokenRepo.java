package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenAndExpiredAtAfter(String token, OffsetDateTime now);
}
