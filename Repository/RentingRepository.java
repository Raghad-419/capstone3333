package com.example.capston3.Repository;

import com.example.capston3.Model.Renting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RentingRepository extends JpaRepository<Renting,Integer> {
    Renting findRentingById(Integer id);

    // Query to check availability of motorcycle for new renting requests
//Raghad
    @Query("SELECT COUNT(r) > 0 FROM RentingRequest r " +
            "WHERE r.renting.motorcycleId = :motorcycleId " +
            "AND (:startDate BETWEEN r.startDate AND r.endDate " +
            "OR :endDate BETWEEN r.startDate AND r.endDate)")
    boolean existsByMotorcycleAndDateRange(
            @Param("motorcycleId") Integer motorcycleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

//Raghad
    // Query to check availability of motorcycle for updates, excluding the current request
    @Query("SELECT COUNT(r) > 0 FROM RentingRequest r " +
            "WHERE r.renting.motorcycleId = :motorcycleId " +
            "AND r.id <> :rentingRequestId " + // Exclude the current renting request
            "AND (:startDate BETWEEN r.startDate AND r.endDate " +
            "OR :endDate BETWEEN r.startDate AND r.endDate)")
    boolean existsByMotorcycleAndDateRangeExcludingRequest(
            @Param("motorcycleId") Integer motorcycleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("rentingRequestId") Integer rentingRequestId
    );
}
