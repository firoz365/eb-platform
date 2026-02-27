package com.firoz.eb.services.application;

import com.firoz.eb.services.application.command.UpdateServiceCommand;
import com.firoz.eb.services.domain.ServiceEntity;
import com.firoz.eb.services.domain.ServiceEnvironment;
import com.firoz.eb.services.domain.ServiceStatus;
import com.firoz.eb.services.persistence.ServiceRepository;
import com.firoz.eb.shared.exception.DuplicateServiceNameException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServiceEntity create(String name, String description, String owner, ServiceStatus status , ServiceEnvironment environment) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new DuplicateServiceNameException(name);
        }
        ServiceEntity s = new ServiceEntity();
        s.setName(name);
        s.setDescription(description);
        s.setOwner(owner);
        s.setStatus(status == null ? ServiceStatus.ACTIVE : status);
        s.setEnvironment(environment == null ? ServiceEnvironment.DEV : environment);
        return repository.save(s);
    }

    @Transactional(readOnly = true)
    public ServiceEntity get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<ServiceEntity> list() {
        return repository.findAll();
    }

    @Transactional
    public ServiceEntity update(UpdateServiceCommand cmd) {

        ServiceEntity existing = repository.findById(cmd.id())
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + cmd.id()));

        if (cmd.version() == null || !cmd.version().equals(existing.getVersion())) {
            throw new OptimisticLockingFailureException(
                    "Version mismatch. Current=" + existing.getVersion() + ", provided=" + cmd.version()
            );
        }

        if (!existing.getName().equalsIgnoreCase(cmd.name()) && repository.existsByNameIgnoreCase(cmd.name())) {
            throw new DuplicateServiceNameException(cmd.name());
        }

        existing.setName(cmd.name());
        existing.setDescription(cmd.description());
        existing.setOwner(cmd.owner());
        existing.setStatus(cmd.status());
        existing.setEnvironment(cmd.environment());

        return repository.save(existing);
    }

    @Transactional
    public ServiceEntity softDelete(Long id, Long expectedVersion) {
        ServiceEntity existing = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Service not found: " + id));

        if (expectedVersion == null || !expectedVersion.equals(existing.getVersion())) {
            throw new OptimisticLockingFailureException(
                "Version mismatch. Current=" + existing.getVersion() + ", provided=" + expectedVersion
            );
        }

        existing.setStatus(ServiceStatus.DELETED);
        return repository.save(existing);
    }
}