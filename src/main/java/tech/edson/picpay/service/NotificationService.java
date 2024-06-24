package tech.edson.picpay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.edson.picpay.client.NotificationClient;
import tech.edson.picpay.entity.Transfer;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationClient notificationClient;

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    void sendNotification(Transfer transfer) {

        try {
            logger.info("Sending notification...");
            var resp = notificationClient.sendNotification(transfer);
            if(resp.getStatusCode().isError()) {
                logger.error("Error while sending notification, status code is not ok");
            }
        } catch (Exception e) {
            logger.error("Error while sending notification", e);
        }
    }
}
