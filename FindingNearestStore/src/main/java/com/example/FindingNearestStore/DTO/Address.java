package com.example.FindingNearestStore.DTO;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@MappedSuperclass
public class Address {
    @NotNull(message = "City field must not be empty")
    private String city;
    @NotNull(message = "state field must not be empty")
    private String state;
    @NotNull(message = "country field must not be empty")
    private String country;
    @NotNull(message = "door field must not be empty")
    private String door;
    @NotNull(message = "Street field must not be empty")
    private String street;
}
