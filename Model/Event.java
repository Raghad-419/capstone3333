package com.example.capston3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(60) not null")
    @NotEmpty(message = "Empty name")
    private String name;
    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "Empty location")
    private String location;
    @Column(columnDefinition = "varchar(200) not null")
    @NotEmpty(message = "Empty details")
    private String details;
    @Column(columnDefinition = "DATE not null")
    @NotNull(message = "Empty Date")
    private LocalDate date;


    @ManyToOne
    @JsonIgnore
    private Company company;
//
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "event")
//    private Set<EventRegistration> eventRegistrations;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "event")
    private Set<UserEventRegistration> userEventRegistrations;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "event")
    private Set<OwnerEventRegistration> ownerEventRegistrations;

}
