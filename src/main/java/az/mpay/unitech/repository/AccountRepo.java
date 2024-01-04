package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}
