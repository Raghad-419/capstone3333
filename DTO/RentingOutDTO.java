package com.example.capston3.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RentingOutDTO {
    private Double pricePerDay;


    private String pickupLocation;


    private String dropOffLocation;

    private MotorcycleDTO motorcycles;




}
