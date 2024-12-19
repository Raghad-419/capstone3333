package com.example.capston3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
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
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "date")
    private LocalDate purchaseDate = LocalDate.now();

    @Positive(message = "Motorcycle id not valid")
    @Column(columnDefinition = "int not null")
    private Integer motorcycleId;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Owner owner;

    public Purchase(User user, Integer motorcycle_id) {
        this.user = user;
        this.motorcycleId = motorcycle_id;
    }
}
