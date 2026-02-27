package com.firoz.eb.services.graphql;


import com.firoz.eb.services.domain.EventEntity;
import com.firoz.eb.services.domain.EventType;
import com.firoz.eb.services.domain.ServiceEntity;
import com.firoz.eb.services.domain.ServiceEnvironment;
import com.firoz.eb.services.domain.ServiceStatus;
import com.firoz.eb.services.persistence.EventRepository;
import com.firoz.eb.services.persistence.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Controller
public class ServiceGraphqlController {

    Logger logger;
    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;

    public ServiceGraphqlController(ServiceRepository serviceRepository, EventRepository eventRepository) {
        this.serviceRepository = serviceRepository;
        this.eventRepository = eventRepository;
    }

    @QueryMapping
    public List<ServiceEntity> services(
            @Argument String name,
            @Argument String owner,
            @Argument ServiceEnvironment environment,
            @Argument ServiceStatus status
    ) {
        // Keep it simple for assignment: fetch all then filter in-memory.
        // If you want DB-level filtering, we can add Specifications later.
        List<ServiceEntity> all = serviceRepository.findAll();

        return all.stream()
                .filter(s -> name == null || s.getName().equalsIgnoreCase(name))
                .filter(s -> owner == null || s.getOwner().equalsIgnoreCase(owner))
                .filter(s -> environment == null || s.getEnvironment() == environment)
                .filter(s -> status == null || s.getStatus() == status)
                .toList();
    }

    @SchemaMapping(typeName = "Service", field = "events")
    public List<EventEntity> events(ServiceEntity service, @Argument EventType type) {
        Long serviceId = service.getId();
        if (serviceId == null) return List.of();

        return (type == null)
                ? eventRepository.findByServiceId(serviceId)
                : eventRepository.findByServiceIdAndType(serviceId, type);
    }
}