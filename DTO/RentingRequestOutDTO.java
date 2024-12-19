package com.example.capston3.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class RentingRequestOutDTO {
    private LocalDate requestDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalCost;
}
