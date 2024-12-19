package com.example.capston3.Repository;

import com.example.capston3.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    Purchase findPurchaseById(Integer id);
}
