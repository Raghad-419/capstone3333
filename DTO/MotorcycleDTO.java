package com.example.capston3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotorcycleDTO {

    private String brand;

    private String model;

    private Integer year;

    private Double price;

    private String color;

    private Boolean isAvailable;
    private Boolean isForSale;
    private Boolean hasOffer;

}
