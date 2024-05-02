package com.example.service;

import com.example.dto.UserSignupDto;
import com.example.model.UserModel;
import com.example.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SignupService {
    @Autowired
    private BloodBankRepository bloodBankRepository;
    //1st Approach
//    public boolean insert(UserSignupDto userDto){
//         check whether username is already exist or not
//        Iterable<UserModel> fetchUsers = userRepository.findAll();
//        for(UserModel userModel:fetchUsers){
//            if(userDto.getUsername().equals(userModel.getUsername()))
//                return false;
//        }

    //2nd Approach
    public boolean insert(UserSignupDto userDto) {
        //check whether username is already exist or not
        UserModel user = bloodBankRepository.checkForSignup(userDto.getUsername());
        if (user != null && (userDto.getUsername().equals(user.getUsername()))) {
            return false;
        }
        UserModel user1 = new UserModel();
        //setting values to the parameters of userDto class
        user1.setName(userDto.getName());
        user1.setUsername(userDto.getUsername());
        user1.setBloodGroup(userDto.getBloodGroup());
        user1.setPassword(userDto.getPassword());
        user1.setDob(userDto.getDob());
        user1.setAddress(userDto.getAddress());
        user1.setCreatedOn(LocalDateTime.now());
        user1.setFirstLogin(userDto.isFirstLogin());
        user1.setCommision(userDto.getCommision());
        user1.setRole(userDto.getRole());
        user1.setCreatedBy(userDto.getCreatedBy());
        user1.setCoins(0l);
        UserModel save = bloodBankRepository.save(user1);
        return true;
    }

}


