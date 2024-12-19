package com.example.capston3.Controller;


import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.DTO.MotorcycleDTO;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Service.MotorcycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @GetMapping("/get")
    public ResponseEntity getAllMotorcycles(){
        List<MotorcycleDTO> motorcycleDTOS = motorcycleService.getAllMotorcycles();
        return ResponseEntity.status(200).body(motorcycleDTOS);
    }



    @PostMapping("/add-by-owner/{owner_id}")
    public ResponseEntity addMotorcycleByOwner(@PathVariable Integer owner_id, @RequestBody @Valid Motorcycle motorcycle) {

        motorcycleService.addMotorcycleByOwner(owner_id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle added successfully!"));
    }

    @PostMapping("/add-by-company/{company_id}")
    public ResponseEntity addMotorcycleByCompany(@PathVariable Integer company_id, @RequestBody @Valid Motorcycle motorcycle) {

        motorcycleService.addMotorcycleByCompany(company_id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle added successfully!"));
    }




    @PutMapping("/update/{id}")
    public ResponseEntity updateMotorcycle(@PathVariable Integer id, @RequestBody @Valid Motorcycle motorcycle){
        motorcycleService.updateMotorcycle(id, motorcycle);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle updated successfully!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMotorcycle(@PathVariable Integer id){

        motorcycleService.deleteMotorcycle(id);
        return ResponseEntity.status(200).body(new ApiResponse("Motorcycle deleted successfully!"));

    }

    @GetMapping("/filter-price/{minPrice}/{maxPrice}")
    public ResponseEntity filterMotorcyclesByPrice(
            @PathVariable Double minPrice,
            @PathVariable Double maxPrice) {

        List<Motorcycle> filteredMotorcycles = motorcycleService.filterByPrice(minPrice, maxPrice);
        return ResponseEntity.ok(filteredMotorcycles);
    }

    @GetMapping("/getAvailableMotorcycles")
    public ResponseEntity getAvailableMotorcycles(){
        return ResponseEntity.status(200).body(motorcycleService.getAvailableMotorcycles());
    }

    @GetMapping("/getMotorcyclesForSale")
    public ResponseEntity getMotorcyclesForSale(){
        return ResponseEntity.status(200).body(motorcycleService.getMotorcyclesForSale());
    }

    @PutMapping("/changeForSaleStatus/{id}/{ownerId}/{forSale}/{price}")
    public ResponseEntity changeForSaleStatus(@PathVariable Integer id,@PathVariable Integer ownerId,@PathVariable Boolean forSale,@PathVariable Double price){
        motorcycleService.changeForSaleStatus(id,ownerId,forSale,price);
        return ResponseEntity.status(200).body(new ApiResponse("Status changes"));

    }


    @PutMapping("/change-available-status/{id}/{owner_id}/{isAvailable}")
    public ResponseEntity changeAvailableStatus(@PathVariable Integer id,@PathVariable Integer owner_id,@PathVariable Boolean isAvailable){
        motorcycleService.changeAvailableStatus(id,owner_id,isAvailable);
        return ResponseEntity.status(200).body(new ApiResponse("Available Status changed!"));
    }

    @PutMapping("/discount-motorcycle/{motorcycle_id}/{owner_id}/{discountRate}")
    public ResponseEntity discountMotorcycle(@PathVariable Integer motorcycle_id,@PathVariable Integer owner_id,@PathVariable Double discountRate){
        motorcycleService.discountMotorcycle(motorcycle_id,owner_id,discountRate);
        return ResponseEntity.status(200).body(new ApiResponse("Discount motorcycle added"));
    }

    @GetMapping("/average-price-for-same-brand-and-year/{brand}/{year}")
    public ResponseEntity CalculateAveragePriceForSameBrandAndYear(@PathVariable String brand,@PathVariable Integer year){
        return ResponseEntity.status(200).body(motorcycleService.CalculateAveragePriceForSameBrandAndYear(brand,year));
    }

    @GetMapping("/by-brand-and-model/{brand}/{model}")
    public ResponseEntity byBrandAndModel(@PathVariable String brand,@PathVariable String model){
        return ResponseEntity.status(200).body(motorcycleService.byBrandAndModel(brand,model));
    }



}
