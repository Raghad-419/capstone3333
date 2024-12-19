package com.example.capston3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MaintenanceExpertDTO {
    private String name;
    private String email;
    private String specialty;
    private Boolean isApproved;
    private String description;

}
