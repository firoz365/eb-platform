package com.firoz.eb.services.api;

import com.firoz.eb.services.api.dto.CreateServiceRequest;
import com.firoz.eb.services.api.dto.ServiceResponse;
import com.firoz.eb.services.api.dto.UpdateServiceRequest;
import com.firoz.eb.services.application.ServiceService;
import com.firoz.eb.services.application.command.UpdateServiceCommand;
import com.firoz.eb.services.domain.ServiceEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

//    @PreAuthorize("hasAuthority('SCOPE_services:write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse create(@Valid @RequestBody CreateServiceRequest req) {
        ServiceEntity created = service.create(req.name, req.description, req.owner, req.status , req.environment);
        return ServiceResponse.from(created);
    }

//    @PreAuthorize("hasAuthority('SCOPE_services:read')")
    @GetMapping("/{id}")
    public ServiceResponse get(@PathVariable Long id) {
        return ServiceResponse.from(service.get(id));
    }

//    @PreAuthorize("hasAuthority('SCOPE_services:read')")
    @GetMapping
    public List<ServiceResponse> list() {
        return service.list().stream().map(ServiceResponse::from).toList();
    }

//    @PutMapping("/{id}")
    public ServiceResponse update(@PathVariable Long id, @Valid @RequestBody UpdateServiceRequest req) {
        var cmd = UpdateServiceCommand.builder()
                .id(id)
                .name(req.name)
                .description(req.description)
                .owner(req.owner)
                .status(req.status)
                .version(req.version)
                .version(req.version)
                .environment(req.environment)
                .build();
        return ServiceResponse.from(service.update(cmd));
    }

    // Soft delete
    // Usage: DELETE /api/v1/services/{id}?version=3
    @DeleteMapping("/{id}")
    public ServiceResponse delete(@PathVariable Long id, @RequestParam("version") Long version) {
        ServiceEntity deleted = service.softDelete(id, version);
        return ServiceResponse.from(deleted);
    }
}