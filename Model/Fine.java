package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//Raghad
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "Empty description ")
    private String description;
    @Column(columnDefinition = "DOUBLE not null")
    private Double amount;
    @Column(columnDefinition = "BOOLEAN default false")
    private Boolean isPaid = false; // Indicates if the fine has been paid





    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToOne
    @JsonIgnore
    private RentingRequest rentingRequest;




}
