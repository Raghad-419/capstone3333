package com.example.capston3.Service;

import com.example.capston3.ApiResponse.ApiException;
import com.example.capston3.DTO.PurchaseDTO;
import com.example.capston3.DTO.PurchaseMotorcycleOutDTO;
import com.example.capston3.DTO.PurchaseUserOutDTO;
import com.example.capston3.InDTO.PurchaseDTO_In;
import com.example.capston3.Model.Motorcycle;
import com.example.capston3.Model.Owner;
import com.example.capston3.Model.Purchase;
import com.example.capston3.Model.User;
import com.example.capston3.Repository.MotorcycleRepository;
import com.example.capston3.Repository.OwnerRepository;
import com.example.capston3.Repository.PurchaseRepository;
import com.example.capston3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    //Hashim Baroom
    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        List<PurchaseDTO> purchaseOutDTOs = new ArrayList<>();
        for (Purchase purchase : purchases) {
            Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchase.getMotorcycleId());
            purchaseOutDTOs.add(new PurchaseDTO(purchase.getPurchaseDate(),new PurchaseUserOutDTO(purchase.getUser().getName(),purchase.getUser().getEmail(),purchase.getUser().getPhoneNumber(),purchase.getUser().getAge(),purchase.getUser().getAddress()),new PurchaseMotorcycleOutDTO(motorcycle.getBrand(),motorcycle.getModel(),motorcycle.getYear(),motorcycle.getPrice(),motorcycle.getColor(),motorcycle.getIsAvailable())));
        }
        return purchaseOutDTOs;
    }

    //Hashim Baroom
    public void addPurchase(PurchaseDTO_In purchaseInDTO) {
        Motorcycle motorcycle = motorcycleRepository.findMotorcycleById(purchaseInDTO.getMotorcycleId());
        if (motorcycle == null) {
            throw new ApiException("Motorcycle not found");
        }
        User user = userRepository.findUserById(purchaseInDTO.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }
        Owner owner = ownerRepository.findOwnerById(purchaseInDTO.getOwnerId());
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if (motorcycle.getIsForSale() == true && motorcycle.getIsAvailable() == true) {
            Purchase purchase = new Purchase(user, motorcycle.getId());
            purchase.setOwner(owner);
            purchaseRepository.save(purchase);

            motorcycle.setIsAvailable(false);
            motorcycle.setIsForSale(false);
            motorcycle.setOwner(null);
            Set<Motorcycle> motorcycles = owner.getMotorcycles();

            if (motorcycles.contains(motorcycle)) {
                motorcycles.remove(motorcycle);
            }else{
                throw new ApiException("Motorcycle not found");
            }

            ownerRepository.save(owner);
            motorcycleRepository.save(motorcycle);

        }else{
            throw new ApiException("This motorcycle is not for sale");
        }


    }
}