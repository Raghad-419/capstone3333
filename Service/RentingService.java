package com.example.capston3.Service;


import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.MotorcycleDTO;
import com.example.capston3.DTO.RentingOutDTO;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Owner;
import com.example.capston3.Model.Renting;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.OwnerRepository;
import com.example.capston3.Repository.RentingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentingService {
//Raghad
    private final RentingRepository rentingRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleService motorcycleService;
    private final MotorcycleRepository motorcycleRepository;

    public List<RentingOutDTO> getAllRentings(){

        List<Renting> rentings = rentingRepository.findAll();

        List<RentingOutDTO> rentingDTOS = new ArrayList<>();


        for(Renting renting : rentings){
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(renting.getMotorcycleId());
            MotorcycleDTO motorcycleDTO = new MotorcycleDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable(),motorcycle.getIsForSale(),motorcycle.getHasOffer());
            RentingOutDTO rentingDTO = new RentingOutDTO(renting.getPricePerDay(), renting.getPickupLocation(), renting.getDropOffLocation(), motorcycleDTO);

            rentingDTOS.add(rentingDTO);
        }
        return rentingDTOS;
    }

    public void addRenting(Integer owner_id, Renting renting) {

        Owner owner = ownerRepository.findOwnerById(owner_id);

        if (owner == null)
            throw new ApiException("Owner not found!");

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(renting.getMotorcycleId());
        if(!owner.getMotorcycles().contains(motorcycle)){
            throw new ApiException("Owner didn't have this motorcycle");
        }

        renting.setOwner(owner);
        rentingRepository.save(renting);
    }


    public void updateRenting(Integer id, Renting renting) {

        Renting r = rentingRepository.findRentingById(id);

        if (r == null)
            throw new ApiException("Renting not found!");

        r.setPricePerDay(renting.getPricePerDay());
        r.setPickupLocation(renting.getPickupLocation());
        r.setDropOffLocation(renting.getDropOffLocation());
        rentingRepository.save(r);
    }

    public void deleteRenting(Integer id){

        Renting renting = rentingRepository.findRentingById(id);
        if(renting == null)
            throw new ApiException("Renting not found!");

        rentingRepository.delete(renting);

    }










}