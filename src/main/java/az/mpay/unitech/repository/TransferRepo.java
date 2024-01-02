package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepo extends JpaRepository<Transfer, Long> {

}
