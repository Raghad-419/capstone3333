package com.example.capston3.Repository;

import com.example.capston3.Model.Course;
import com.example.capston3.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);
    List<Course> findCoursesByOwnerId(Integer id);

//Raghad
    @Query("SELECT c FROM Course c " +
            "WHERE (:minPrice IS NULL OR c.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR c.price <= :maxPrice) " +
            "AND (:minDuration IS NULL OR c.duration >= :minDuration) " +
            "AND (:maxDuration IS NULL OR c.duration <= :maxDuration) " )
    List<Course> filterCourses(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration
    );


    @Query("SELECT c FROM Course c ORDER BY SIZE(c.bookings) DESC")
    List<Course> findTopCoursesByBookingCount();
}
