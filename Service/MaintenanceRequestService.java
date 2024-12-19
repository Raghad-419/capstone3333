package com.example.capston3.Service;


import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.MaintenanceRequestDTO;
import com.example.capston3.DTO.MaintenanceRequestHistoryDTO;
import com.example.capston3.DTO.MotorcycleDTO;
import com.example.capston3.InDTO.MaintenanceRequestDTO_In;
import com.example.capston3.Model.MaintenanceExpert;
import com.example.capston3.Model.MaintenanceRequest;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Owner;
import com.example.capston3.Repository.MaintenanceExpertRepository;
import com.example.capston3.Repository.MaintenanceRequestRepository;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final OwnerRepository ownerRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final MaintenanceExpertRepository maintenanceExpertRepository;
    private final EmailService emailService;

    //Raghad
    public List<MaintenanceRequestDTO> getAllMaintenanceRequest() {
        // Step 1: Fetch all maintenance requests
        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        // Step 2: Map each maintenance request to a DTO with its motorcycle details
        return maintenanceRequests.stream().map(maintenanceRequest -> {
            // Fetch motorcycle details
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(maintenanceRequest.getMotorcycle_id());
            if (motorcycle == null) {
                throw new ApiException("Motorcycle not found for MaintenanceRequest ID " + maintenanceRequest.getId());
            }

            // Map motorcycle to MotorcycleDTO
            MotorcycleDTO motorcycleDTO = new MotorcycleDTO(
                    motorcycle.getBrand(),
                    motorcycle.getModel(),
                    motorcycle.getYear(),
                    motorcycle.getPrice(),
                    motorcycle.getColor(),
                    motorcycle.getIsForSale(),
                    motorcycle.getIsAvailable(),
                    motorcycle.getHasOffer()
            );

            // Map MaintenanceRequest to MaintenanceRequestDTO
            return new MaintenanceRequestDTO(
                    maintenanceRequest.getRequestDate(),
                    maintenanceRequest.getTotalPrice(),
                    maintenanceRequest.getExpert_name(),
                    maintenanceRequest.getStatus(),
                    maintenanceRequest.getPickupDate(),
                    motorcycleDTO
            );
        }).collect(Collectors.toList());

    }

    //Raghad
    public void addMaintenanceRequest(MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(maintenanceRequestDTO_in.getMotorcycle_id());
        if(motorcycle == null){
            throw new ApiException("Motorcycle not found");
        }

        Owner owner = ownerRepository.findOwnerById(maintenanceRequestDTO_in.getOwner_id());
        if(owner == null)
            throw new ApiException("Owner not found");


        if (maintenanceRequestDTO_in.getPickupDate().isBefore(LocalDate.now())) {
            throw new ApiException("Pickup date cannot be in the past!");
        }

        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequestDTO_in.getExpert_name());
        if (expert == null||!expert.getIsApproved()) {
            throw new ApiException("Expert not found!");
        }

        Double totalPrice = calculateTotalPrice(expert, maintenanceRequestDTO_in.getPickupDate());

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest(maintenanceRequestDTO_in.getExpert_name(),maintenanceRequestDTO_in.getPickupDate(),owner,maintenanceRequestDTO_in.getMotorcycle_id());

        maintenanceRequest.setStatus("Pending");
        maintenanceRequest.setTotalPrice(totalPrice);

        maintenanceRequestRepository.save(maintenanceRequest);

    }

    //Raghad method to calculate total price
    private Double calculateTotalPrice(MaintenanceExpert expert, LocalDate pickupDate) {
        // calc price based on expert daily rate and number of days
        Double numberOfDays = (double) Duration.between(LocalDate.now().atStartOfDay(), pickupDate.atStartOfDay()).toDays();

        // Calculate total price as the daily rate times the number of days
        return expert.getMaintenancePricePerDay() * numberOfDays;
    }

