package com.example.capston3.Controller;

import com.example.capston3.ApiResponse.ApiResponse;
import com.example.capston3.Model.Admin;
import com.example.capston3.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity getAllAdmins(){
        return ResponseEntity.status(200).body(adminService.getAllAdmins());
    }

    @PostMapping("/add")
    public ResponseEntity addAdmin(@RequestBody @Valid Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable Integer id ,@RequestBody @Valid Admin admin){
        adminService.updateAdmin(id,admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin updated"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("Admin deleted"));

    }

    @PutMapping("/approveCompany/{adminId}/{companyId}")
    public ResponseEntity  approveCompany(@PathVariable Integer adminId,@PathVariable Integer companyId){
        adminService.approveCompany(adminId,companyId);
        return ResponseEntity.status(200).body(new ApiResponse("Company Approved"));
    }

    @PutMapping("/approveExpert/{adminId}/{expertId}")
    public ResponseEntity approveExpert(@PathVariable Integer adminId, @PathVariable Integer expertId){
        adminService.approveExpert(adminId,expertId);
        return ResponseEntity.status(200).body(new ApiResponse("Maintenance Expert Approved"));

    }

}
