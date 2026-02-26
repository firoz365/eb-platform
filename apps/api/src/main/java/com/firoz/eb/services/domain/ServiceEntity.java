package com.firoz.eb.services.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "services",
        indexes = {
                @Index(name = "idx_services_name", columnList = "name", unique = true),
                @Index(name = "idx_services_owner", columnList = "owner"),
                @Index(name = "idx_services_status", columnList = "status")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false, length = 200)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ServiceStatus status = ServiceStatus.ACTIVE;

    @Version
    private Long version;

    @CreatedBy
    @Column(nullable = false, updatable = false, length = 100)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String updatedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public ServiceStatus getStatus() { return status; }
    public void setStatus(ServiceStatus status) { this.status = status; }

    public Long getVersion() { return version; }

    public String getCreatedBy() { return createdBy; }
    public Instant getCreatedAt() { return createdAt; }
    public String getUpdatedBy() { return updatedBy; }
    public Instant getUpdatedAt() { return updatedAt; }
}