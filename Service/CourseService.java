package com.example.capston3.Service;
import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.CourseDTO;
import com.example.capston3.Model.BookingCourse;
import com.example.capston3.Model.Course;
import com.example.capston3.Model.Owner;
import com.example.capston3.Model.User;
import com.example.capston3.Repository.BookingCourseRepository;
import com.example.capston3.Repository.CourseRepository;
import com.example.capston3.Repository.OwnerRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final BookingCourseRepository bookingCourseRepository;
    private final EmailService emailService;

    public List<CourseDTO> getAllCourses(){

            List<Course> courses = courseRepository.findAll();

            List<CourseDTO> courseDTOS = new ArrayList<>();

            for(Course course : courses){
                CourseDTO courseDTO = new CourseDTO(course.getName(), course.getDescription(), course.getPrice(), course.getDuration());

                courseDTOS.add(courseDTO);
            }
            return courseDTOS;
        }

        public void addCourse(Integer owner_id, Course course) {

            Owner owner = ownerRepository.findOwnerById(owner_id);

            if (owner == null)
                throw new ApiException("Owner not found!");


            //assign course to one owner
            course.setOwner(owner);
            courseRepository.save(course);
        }


        public void updateCourse(Integer id, Course course) {

            Course c = courseRepository.findCourseById(id);
            if (c == null)
                throw new ApiException("Course not found!");

            c.setName(course.getName());
            c.setDescription(c.getDescription());
            c.setPrice(course.getPrice());
            c.setDuration(course.getDuration());
            courseRepository.save(c);
        }

        public void deleteCourse(Integer id){

            Course course = courseRepository.findCourseById(id);
            if(course == null)
                throw new ApiException("Course not found!");

            courseRepository.delete(course);

        }




//Raghad
    public List<CourseDTO> filterCourses(Double minPrice, Double maxPrice, Integer minDuration, Integer maxDuration) {
        // Fetch filtered courses from the repository
        List<Course> courses = courseRepository.filterCourses(minPrice, maxPrice, minDuration, maxDuration);

        // Map courses to CourseDTOs
        return courses.stream().map(course -> new CourseDTO(
                course.getName(),
                course.getDescription(),
                course.getPrice(),
                course.getDuration() // Include trainer name in the DTO
        )).collect(Collectors.toList());
    }



    //Durrah
    public String awardBadgeToUser(Integer userId, Integer courseId) {

        // Fetch the booking record for the user and course
        List<BookingCourse> bookingCourses = bookingCourseRepository.findByUserIdAndCourseId(userId, courseId);

        if (bookingCourses.isEmpty()) {
            throw new ApiException("No booking found for this user and course!");
        }

        BookingCourse booking = bookingCourses.get(0);

        // Validate if course is completed (check if courseEndDate is in the past)
        if (booking.getCourseEndDate() == null || !LocalDate.now().isAfter(booking.getCourseEndDate())) {
            throw new ApiException("Course is not completed yet or courseEndDate is missing!");
        }

        // Check if the user already has the badge
        User user = booking.getUser();
        String badge = "***Completed Course*** : " + booking.getCourse().getName();

        // If badges list is null, initialize it
        if (user.getBadges() == null) {
            user.setBadges(new HashSet<>());
        }

        // If the badge is already present, return message
        if (user.getBadges().contains(badge)) {
            return "User already has this badge!";
        }

        // Award badge
        user.getBadges().add(badge);

        userRepository.save(user);

        return "Badge awarded successfully!";
    }


    //Durrah
    public String giftCourseToUser(Integer giverId, Integer receiverId, Integer courseId) {

        // Fetch users and course
        User giver = userRepository.findUserById(giverId);
        if (giver == null)
            throw new ApiException("Giver not found!");

        User receiver = userRepository.findUserById(receiverId);
        if (receiver == null)
            throw new ApiException("Receiver not found!");

        Course course = courseRepository.findCourseById(courseId);
        if (course == null)
            throw new ApiException("Course not found!");

        // Check if the receiver has already been gifted this course
        for (BookingCourse booking : receiver.getBookings()) {
            if (booking.getCourse().getId().equals(courseId)) {
                throw new ApiException("Receiver already has this course!");
            }
        }

        // Create a new booking for the receiver
        BookingCourse newBooking = new BookingCourse();
        newBooking.setCourse(course);
        newBooking.setUser(receiver);  // Set receiver as the user
        newBooking.setCourseStartDate(LocalDate.now());
        newBooking.setCourseEndDate(LocalDate.now().plusDays(course.getDuration()));  // Assuming course duration in days

        // Save
        bookingCourseRepository.save(newBooking);

        // Send email to both giver and receiver
        sendGiftNotificationEmail(giver, receiver, course);

        return "Course gifted successfully!";
    }

    // Helper method
    private void sendGiftNotificationEmail(User giver, User receiver, Course course) {
        // Email to giver
        String giverEmailContent = "Dear " + giver.getName() + ",\n\n" +
                "You have successfully gifted the course \"" + course.getName() + "\" to " + receiver.getName() + ".\n" +
                "The receiver has been notified about the gift and will be able to start the course soon.\n\n" +
                "Thank you for your generosity!\n\n" +
                "Best regards.\n";

        // Email  receiver
        String receiverEmailContent = "Dear " + receiver.getName() + ",\n\n" +
                "You have received a wonderful gift! " + giver.getName() + " has gifted you the course \"" + course.getName() + "\".\n" +
                "You can attend the course as soon as possible!\n\n" +
                "Enjoy learning!\n\n" +
                "Best regards.\n" ;

        // Send email to the giver
        emailService.sendEmail(giver.getEmail(), "Course Gift Confirmation!", giverEmailContent);

        // Send email to the receiver
        emailService.sendEmail(receiver.getEmail(), "You've Received a Course Gift!", receiverEmailContent);
    }



    //Durrah
    public List<Course> getPopularCourses() {

        // Fetch all courses with their associated bookings, ordered by booking count
        return courseRepository.findTopCoursesByBookingCount();
    }





    //Hashim Baroom
    public List<Course> upcomingCourses(Integer userId){
        List<Course> courses = new ArrayList<>();
        User user = userRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("User not found!");
        }
        List<Course> allCourses = courseRepository.findAll();
        for(Course course : allCourses){
            Set<BookingCourse> allBookingCourses = course.getBookings();
            for(BookingCourse bookingCourse : allBookingCourses){
                if (bookingCourse.getUser().getId() == user.getId()){
                    if (bookingCourse.getCourseStartDate().isAfter(LocalDate.now())){
                        courses.add(course);
                    }
                }
            }
        }
        return courses;
    }


}



