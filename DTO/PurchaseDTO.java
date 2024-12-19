package com.example.capston3.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class PurchaseDTO {
    private LocalDate purchaseDate ;

    private PurchaseUserOutDTO user;

    private PurchaseMotorcycleOutDTO motorcycle;
}
