//package com.example.capston3.Controller;
//
//import com.example.capston3.ApiResponse.ApiResponse;
//import com.example.capston3.Model.EventRegistration;
//import com.example.capston3.Service.EventRegistrationService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/event-registration")
//@RequiredArgsConstructor
//public class EventRegistrationController {
//    private final EventRegistrationService eventRegistrationService;
//
//    @GetMapping("/get")
//    public ResponseEntity getEventRegistrations() {
//        return ResponseEntity.status(200).body(eventRegistrationService.getEventRegistrations());
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity UserRegistration(@PathVariable Integer userId,@PathVariable Integer eventId) {
//        eventRegistrationService.UserRegistration(userId,eventId);
//        return ResponseEntity.status(200).body(new ApiResponse("user Registration added"));
//    }
//
//    @PostMapping("/addOwner/{owner_id}/{event_id}/{motorcycle_id}")
//    public ResponseEntity OwnerRegistration(@PathVariable Integer owner_id,@PathVariable Integer event_id,@PathVariable Integer motorcycle_id){
//        eventRegistrationService.OwnerRegistration(owner_id,event_id,motorcycle_id);
//        return ResponseEntity.status(200).body(new ApiResponse("Owner Registration added"));
//
//    }
//
//}