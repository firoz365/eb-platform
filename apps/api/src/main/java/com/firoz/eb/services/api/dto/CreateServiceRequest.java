package com.firoz.eb.services.api.dto;

import com.firoz.eb.services.domain.ServiceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateServiceRequest {

    @NotBlank
    @Size(max = 120)
    public String name;

    @Size(max = 2000)
    public String description;

    @NotBlank
    @Size(max = 200)
    public String owner;

    public ServiceStatus status;
}