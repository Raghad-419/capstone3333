package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.FineDTO;
import com.example.capston3.InDTO.FineDTO_In;
import com.example.capston3.Model.Fine;
import com.example.capston3.Model.Owner;
import com.example.capston3.Model.Renting;
import com.example.capston3.Model.RentingRequest;
import com.example.capston3.Repository.FineRepository;
import com.example.capston3.Repository.OwnerRepository;
import com.example.capston3.Repository.RentingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class FineService {
    private final FineRepository fineRepository;
    private final RentingRequestRepository rentingRequestRepository;
    private final OwnerRepository ownerRepository;



    //Raghad
    //  Fetch all fines by user ID
    public List<FineDTO> getAllFineByUserId(Integer userId){
        List<Fine> fines = fineRepository.findFineByUserId(userId);
        List<FineDTO> fineDTOS=new ArrayList<>();

        for(Fine fine:fines){
            FineDTO fineDTO = new FineDTO(fine.getDescription(),fine.getAmount(),fine.getIsPaid());
            fineDTOS.add(fineDTO);
        }
        return fineDTOS;
    }

//Raghad
// Scheduled job to impose late return penalties
@Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
//@Scheduled(fixedRate = 60000)
public void imposeLateReturnPenalties() {
    // Fetch overdue renting requests where the bike is not yet returned
    List<RentingRequest> overdueRequests = rentingRequestRepository.findOverdueRentals(LocalDate.now());

    for (RentingRequest rentingRequest : overdueRequests) {
        // Skip fine calculation if the bike has been returned
        if (Boolean.TRUE.equals(rentingRequest.getIsReturned())) {
            continue;
        }

        LocalDate endDate = rentingRequest.getEndDate();
        LocalDate currentDate = LocalDate.now();

        // Calculate late days
        long lateDays = java.time.temporal.ChronoUnit.DAYS.between(endDate, currentDate);

        if (lateDays > 0) {
            // Calculate fine amount
            Renting renting = rentingRequest.getRenting();
            if (renting == null) {
                System.err.println("Renting details not found for RentingRequest ID " + rentingRequest.getId());
                continue; // Skip this request
            }

            Double fineAmount = lateDays * renting.getPricePerDay();

            // Update or create the Fine entity
            Fine fine = rentingRequest.getFine();
            if (fine == null) {
                fine = new Fine();
                fine.setDescription("Late return penalty for " + lateDays + " days");
                fine.setAmount(fineAmount);
                fine.setUser(rentingRequest.getUser());
                fine.setRentingRequest(rentingRequest);
                fine.setIsPaid(false);
                rentingRequest.setFine(fine);
            } else {
                fine.setAmount(fineAmount); // Update the existing fine
            }

            fineRepository.save(fine);
            System.out.println("Updated fine for RentingRequest ID " + rentingRequest.getId() +
                    ": $" + fineAmount + " for " + lateDays + " days late.");
        }
    }
}
    //Raghad
    // Method to mark a bike as returned
    public void markBikeAsReturned(Integer rentingRequestId) {
        RentingRequest rentingRequest = rentingRequestRepository.findRentingRequestById(rentingRequestId);
          if(rentingRequest==null){
            throw new ApiException("Renting Request not found");}
        Owner owner = ownerRepository.findOwnerById(rentingRequest.getRenting().getOwner().getId());
          if(owner ==null){
              throw new ApiException("Just owner can mark a bike as returned");
          }


        // Update the isReturned status
        rentingRequest.setIsReturned(true);
        rentingRequestRepository.save(rentingRequest);

        System.out.println("Bike marked as returned for RentingRequest ID " + rentingRequestId);
    }


    public long getNumberOfFinesByUserId(Integer userId) {
        // Fetch the count of fines by user ID
        return fineRepository.countFinesByUserId(userId);
    }

//Raghad
//fine payment feature
    public void payFine(Integer fineId) {
        // Step 1: Fetch the fine
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ApiException("Fine not found"));

        // Step 2: Check if the fine is already paid
        if (Boolean.TRUE.equals(fine.getIsPaid())) {
            throw new ApiException("This fine has already been paid");
        }

        // Step 3: Mark the fine as paid
        fine.setIsPaid(true);
        fineRepository.save(fine);

    }

    //Raghad
    public List<FineDTO> getUnpaidFinesByUserId(Integer userId) {
        List<Fine> fines = fineRepository.findUnpaidFinesByUserId(userId);
        List<FineDTO> fineDTOS=new ArrayList<>();

        for(Fine fine:fines){
            FineDTO fineDTO = new FineDTO(fine.getDescription(),fine.getAmount(),fine.getIsPaid());
            fineDTOS.add(fineDTO);
        }
        return fineDTOS;

    }

}

