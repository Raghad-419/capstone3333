package com.example.capston3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Owner name is required!")
    @Size(max = 15, message = "Owner name cannot exceed 15 characters")
    @Column(columnDefinition = "varchar(15) not null")
    private String name;

    @Email(message = "Email should be valid format!")
    @NotEmpty(message = "Email is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String email;

    @NotEmpty(message = "Password is required!")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @Column(columnDefinition = "varchar(20) not null")
    private String password;


    @NotEmpty(message = "Phone number is required!")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @NotEmpty(message = "Owner address is required!")
    @Size(max = 20, message = "Address cannot exceed 20 characters!")
    @Column(columnDefinition = "varchar(20) not null")
    private String address; // Owner's physical address

    //Relations

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Course> courses; // Courses offered by the owner

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Motorcycle> motorcycles; // set of motorcycles owned by the owner

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Renting> rentals; // List of rentals

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<MaintenanceRequest> maintenanceRequests; //Maintenance requests made by the owner

//    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
//    private Set<EventRegistration> eventRegistrations; // Events the owner has registered for

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<OwnerEventRegistration> OwnerEventRegistrations; // Events the owner has registered for

    @OneToMany(mappedBy = "owner" ,cascade = CascadeType.ALL)
    private Set<Purchase> purchases;






}