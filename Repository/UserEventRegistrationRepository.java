package com.example.capston3.Repository;

import com.example.capston3.Model.UserEventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRegistrationRepository extends JpaRepository<UserEventRegistration, Integer> {
    UserEventRegistration findUserEventRegistrationById(Integer id);
}
