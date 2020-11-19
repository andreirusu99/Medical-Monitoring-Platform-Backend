package com.andrei.ds.services;

import com.andrei.ds.DTOs.ActivityDTO;
import com.andrei.ds.entities.Activity;
import com.andrei.ds.entities.Patient;
import com.andrei.ds.monitoring.NotificationService;
import com.andrei.ds.repositories.ActivityRepository;
import com.andrei.ds.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);
    private final ActivityRepository activityRepository;
    private final PatientRepository patientRepository;
    private final NotificationService notificationService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, PatientRepository patientRepository, NotificationService notificationService) {
        this.activityRepository = activityRepository;
        this.patientRepository = patientRepository;
        this.notificationService = notificationService;
    }

    public void processActivity(ActivityDTO activityDTO) {
        if (activityDTO == null) {
            return;
        }
        LOGGER.info("Processing " + activityDTO);

        UUID patientId = activityDTO.getPatientId();
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (!optionalPatient.isPresent()) {
            LOGGER.error("Patient with id {} does not exist!", patientId);
            return;
        }

        Optional<Activity> optionalActivity = activityRepository.findActivityByDetails(
                activityDTO.getPatientId(),
                activityDTO.getActivityName(),
                activityDTO.getStartTime(),
                activityDTO.getEndTime());
        if (optionalActivity.isPresent()) {
            LOGGER.error("Duplicate activity!");
            return;
        }
        applyRules(activityDTO);
//        Activity saved = activityRepository.save(
//                new Activity(
//                        activityDTO.getPatientId(),
//                        activityDTO.getActivityName(),
//                        activityDTO.getStartTime(),
//                        activityDTO.getEndTime()
//                ));
//        LOGGER.info("Saved new activity with id {} for patient {}", saved.getId(), optionalPatient.get().getName());
    }

    private void applyRules(ActivityDTO activityDTO) {

        String activity = activityDTO.getActivityName();
        Long startTime = activityDTO.getStartTime();
        Long endTime = activityDTO.getEndTime();
        double duration = endTime - startTime;
        Patient patient = patientRepository.findById(activityDTO.getPatientId()).get();
        String message;
        switch (activity) {
            case "Sleeping":
                if (duration > 7 * 3600) {
                    message = patient.getId() + ",sleeping," + String.format("%.2f", duration / 3600.0);
                    LOGGER.warn(message);
                    notificationService.dispatchNotification(message);
                }
                break;
            case "Showering":
                if (duration > 0.5 * 3600) {
                    message = patient.getId() + ",bathroom," + String.format("%.2f", duration / 60.0);
                    LOGGER.warn(message);
                    notificationService.dispatchNotification(message);
                }
                break;

            case "Leaving":
                if (duration > 5 * 3600) {
                    message = patient.getId() + ",outdoor," + String.format("%.2f", duration / 3600.0);
                    LOGGER.warn(message);
                    notificationService.dispatchNotification(message);
                }
                break;
            default:
                LOGGER.warn("Not a monitored activity");
                break;
        }

    }

}
