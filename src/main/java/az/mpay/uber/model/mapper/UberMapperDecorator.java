package az.mpay.uber.model.mapper;

import az.mpay.uber.exception.ParkNotFoundException;
import az.mpay.uber.model.entity.Park;
import az.mpay.uber.model.entity.ProfileScheduleRun;
import az.mpay.uber.model.response.client.DriverProfilesResp;
import az.mpay.uber.repository.ParkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UberMapperDecorator implements UberMapper {

    @Autowired
    @Qualifier("delegate")
    private UberMapper delegate;

    @Autowired
    private ParkRepo parkRepo;

    @Override
    public ProfileScheduleRun fromClientResp(DriverProfilesResp profilesResp) {
        ProfileScheduleRun scheduleRun = delegate.fromClientResp(profilesResp);
        String parkId = profilesResp.getProfiles().get(0).getProfile().getParkId();
        Park park = parkRepo.findById(parkId)
                            .orElseThrow(() -> new ParkNotFoundException("Park '%s' not found", parkId));

        scheduleRun.getProfiles().forEach(profile -> {
            profile.setScheduleRun(scheduleRun);
            profile.setPark(park);
            park.getProfiles().add(profile);
        });

        return scheduleRun;
    }

//    @Override
//    public DriverProfile fromClientDto(DriverProfileDto dto) {
//        DriverProfile profile = delegate.fromClientDto(dto);
//        parkRepo.findById(dto.getProfile().getParkId())
//                .ifPresent(park -> {
//                    if (park.getProfiles() == null) {
//                        park.setProfiles(new ArrayList<>());
//                    }
//                    park.getProfiles().add(profile);
//                    profile.setPark(park);
//                });
//
//        return profile;
//    }
}
