package com.andrei.ds.DTOs;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class ActivityDTO {

    @NonNull
    private UUID patientId;

    @NonNull
    private String activityName;

    @NonNull
    private Long startTime;

    @NonNull
    private Long endTime;

    public ActivityDTO(@NonNull UUID patientId,
                       @NonNull String activityName,
                       @NonNull Long startTime,
                       @NonNull Long endTime) {
        this.patientId = patientId;
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setPatientId(@NonNull UUID patientId) {
        this.patientId = patientId;
    }

    public void setActivityName(@NonNull String activityName) {
        this.activityName = activityName;
    }

    public void setStartTime(@NonNull Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(@NonNull Long endTime) {
        this.endTime = endTime;
    }

    @NonNull
    public UUID getPatientId() {
        return patientId;
    }

    @NonNull
    public String getActivityName() {
        return activityName;
    }

    @NonNull
    public Long getStartTime() {
        return startTime;
    }

    @NonNull
    public Long getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return patientId.toString() + ", " + activityName + ", from " + startTime + " to " + endTime;
    }
}
