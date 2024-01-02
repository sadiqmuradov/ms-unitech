package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByPin(String pin);
}
