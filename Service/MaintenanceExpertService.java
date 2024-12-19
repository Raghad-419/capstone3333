package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.MaintenanceExpertDTO;
import com.example.capston3.Model.Admin;
import com.example.capston3.Model.MaintenanceExpert;
import com.example.capston3.Repository.MaintenanceExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceExpertService {


    private final MaintenanceExpertRepository maintenanceExpertRepository;

    public List<MaintenanceExpertDTO> getMaintenanceExpert(){
        List<MaintenanceExpert> maintenanceExperts = maintenanceExpertRepository.findAll();
        List<MaintenanceExpertDTO> maintenanceExpertDTOS =new ArrayList<>();

        for(MaintenanceExpert maintenanceExpert:maintenanceExperts){
            MaintenanceExpertDTO maintenanceExpertDTO = new MaintenanceExpertDTO(maintenanceExpert.getName(),maintenanceExpert.getEmail(),maintenanceExpert.getSpecialty(),maintenanceExpert.getIsApproved(),maintenanceExpert.getDescription());
            maintenanceExpertDTOS.add(maintenanceExpertDTO);
        }
        return maintenanceExpertDTOS;
    }


    public void addMaintenanceExpert(MaintenanceExpert maintenanceExpert ){
        maintenanceExpertRepository.save(maintenanceExpert);
    }

    public void updateMaintenanceExpert(Integer id ,MaintenanceExpert maintenanceExpert){
        MaintenanceExpert maintenanceExpert1 = maintenanceExpertRepository.findMaintenanceExpertById(id);
        if(maintenanceExpert1==null){
            throw new ApiException("Maintenance Expert Not found");
        }
        maintenanceExpert1.setDescription(maintenanceExpert.getDescription());
        maintenanceExpert1.setName(maintenanceExpert.getName());
        maintenanceExpert1.setEmail(maintenanceExpert.getEmail());
        maintenanceExpert1.setSpecialty(maintenanceExpert1.getSpecialty());
        maintenanceExpert1.setIsApproved(maintenanceExpert.getIsApproved());

        maintenanceExpertRepository.save(maintenanceExpert1);
    }

    public void deleteMaintenanceExpert(Integer id){
        MaintenanceExpert maintenanceExpert = maintenanceExpertRepository.findMaintenanceExpertById(id);
        if(maintenanceExpert==null){
            throw new ApiException("Maintenance Expert Not found");
        }
        maintenanceExpertRepository.delete(maintenanceExpert);
    }






}