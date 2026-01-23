package com.example.inventory.messaging;

import com.example.inventory.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class InventoryEventsConsumer {

    private final NotificationService notificationService;

    public InventoryEventsConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Bean
    public Consumer<Map<String, Object>> inventoryEvents() {
        return event -> notificationService.sendEvent(event);
    }
}
