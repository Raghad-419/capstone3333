package com.example.capston3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserOutDTO {

    private String name;

    private String email;

    private String phoneNumber;

    private Integer age;

    private String address;


    //private List<EventRegistrationDTO> eventRegistrations;

    private List<UserEventRegistrationOutDTO> eventRegistrations;

    private List<PurchaseDTO> purchases;

    private List<RentingRequestOutDTO> rentingRequests;

    private List<BookingCourseOutDTO> bookingCourses;

}
