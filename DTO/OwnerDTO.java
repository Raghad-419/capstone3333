package com.example.capston3.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OwnerDTO {

    private String name;

    private String email;


    private String phoneNumber;

    private String address;

    private List<MotorcycleDTO> motorcycles;

    private List<CourseDTO> courses;












}