package com.example.capston3.Controller;

import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.Model.MaintenanceExpert;
import com.example.capston3.Service.MaintenanceExpertService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expert")
public class MaintenanceExpertController {
    private final MaintenanceExpertService maintenanceExpertService;

    @GetMapping("/get")
    public ResponseEntity getMaintenanceExpert(){
        return ResponseEntity.status(200).body(maintenanceExpertService.getMaintenanceExpert());
    }

    @PostMapping("/add")
    public ResponseEntity addMaintenanceExpert(@RequestBody @Valid MaintenanceExpert maintenanceExpert){
        maintenanceExpertService.addMaintenanceExpert(maintenanceExpert);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMaintenanceExpert(@PathVariable Integer id,@RequestBody @Valid MaintenanceExpert maintenanceExpert){
        maintenanceExpertService.updateMaintenanceExpert(id,maintenanceExpert);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMaintenanceExpert(@PathVariable Integer id){
        maintenanceExpertService.deleteMaintenanceExpert(id);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert Deleted"));
    }



}
