package com.example.capston3.Service;

import com.example.capston3.DTO.UserEventRegistrationOutDTO;
import com.example.capston3.Model.Event;
import com.example.capston3.Model.User;
import com.example.capston3.Model.UserEventRegistration;
import com.example.capston3.Repository.EventRepository;
import com.example.capston3.Repository.UserEventRegistrationRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEventRegistrationService {
    private final UserEventRegistrationRepository userEventRegistrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    @Autowired
    private EmailService emailNotificationService;

    public List<UserEventRegistrationOutDTO> getAllUserEventRegistrations() {
        List<UserEventRegistration> userEventRegistrations = userEventRegistrationRepository.findAll();
        List<UserEventRegistrationOutDTO> userEventRegistrationOutDTOs = new ArrayList<>();
        for (UserEventRegistration userEventRegistration : userEventRegistrations) {
            userEventRegistrationOutDTOs.add(new UserEventRegistrationOutDTO(userEventRegistration.getStatus(),userEventRegistration.getEvent().getId(),userEventRegistration.getUser().getId()));
        }
        return userEventRegistrationOutDTOs;
    }

    public void UserRegistration(Integer user_id,Integer event_id) {
        Event event = eventRepository.findEventById(event_id);
        User user = userRepository.findUserById(user_id);
        UserEventRegistration userEventRegistration = new UserEventRegistration("Visitor",event ,user);
        userEventRegistrationRepository.save(userEventRegistration);
    }


    @Scheduled(fixedRate = 18000000)
    public void sendReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Event> events = eventRepository.findAllByDate(tomorrow);
        for (Event event : events) {
            for (UserEventRegistration registration : event.getUserEventRegistrations()) {
                User user = registration.getUser();
                if (user != null) {
                    String emailMessage = "Reminder: You are registered for the event "
                            + event.getName() + " which will take place at "
                            + event.getLocation() + " on " + event.getDate() + ".";
                    emailNotificationService.sendEmail(user.getEmail(), "Event Reminder", emailMessage);
                }
            }
        }
    }
}

