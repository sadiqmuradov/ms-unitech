package az.mpay.uber.repository;

import az.mpay.uber.model.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepo extends JpaRepository<Park, String> {

}
