package az.mpay.unitech.model.mapper;

import az.mpay.unitech.exception.ParkNotFoundException;
import az.mpay.unitech.model.entity.Park;
import az.mpay.unitech.model.entity.ProfileScheduleRun;
import az.mpay.unitech.model.response.client.DriverProfilesResp;
import az.mpay.unitech.repository.ParkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UnitechMapperDecorator implements UnitechMapper {

    @Autowired
    @Qualifier("delegate")
    private UnitechMapper delegate;

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
