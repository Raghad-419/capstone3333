package com.example.capston3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Course name is required!")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "Description is required!")
    @Size(max = 500, message = "Description cannot exceed 500 characters!")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @Positive(message = "Price must be a positive number!")
    @NotNull(message = "empty price")
    @Column(columnDefinition = "DOUBLE not null")
    private Double price;

    @Positive(message = "Duration must be a positive number!")
    @Column(columnDefinition = "int not null")
    private Integer duration; //in days


    //Relations

    @ManyToOne
    @JsonIgnore
    private Owner owner;  // The owner who provides the course

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<BookingCourse> bookings;  // set of bookings for this course







}
