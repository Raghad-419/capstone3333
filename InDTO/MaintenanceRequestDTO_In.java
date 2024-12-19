package com.example.capston3.InDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequestDTO_In {

    @NotEmpty(message = "Empty expert name")
    private String expert_name;

    private Integer motorcycle_id;

    private Integer owner_id;

    @NotNull(message = "Empty pickup Date ")
    private LocalDate pickupDate;

}
