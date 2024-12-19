package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Renting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "Rental price per day must be a positive number!")
    @Column(columnDefinition = "DOUBLE not null")
    private Double pricePerDay;

    @NotEmpty(message = "Pickup location is required!")
    @Column(columnDefinition = "varchar(20) not null")
    private String pickupLocation;


    // Default is pickup location, so it is not required
    @Column(columnDefinition = "varchar(20) ")
    private String dropOffLocation;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Empty motorcycle id")
    private Integer motorcycleId ;

    //Relations

    @ManyToOne
    @JsonIgnore
    private Owner owner;  // Owner of the motorcycle being rented


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "renting")
    private Set<RentingRequest> rentingRequest;

}