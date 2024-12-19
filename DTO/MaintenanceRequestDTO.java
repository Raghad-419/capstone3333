package com.example.capston3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequestDTO {
    private LocalDate requestDate = LocalDate.now();

    private Double totalPrice;

    private String expertName;

    private String status;

    private LocalDate pickupDate;

    private MotorcycleDTO motorcycleS;


}
