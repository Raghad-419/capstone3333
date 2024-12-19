//package com.example.capston3.Service;
//
//
//import com.example.capston3.ApiResponse.ApiException;
//import com.example.capston3.DTO.EventRegistrationDTO;
//import com.example.capston3.Model.*;
//import com.example.capston3.Repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EventRegistrationService {
//
//    private final EventRegistrationRepository eventRegistrationRepository;
//    private final UserRepository userRepository;
//    private final EventRepository eventRepository;
//    private final OwnerRepository ownerRepository;
//    private final MotorcycleRepository motorcycleRepository;
//
//    public List<EventRegistrationDTO> getEventRegistrations() {
//        List<EventRegistration> eventRegistrations = eventRegistrationRepository.findAll();
//        List<EventRegistrationDTO> eventRegistrationOutDTOs = new ArrayList<>();
//        for (EventRegistration eventRegistration : eventRegistrations) {
//            eventRegistrationOutDTOs.add(new EventRegistrationDTO(eventRegistration.getEvent().getId(),eventRegistration.getOwner().getId(), eventRegistration.getUser().getId(), eventRegistration.getMotorcycle().getId(),eventRegistration.getStatus()));
//        }
//        return eventRegistrationOutDTOs;
//    }
//    public void UserRegistration(Integer user_id,Integer event_id) {
//        Event event = eventRepository.findEventById(event_id);
//        User user = userRepository.findUserById(user_id);
//        EventRegistration eventRegistration = new EventRegistration(event ,user);
//
//        eventRegistration.setStatus("visitor");
//        eventRegistrationRepository.save(eventRegistration);
//    }
//
//    public void OwnerRegistration(Integer owner_id,Integer event_id,Integer motorcycle_id) {
//        Event event = eventRepository.findEventById(event_id);
//        Owner owner = ownerRepository.findOwnerById(owner_id);
//        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(motorcycle_id);
//        EventRegistration eventRegistration = new EventRegistration(event ,owner,motorcycle);
//        eventRegistration.setStatus("owner");
//        eventRegistrationRepository.save(eventRegistration);
//    }
//
//
//
//
//
//
//
//}