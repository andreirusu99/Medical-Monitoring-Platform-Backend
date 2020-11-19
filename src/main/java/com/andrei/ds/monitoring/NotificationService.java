package com.andrei.ds.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    private final SimpMessagingTemplate template;

    public NotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void dispatchNotification(String message) {
        template.convertAndSend("/notification", message);
        LOGGER.info("<--- Notification: " + message);
    }

}
