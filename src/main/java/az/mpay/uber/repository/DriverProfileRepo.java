package az.mpay.uber.repository;

import az.mpay.uber.model.entity.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverProfileRepo extends JpaRepository<DriverProfile, String> {

    List<DriverProfile> findAllByPhonesContains(String phoneNumber);
}
