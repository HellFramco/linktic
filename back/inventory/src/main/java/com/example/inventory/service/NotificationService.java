package com.example.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final List<SseEmitter> emitters = new ArrayList<>();

    public SseEmitter register() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void sendEvent(Object data) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(data);
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
}
