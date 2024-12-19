package com.example.capston3.InDTO;


import com.example.capston3.Model.Renting;
import com.example.capston3.Model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RentingRequestDTO_In {


    private Integer renting_id;

    private Integer user_id;

   // @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

  //  @Future(message = "End date must be in the future")
    private LocalDate endDate;

    private Integer motorcycleId ;


}
