package com.example.capston3.Repository;

import com.example.capston3.Model.MaintenanceExpert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceExpertRepository extends JpaRepository<MaintenanceExpert,Integer> {
    MaintenanceExpert findMaintenanceExpertById(Integer id);
    //Hashim Baroom
    MaintenanceExpert findMaintenanceExpertByName(String name);
}
