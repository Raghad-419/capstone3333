package com.example.capston3.Repository;

import com.example.capston3.Model.RentingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentingRequestRepository extends JpaRepository<RentingRequest,Integer> {
    RentingRequest findRentingRequestById(Integer id);

    //Raghad
    @Query("SELECT r FROM RentingRequest r WHERE r.endDate < :currentDate")
    List<RentingRequest> findOverdueRentals(@Param("currentDate") LocalDate currentDate);

}
