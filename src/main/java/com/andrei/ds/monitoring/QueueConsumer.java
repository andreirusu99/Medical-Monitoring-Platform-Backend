package com.andrei.ds.monitoring;

import com.andrei.ds.DTOs.ActivityDTO;
import com.andrei.ds.services.ActivityService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class QueueConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumer.class);

    private final ActivityService activityService;

    @Autowired
    public QueueConsumer(ActivityService activityService) {
        this.activityService = activityService;
    }

    public void receiveMessage(byte[] bytes) {
        try {
            String activityJson = new String(bytes, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            ActivityDTO activityDTO = gson.fromJson(activityJson, ActivityDTO.class);
            LOGGER.info("---> Received " + activityDTO);
            activityService.processActivity(activityDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
