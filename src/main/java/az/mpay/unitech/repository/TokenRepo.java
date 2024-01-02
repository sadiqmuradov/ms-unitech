package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.Token;
import az.mpay.unitech.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
