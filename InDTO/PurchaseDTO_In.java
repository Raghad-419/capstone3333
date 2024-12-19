package com.example.capston3.InDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseDTO_In {

    private Integer motorcycleId;

    private Integer userId;

    private Integer ownerId;


}
