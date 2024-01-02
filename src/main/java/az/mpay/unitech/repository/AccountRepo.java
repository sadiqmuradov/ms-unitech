package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.Account;
import az.mpay.unitech.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByUser(User user);
}
