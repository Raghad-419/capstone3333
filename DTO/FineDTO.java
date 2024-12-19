package com.example.capston3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FineDTO {
    private String description;
    private Double amount;
    private Boolean isPaid;

}
