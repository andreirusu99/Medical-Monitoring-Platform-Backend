package com.andrei.ds.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Activity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "activity", nullable = false)
    private String activityName;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time", nullable = false)
    private Long endTime;

    public Activity() {
    }

    public Activity(UUID patientId, String activityName, Long startTime, Long endTime) {
        this.patientId = patientId;
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getEndTime() {
        return endTime;
    }
}
