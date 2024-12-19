package com.example.capston3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class BookingCourseOutDTO {

    private LocalDate bookingDate;

    private LocalDate courseStartDate;

    private LocalDate courseEndDate;
}