//Raghad
    public void updateMaintenanceRequest(Integer maintenanceRequest_id, MaintenanceRequestDTO_In maintenanceRequestDTO_in){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest ==null)
            throw new ApiException("MaintenanceRequest not found!");

        maintenanceRequest.setExpert_name(maintenanceRequestDTO_in.getExpert_name());
        maintenanceRequest.setPickupDate(maintenanceRequestDTO_in.getPickupDate());


        if (maintenanceRequestDTO_in.getOwner_id() != null) {
            Owner owner = ownerRepository.findOwnerById(maintenanceRequestDTO_in.getOwner_id());
            if(owner== null)
                throw new ApiException("Owner not found !");

            maintenanceRequest.setOwner(owner);
        }

        if (maintenanceRequestDTO_in.getMotorcycle_id() != null) {
            maintenanceRequest.setMotorcycle_id(maintenanceRequestDTO_in.getMotorcycle_id());
        }

        if (maintenanceRequestDTO_in.getPickupDate() != null) {
            MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(maintenanceRequestDTO_in.getExpert_name());
            if (expert == null||!expert.getIsApproved()) {
                throw new ApiException("Expert not found or not approved");
            }

            // calculate again the total price if the pickupdate is changed
            Double newTotalPrice = calculateTotalPrice(expert, maintenanceRequestDTO_in.getPickupDate());
            maintenanceRequest.setTotalPrice(newTotalPrice);
        }

        maintenanceRequestRepository.save(maintenanceRequest);
    }

    //Durrah
    public void updateMaintenanceRequestStatusToCompleted(Integer maintenanceRequest_id, String expertName) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if (maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the current expert is the one assigned to the request
        if (!maintenanceRequest.getExpert_name().equalsIgnoreCase(expertName)) {
            throw new ApiException("Only the expert can mark the maintenance request as completed!");
        }

        // Only allow the status to be updated if the request is in 'Pending' status
        if (!"Pending".equalsIgnoreCase(maintenanceRequest.getStatus())) {
            throw new ApiException("Maintenance request is not in a Pending status, it cannot be marked as completed!");
        }

        // Update status
        maintenanceRequest.setStatus("Completed");
        maintenanceRequestRepository.save(maintenanceRequest);
    }


    public void deleteMaintenanceRequest(Integer maintenanceRequest_id ){

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);

        if(maintenanceRequest == null)
            throw new ApiException("MaintenanceRequest not found!");

        // Check if the pickupDate is after the current date (meaning the expert has completed their work)
        if (maintenanceRequest.getPickupDate() != null && maintenanceRequest.getPickupDate().isAfter(LocalDate.now())) {
            throw new ApiException("Cannot delete this Maintenance Request !");
        }

        maintenanceRequestRepository.delete(maintenanceRequest);

    }

    //Raghad ahmad
    public List<MaintenanceRequestHistoryDTO> getMaintenanceHistory() {
        // Step 1: Fetch all maintenance requests
        List<MaintenanceRequest> maintenanceRequests = maintenanceRequestRepository.findAll();

        // Step 2: Map MaintenanceRequest to MaintenanceRequestDTO
        return maintenanceRequests.stream().map(request -> {
            // Fetch motorcycle information based on motorcycle_id
            Motorcycle motorcycle = motorcycleRepository.findById(request.getMotorcycle_id())
                    .orElseThrow(() -> new ApiException("Motorcycle not found for maintenance request"));

            // Map motorcycle to DTO
            MotorcycleDTO motorcycleDTO = new MotorcycleDTO(
                    motorcycle.getBrand(),
                    motorcycle.getModel(),
                    motorcycle.getYear(),
                    motorcycle.getPrice(),
                    motorcycle.getColor(),
                    motorcycle.getIsAvailable(),
                    motorcycle.getIsForSale(),
                    motorcycle.getHasOffer()
            );

            // Map maintenance request to DTO
            MaintenanceRequestHistoryDTO dto = new MaintenanceRequestHistoryDTO();
            dto.setRequestDate(request.getRequestDate());
            dto.setTotalPrice(request.getTotalPrice());
            dto.setExpertName(request.getExpert_name());
            dto.setStatus(request.getStatus());
            dto.setPickupDate(request.getPickupDate());
            dto.setMotorcycleS(List.of(motorcycleDTO)); // Add the motorcycle information

            return dto;
        }).collect(Collectors.toList());
    }



    //Durrah
    public Map<String, Object> generateMaintenanceRequestInvoice(Integer maintenanceRequest_id) {
        // Retrieve the maintenance request by ID
        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_id);
        if (request == null) {
            throw new ApiException("Request not found!");
        }

        // Check if the request's status is "Completed"
        if (!"Completed".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("Cannot generate invoice for pending requests!"); // Only allow invoice generation for completed requests
        }



        Map<String, Object> invoiceData = new HashMap<>();
        invoiceData.put("Request Id", request.getId());
        invoiceData.put("Owner Name", request.getOwner().getName());
        invoiceData.put("Owner Email", request.getOwner().getEmail());
        invoiceData.put("Total Price", request.getTotalPrice());
        invoiceData.put("Date Generated", LocalDate.now());
        invoiceData.put("Request Status", "Completed");

        return invoiceData;
    }



    //Durrah
    public void notifyOwnerOnCompletion(Integer maintenanceRequest_Id, Integer ownerId) {
        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceRequest_Id);

        if (request == null) {
            throw new RuntimeException("Maintenance Request not found!");
        }

        if (!"Completed".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Request is not completed yet!");
        }

        // Retrieve the owner's email
        Owner owner = request.getOwner();
        String ownerEmail = owner.getEmail();

        // build the email message
        String subject = "Your Maintenance Request is Completed!";
        String text = "Dear " + owner.getName() + ",\n\n" +
                "Your maintenance request for motorcycle: " + request.getMotorcycle_id() + " has been completed successfully and ready for pickup.\n\n" +
                "Thank you for using our services!";

        // Send the email notification
        emailService.sendEmail(ownerEmail, subject, text);
    }




    //Durrah
    public List<MaintenanceRequest> getMaintenanceHistoryByOwner(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner==null)
            throw  new ApiException("Owner not found!");

        return maintenanceRequestRepository.findByOwner(owner);
    }


    //Durrah
    public void notifyExpert(Integer maintenanceId) {

        MaintenanceRequest request = maintenanceRequestRepository.findMaintenanceRequestById(maintenanceId);
        if (request == null) {
            throw new ApiException("Maintenance request not found!");
        }

        if (!request.getStatus().equalsIgnoreCase("Pending")) {
            throw new ApiException("Can not send notification for completed requests!!");
        }

        MaintenanceExpert expert = maintenanceExpertRepository.findMaintenanceExpertByName(request.getExpert_name());
        if (expert == null) {
            throw new ApiException("Expert not found.");
        }


        String subject = "New Maintenance Request Assigned!";
        String text = String.format("Dear %s,\n\nYou have been assigned a new maintenance request for motorcycle ID: %d.\n\nPlease review the request.\n\nBest regards,\nMaintenance System",
                expert.getName(), request.getMotorcycle_id());

        // Sending the email to the expert
        emailService.sendEmail(expert.getEmail(), subject, text);

    }

    //Durrah
    public List<MaintenanceRequest> getUpcomingRequestsByExpert(String expertName, LocalDate today) {
        return maintenanceRequestRepository.findUpcomingRequestsByExpert(expertName, today);
    }



}