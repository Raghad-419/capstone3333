package com.example.capston3.Repository;

import com.example.capston3.Model.OwnerEventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerEventRegistrationRepository extends JpaRepository<OwnerEventRegistration, Integer> {
    OwnerEventRegistration findOwnerEventRegistrationById(Integer id);
}
