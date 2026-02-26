package com.firoz.eb.services.api.dto;
import com.firoz.eb.services.domain.ServiceEntity;
import com.firoz.eb.services.domain.ServiceStatus;

import java.time.Instant;

public class ServiceResponse {
    public Long id;
    public String name;
    public String description;
    public String owner;
    public ServiceStatus status;
    public Long version;
    public String createdBy;
    public Instant createdAt;
    public String updatedBy;
    public Instant updatedAt;

    public static ServiceResponse from(ServiceEntity e) {
        ServiceResponse r = new ServiceResponse();
        r.id = e.getId();
        r.name = e.getName();
        r.description = e.getDescription();
        r.owner = e.getOwner();
        r.status = e.getStatus();
        r.version = e.getVersion();
        r.createdBy = e.getCreatedBy();
        r.createdAt = e.getCreatedAt();
        r.updatedBy = e.getUpdatedBy();
        r.updatedAt = e.getUpdatedAt();
        return r;
    }
}