package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RentingRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "date ")
    private LocalDate requestDate = LocalDate.now();

    @Column(columnDefinition = "date not null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @Column(columnDefinition = "date not null")
   @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Column(columnDefinition = "int")
    private Integer totalCost;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Empty motorcycle id")
    private Integer motorcycleId ;

    @Column(columnDefinition = "boolean ")
    private Boolean isReturned;

    public RentingRequest(LocalDate startDate, LocalDate endDate, User user, Renting renting,Integer motorcycleId) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.renting = renting;
        this.motorcycleId=motorcycleId;
    }


    @ManyToOne
    @JsonIgnore
    private Renting renting;

    @ManyToOne
    @JsonIgnore
    private User user;

   @OneToOne(cascade = CascadeType.ALL)
   @PrimaryKeyJoinColumn
    private Fine fine;





}