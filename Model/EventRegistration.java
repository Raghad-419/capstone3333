//package com.example.capston3.Model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class EventRegistration {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(columnDefinition = "varchar(20)")
//    private String status;
//
//
//    @ManyToOne
//    @JsonIgnore
//    private Event event;
//
//    @ManyToOne
//    @JsonIgnore
//    private Owner owner;
//
//    @ManyToOne
//    @JsonIgnore
//    private User user;
//
//
//
//    @ManyToOne
//    @JsonIgnore
//    private Motorcycle motorcycle;
//
//    public EventRegistration(Event event ,User user) {
//        this.event = event;
//        this.user = user;
//
//    }
//
//    public EventRegistration(Event event, Owner owner, Motorcycle motorcycle) {
//        this.event = event;
//        this.owner = owner;
//        this.motorcycle = motorcycle;
//    }
//
//
//    public String getStatus() {
//        return getStatus();
//    }
//
//}
