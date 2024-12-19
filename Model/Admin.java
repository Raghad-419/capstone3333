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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "Empty name")
    private String name;
    @Column(columnDefinition = "varchar(35) not null unique")
    @NotEmpty(message = "Empty email")
    @Email(message = "Enter valid email")
    private String email;
    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "Empty password")
    @Size(min = 8,message = "Password should be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*^?&])[A-Za-z\\d@$!%*^?&]{8,}$", message = "Password must be at least 8 characters long and contain one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;


}
