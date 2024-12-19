package com.example.capston3.Controller;


import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.DTO.OwnerDTO;
import com.example.capston3.Model.Owner;
import com.example.capston3.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/get")
    public ResponseEntity getAllOwners() {
        List<OwnerDTO> ownerDTOS = ownerService.getAllOwners();
        return ResponseEntity.status(200).body(ownerDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity createOwner( @RequestBody @Valid Owner owner) {
        ownerService.createOwner(owner);
        return ResponseEntity.status(200).body(new ApiResponse("Owner added successfully!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOwner(@PathVariable Integer id, @RequestBody @Valid Owner owner){

        ownerService.updateOwner(id, owner);
        return ResponseEntity.status(200).body(new ApiResponse("Owner updated successfully!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOwner(@PathVariable Integer id){
        ownerService.deleteOwner(id);
        return ResponseEntity.status(200).body(new ApiResponse("Owner deleted successfully!"));

    }

















}
