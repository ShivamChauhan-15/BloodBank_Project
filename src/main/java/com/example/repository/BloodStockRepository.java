package com.example.repository;

import com.example.model.BloodStockModel;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BloodStockRepository extends JpaRepository<BloodStockModel,Long> {
    BloodStockModel getByBloodGroup(String bloodGroup);

}
