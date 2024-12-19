package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.*;
import com.example.capston3.Model.*;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;


    public List<UserOutDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserOutDTO> userOutDTOs = new ArrayList<>();

        for (User user : users) {
            List<UserEventRegistrationOutDTO> userEventRegistrationOutDTOs = new ArrayList<>();
            if (user.getUserEventRegistrations() != null) {
                for (UserEventRegistration er : user.getUserEventRegistrations()) {
                    if (er.getEvent() != null) {
                        userEventRegistrationOutDTOs.add(new UserEventRegistrationOutDTO(er.getStatus(), er.getEvent().getId(), er.getUser().getId()));
                    }
                }
            }

            List<PurchaseDTO> purchaseOutDTOs = new ArrayList<>();
            if (user.getPurchases() != null) {
                for (Purchase p : user.getPurchases()) {
                    Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(p.getMotorcycleId());
                    if (motorcycle != null) {
                        purchaseOutDTOs.add(new PurchaseDTO(
                                p.getPurchaseDate(),
                                new PurchaseUserOutDTO(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getAge(), user.getAddress()),
                                new PurchaseMotorcycleOutDTO(motorcycle.getBrand(), motorcycle.getModel(), motorcycle.getYear(), motorcycle.getPrice(), motorcycle.getColor(), motorcycle.getIsAvailable())));
                    }
                }
            }

            // Prepare RentingRequestOutDTOs
            List<RentingRequestOutDTO> rentingRequestOutDTOs = new ArrayList<>();
            if (user.getRentingRequests() != null) {
                for (RentingRequest r : user.getRentingRequests()) {
                    rentingRequestOutDTOs.add(new RentingRequestOutDTO(r.getRequestDate(), r.getStartDate(), r.getEndDate(), r.getTotalCost()));
                }
            }

            // Prepare BookingCourseOutDTOs
            List<BookingCourseOutDTO> bookingCourseOutDTOs = new ArrayList<>();
            if (user.getBookings() != null) {
                for (BookingCourse bc : user.getBookings()) {
                    bookingCourseOutDTOs.add(new BookingCourseOutDTO(bc.getBookingDate(), bc.getCourseStartDate(), bc.getCourseEndDate()));
                }
            }
            userOutDTOs.add(new UserOutDTO(user.getName(), user.getEmail(), user.getPhoneNumber(), user.getAge(), user.getAddress(), userEventRegistrationOutDTOs, purchaseOutDTOs, rentingRequestOutDTOs, bookingCourseOutDTOs));
        }

        return userOutDTOs;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id,User user) {
        User user1 = userRepository.findUserById(id);
        if (user1 == null){
            throw new ApiException("User not found");
        }
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAge(user.getAge());
        user1.setAddress(user.getAddress());
        userRepository.save(user1);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        userRepository.delete(user);
    }


    //Hashim Baroom
    public List<String> getAllBadge(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (user.getUserEventRegistrations() == null) {
            throw new ApiException("User does not have badge");
        }
        List<String> Badge = new ArrayList<>();
        for(String s:user.getBadges()){
            Badge.add(s);
        }
        return Badge;
    }




}