package com.firoz.eb.common.bootstrap;

import com.firoz.eb.services.domain.EventEntity;
import com.firoz.eb.services.domain.EventType;
import com.firoz.eb.services.domain.ServiceEntity;
import com.firoz.eb.services.domain.ServiceEnvironment;
import com.firoz.eb.services.domain.ServiceStatus;
import com.firoz.eb.services.persistence.EventRepository;
import com.firoz.eb.services.persistence.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;

    public DataSeeder(ServiceRepository serviceRepository, EventRepository eventRepository) {
        this.serviceRepository = serviceRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) {
        // If services already exist, assume DB is seeded.
        if (serviceRepository.count() > 0) return;

        seed("Payment-Service", "Handles payments", "Finance Team", ServiceEnvironment.PROD);
        seed("User-Service", "User management", "Identity Team", ServiceEnvironment.PROD);
        seed("Order-Service", "Orders", "Commerce Team", ServiceEnvironment.SIT);
    }

    private void seed(String name, String desc, String owner, ServiceEnvironment env) {
        ServiceEntity s = new ServiceEntity();
        s.setName(name);
        s.setDescription(desc);
        s.setOwner(owner);
        s.setEnvironment(env);
        s.setStatus(ServiceStatus.ACTIVE);

        ServiceEntity saved = serviceRepository.save(s);

        // Seed a few events per service so GraphQL queries have data
        createEvent(saved, EventType.INFO);
        createEvent(saved, EventType.ERROR);
        createEvent(saved, EventType.WARN);
    }

    private void createEvent(ServiceEntity service, EventType type) {
        EventEntity e = new EventEntity();
        e.setService(service);
        e.setType(type);
        e.setMessage("Test");

        // createdAt is typically set via @PrePersist in EventEntity (as you have)
        eventRepository.save(e);
    }
}