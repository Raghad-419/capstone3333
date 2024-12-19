package com.example.capston3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventRegistrationOutDTO {
    private String status;

    private Integer eventId;

    private Integer userId;

}
