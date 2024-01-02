package az.mpay.unitech.repository;

import az.mpay.unitech.model.entity.ProfileScheduleRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileScheduleRunRepo extends JpaRepository<ProfileScheduleRun, Long> {

    Optional<ProfileScheduleRun> findFirstByOrderByIdDesc();
}
