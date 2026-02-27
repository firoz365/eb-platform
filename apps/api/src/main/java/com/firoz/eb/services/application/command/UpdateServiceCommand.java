package com.firoz.eb.services.application.command;

import com.firoz.eb.services.domain.ServiceEnvironment;
import com.firoz.eb.services.domain.ServiceStatus;

public record UpdateServiceCommand(
        Long id,
        String name,
        String description,
        String owner,
        ServiceStatus status,
        Integer version,
        ServiceEnvironment environment
) {
    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Long id;
        private String name;
        private String description;
        private String owner;
        private ServiceStatus status;
        private Integer version;
        private ServiceEnvironment environment;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder owner(String owner) { this.owner = owner; return this; }
        public Builder status(ServiceStatus status) { this.status = status; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder environment(ServiceEnvironment environment) { this.environment = environment; return this; }

        public UpdateServiceCommand build() {
            return new UpdateServiceCommand(id, name, description, owner, status, version , environment);
        }
    }
}