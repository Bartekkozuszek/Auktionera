package se.iths.receiver.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import se.iths.receiver.business.service.IUserLogService;

@Component
public class RabbitMQ {

    private final IUserLogService userLogService;

    public RabbitMQ(IUserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @RabbitListener(queues = "${auktionera.rabbitmq.queue}")
    public void receivedMessage(String message) {
        String userName = message.substring(0, message.lastIndexOf("-"));
        String userId = message.substring(message.lastIndexOf("-") + 1);
        userLogService.createUserLog(userName, userId);
        System.out.println("Received Message from RabbitMQ: " + message);
    }
}

