package com.example.capston3.Repository;

import com.example.capston3.Model.BookingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface BookingCourseRepository extends JpaRepository<BookingCourse,Integer> {
    BookingCourse findBookingCourseById(Integer id);

//Raghad
    @Query("SELECT COUNT(b) > 0 FROM BookingCourse b " +
            "WHERE b.course.owner.id = :ownerId " +
            "AND (:startDate BETWEEN b.courseStartDate AND b.courseEndDate " +
            "OR :endDate BETWEEN b.courseStartDate AND b.courseEndDate)")
    boolean isTrainerUnavailable(
            @Param("ownerId") Integer ownerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    @Query("SELECT b FROM BookingCourse b WHERE b.user.id = :userId AND b.course.id = :courseId")
    List<BookingCourse> findByUserIdAndCourseId(Integer userId, Integer courseId);


}
