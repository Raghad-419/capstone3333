package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Brand is required!")
    @Size(max = 20, message = "Brand name cannot exceed 20 characters!")
    @Column(columnDefinition = "varchar(20) not null")
    private String brand; // e.g., Yamaha, Honda

    @NotEmpty(message = "Model is required!")
    @Size(max = 15, message = "Model cannot exceed 15 characters!")
    @Column(columnDefinition = "varchar(15) not null")
    private String model;

    @NotNull(message = "Empty Year of manufacture ")
    @Min(value = 1900, message = "Year must be valid")
    @Max(value = 2025, message = "Year must be valid")
    @Column(columnDefinition = "int")
    private Integer year; // Year of manufacture

    @NotNull(message = "Empty price")
    @Positive(message = "Price must be a positive number!")
    @Column(columnDefinition = "DOUBLE not null")
    private Double price=0.0;


    @NotEmpty(message = "Color is required!")
    @Size(max = 10, message = "Color cannot exceed 10 characters!")
    @Column(columnDefinition = "varchar(10) not null")
    private String color;

    @Column(columnDefinition = "BOOLEAN not null")
    private Boolean isAvailable=true;

    @Column(columnDefinition = "BOOLEAN not null default false")
    private Boolean isForSale = false;

    @Column(columnDefinition = "BOOLEAN not null default false")
    Boolean hasOffer = false;

    //Relations

    @ManyToOne
    @JsonIgnore
    private Owner owner;


    @ManyToOne
    @JsonIgnore
    private Company company; // Company that is selling the motorcycle


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "motorcycle")
    private Set<OwnerEventRegistration> ownerEventRegistrations;

}