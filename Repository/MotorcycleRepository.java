package com.example.capston3.Repository;

import com.example.capston3.Model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle,Integer> {
    Motorcycle findMotorcycleById(Integer id);
   //Raghad
    List<Motorcycle> findMotorcycleByOwnerId(Integer owenrId);


    // Find motorcycles by price range
    List<Motorcycle> findByPriceBetween(Double minPrice, Double maxPrice);

    // Find motorcycles by minimum price
    List<Motorcycle> findByPriceGreaterThanEqual(Double minPrice);

    // Find motorcycles by maximum price
    List<Motorcycle> findByPriceLessThanEqual(Double maxPrice);


    //Hashim Baroom
    List<Motorcycle> findMotorcycleByIsAvailable(Boolean isAvailable);
    //Hashim Baroom
    List<Motorcycle> findMotorcycleByIsForSale(Boolean isForSale);
    //Hashim Baroom
    List<Motorcycle> findMotorcycleByBrandAndYear(String brand, Integer year);
    //Hashim Baroom
    List<Motorcycle> findMotorcycleByBrandAndModel(String brand, String model);
}
