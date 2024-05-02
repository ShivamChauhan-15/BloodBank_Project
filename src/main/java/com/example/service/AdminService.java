package com.example.service;

import com.example.model.UserModel;
import com.example.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class AdminService {
    @Autowired
    private BloodBankRepository bloodBankRepository;

    public void setAdmin() {
        UserModel admin = new UserModel();
        admin.setName("Admin");
        admin.setDob(Date.valueOf("2000-03-01"));
        admin.setRole("admin");
        admin.setBloodGroup("B+");
        admin.setCreatedOn(LocalDateTime.now());
        admin.setCreatedBy("self");
        admin.setUsername("Admin_1");
        admin.setPassword("admin123");
        admin.setFirstLogin(false);
        admin.setAddress("Delhi");
        admin.setCoins(100l);
        Iterable<UserModel> user1 = bloodBankRepository.findAll();
        if (!(user1.iterator().hasNext())) {
            System.out.println("in admin service method");
            bloodBankRepository.save(admin);
        }
    }
}

