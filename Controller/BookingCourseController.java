package com.example.capston3.Controller;


import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.Model.BookingCourse;
import com.example.capston3.Service.BookingCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking-course")
@RequiredArgsConstructor
public class BookingCourseController {
    private final BookingCourseService bookingCourseService;

    @GetMapping("/get")
    public ResponseEntity getBookingCourses() {
        return ResponseEntity.status(200).body(bookingCourseService.getAllBookingCourses());
    }

    @PostMapping("/add/{user_id}/{course_id}")
    public ResponseEntity bookingCourse(@PathVariable Integer user_id, @PathVariable Integer course_id, @RequestBody @Valid BookingCourse bookingCourse) {
        bookingCourseService.bookingCourse(user_id,course_id,bookingCourse);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Course added"));
    }

    @PutMapping("/update/{bookingCourse_id}")
    public ResponseEntity updateBookingCourse(@PathVariable Integer bookingCourse_id,@RequestBody @Valid BookingCourse bookingCourse) {
        bookingCourseService.updateBookingCourse(bookingCourse_id,bookingCourse);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Course updated"));
    }

    @DeleteMapping("/delete/{bookingCourse_id}")
    public ResponseEntity deleteBookingCourse(@PathVariable Integer bookingCourse_id) {
        bookingCourseService.deleteBookingCourse(bookingCourse_id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Course deleted"));
    }
}
