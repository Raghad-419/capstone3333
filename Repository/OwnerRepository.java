package com.example.capston3.Repository;

import com.example.capston3.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner ,Integer> {
    Owner findOwnerById(Integer id);

    //Hashim Baroom
    Owner findOwnerByMotorcyclesId(Integer id);
}
