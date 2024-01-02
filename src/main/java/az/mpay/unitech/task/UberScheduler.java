package az.mpay.unitech.task;

import az.mpay.unitech.client.UberClient;
import az.mpay.unitech.model.dto.client.QueryDto;
import az.mpay.unitech.model.entity.ProfileScheduleRun;
import az.mpay.unitech.model.mapper.UnitechMapper;
import az.mpay.unitech.model.request.client.DriverProfilesReq;
import az.mpay.unitech.model.response.client.DriverProfilesResp;
import az.mpay.unitech.repository.ProfileScheduleRunRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.OffsetDateTime;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class UberScheduler {

    @Value("${uber.request.park-id}")
    private String parkId;
    @Value("${uber.request.client-id}")
    private String clientId;
    @Value("${uber.request.api-key}")
    private String apiKey;
    @Value("${uber.request.limit}")
    private Integer limit;
    @Value("${uber.request.offset}")
    private Integer offset;

    private final ProfileScheduleRunRepo scheduleRunRepo;
    private final UberClient uberClient;
    private final UnitechMapper mapper;


//    @Scheduled(cron = "${schedule.cron.expression}")
    @Scheduled(fixedDelayString = "${schedule.fixed-delay.in.milliseconds}") // must be 600000
    public void saveDriverProfiles() {
        try {
            log.info("Profile scheduler starts ...");
            OffsetDateTime startTime = OffsetDateTime.now();

            Integer computedOffset = offset;
            Optional<ProfileScheduleRun> lastScheduleRunOpt = scheduleRunRepo.findFirstByOrderByIdDesc();
            if (lastScheduleRunOpt.isPresent()) {
                computedOffset = lastScheduleRunOpt.get().getReqOffset();
            }

            OffsetDateTime uberRequestTime = OffsetDateTime.now();
            log.info("Getting list of driver profiles (couriers) ...");
            DriverProfilesResp driverProfilesResp =
                    uberClient.getDriverProfiles(clientId,
                                                 apiKey,
                                                 DriverProfilesReq.builder()
                                                         .limit(limit)
                                                         .offset(computedOffset)
                                                         .query(QueryDto.create(parkId))
                                                         .build());

            OffsetDateTime uberResponseTime = OffsetDateTime.now();

            if (ObjectUtils.isEmpty(driverProfilesResp.getProfiles())) {
                log.info("No new driver profiles, exiting ...");
                return;
            } else if (driverProfilesResp.getProfiles().size() < limit) {
                computedOffset += driverProfilesResp.getProfiles().size();
            } else {
                computedOffset += limit;
            }

            ProfileScheduleRun scheduleRun = mapper.fromClientResp(driverProfilesResp);

            scheduleRun.setStartedAt(startTime);
            scheduleRun.setUberRequestTime(uberRequestTime);
            scheduleRun.setUberResponseTime(uberResponseTime);
            scheduleRun.setReqOffset(computedOffset);
            scheduleRun.setSaveStartedAt(OffsetDateTime.now());

            scheduleRun = scheduleRunRepo.save(scheduleRun);

            scheduleRun.setSaveFinishedAt(OffsetDateTime.now());
            scheduleRun.setFinishedAt(OffsetDateTime.now());

            scheduleRunRepo.save(scheduleRun);
        } catch (Exception e) {
            log.error("Profile scheduler failed. Cause: {}", e.getMessage());
        }
    }
}
