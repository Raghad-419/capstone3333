package com.example.capston3.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {

    private String name;

    private String description;

    private Double price;

    private Integer duration; //in days

}