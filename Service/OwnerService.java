package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.CourseDTO;
import com.example.capston3.DTO.MotorcycleDTO;
import com.example.capston3.DTO.OwnerDTO;
import com.example.capston3.Model.Course;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Owner;
import com.example.capston3.Repository.CourseRepository;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {
//Raghad
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final CourseRepository courseRepository;

//Raghad
    public List<OwnerDTO> getAllOwners() {
        // Step 1: Fetch all owners
        List<Owner> owners = ownerRepository.findAll();

        // Step 2: Map each owner to OwnerDTO
        List<OwnerDTO> ownerDTOList = new ArrayList<>();
        for (Owner owner : owners) {
            // Fetch motorcycles for the owner
            List<Motorcycle> motorcycles = motorcycleRepository.findMotorcycleByOwnerId(owner.getId());

            // Map motorcycles to MotorcycleDTOs
            List<MotorcycleDTO> motorcycleDTOs = motorcycles.stream().map(motorcycle -> new MotorcycleDTO(
                    motorcycle.getBrand(),
                    motorcycle.getModel(),
                    motorcycle.getYear(),
                    motorcycle.getPrice(),
                    motorcycle.getColor(),
                    motorcycle.getIsForSale(),
                    motorcycle.getIsAvailable(),
                    motorcycle.getHasOffer()
            )).collect(Collectors.toList());

            // Fetch courses for the owner
            List<Course> courses = courseRepository.findCoursesByOwnerId(owner.getId());

            // Map courses to CourseDTOs
            List<CourseDTO> courseDTOs = courses.stream().map(course -> new CourseDTO(
                    course.getName(),
                    course.getDescription(),
                    course.getPrice(),
                    course.getDuration()
            )).collect(Collectors.toList());

            // Map Owner to OwnerDTO
            OwnerDTO ownerDTO = new OwnerDTO(
                    owner.getName(),
                    owner.getEmail(),
                    owner.getPhoneNumber(),
                    owner.getAddress(),
                    motorcycleDTOs,
                    courseDTOs
            );

            ownerDTOList.add(ownerDTO);
        }

        return ownerDTOList;
    }


    public void createOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id, Owner owner){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        o.setName(owner.getName());
        o.setEmail(owner.getEmail());
        o.setPhoneNumber(owner.getPhoneNumber());
        o.setPassword(owner.getPassword());

        ownerRepository.save(o);
    }

    public void deleteOwner(Integer id){

        Owner o = ownerRepository.findOwnerById(id);

        if(o == null)
            throw new ApiException("Owner not found!");

        ownerRepository.delete(o);

    }





}
