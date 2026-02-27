package com.firoz.eb.services.application;


import com.firoz.eb.services.domain.EventEntity;
import com.firoz.eb.services.domain.EventType;
import com.firoz.eb.services.domain.ServiceEntity;
import com.firoz.eb.services.persistence.EventRepository;
import com.firoz.eb.services.persistence.ServiceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EventGenerator {

    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;

    public EventGenerator(ServiceRepository serviceRepository, EventRepository eventRepository) {
        this.serviceRepository = serviceRepository;
        this.eventRepository = eventRepository;
    }

    @Scheduled(fixedRate = 30_000)
    public void generate() {
        System.out.println("========== generate data fatest ===");
        List<ServiceEntity> services = serviceRepository.findAll();
        if (services.isEmpty()) return;

        ServiceEntity service = services.get(ThreadLocalRandom.current().nextInt(services.size()));

        EventType type = pickType();
        EventEntity e = new EventEntity();
        e.setService(service);
        e.setType(type);
        e.setMessage("Mock " + type + " event for " + service.getName());

        eventRepository.save(e);
    }

    private EventType pickType() {
        int r = ThreadLocalRandom.current().nextInt(100);
        if (r < 70) return EventType.INFO;
        if (r < 90) return EventType.WARN;
        return EventType.ERROR;
    }
}