package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OwnerEventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20)")
    private String status;


    @ManyToOne
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JsonIgnore
    private Owner owner;

    @ManyToOne
    @JsonIgnore
    private Motorcycle motorcycle;

    public OwnerEventRegistration(String status, Event event, Owner owner, Motorcycle motorcycle) {
        this.status = status;
        this.event = event;
        this.owner = owner;
        this.motorcycle = motorcycle;
    }

}
