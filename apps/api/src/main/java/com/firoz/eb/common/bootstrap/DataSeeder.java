package com.firoz.eb.common.bootstrap;

import com.firoz.eb.services.domain.ServiceEntity;
import com.firoz.eb.services.domain.ServiceEnvironment;
import com.firoz.eb.services.domain.ServiceStatus;
import com.firoz.eb.services.persistence.ServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ServiceRepository serviceRepository;

    public DataSeeder(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void run(String... args) {
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
        serviceRepository.save(s);
    }
}