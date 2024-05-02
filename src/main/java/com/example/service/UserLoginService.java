package com.example.service;

import com.example.dto.UserLoginDto;
import com.example.dto.UserSignupDto;
import com.example.model.UserModel;
import com.example.repository.BloodBankRepository;
import com.example.repository.UserRequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserLoginService {
    @Autowired
    private BloodBankRepository bloodBankRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRequestRepository userRequestRepository;

    //check for user exist or not
    public UserSignupDto check(UserLoginDto userLoginDto) {
        UserModel user = bloodBankRepository.findByUsername(userLoginDto.getUsername());
        if (user != null && userLoginDto.getPassword().equals(user.getPassword())) {
            user.setLoginAttempt(0);
            bloodBankRepository.save(user);
            //we use ModelMapper for setting values in Dto class
            return convertToDto(user);

        }
         else {
            if (user != null)
                incrementLoginAttempt(user);
            return convertToDto(user);
        }

    }

    public UserSignupDto convertToDto(UserModel userModel) {
        return modelMapper.map(userModel, UserSignupDto.class);
    }

    public void incrementLoginAttempt(UserModel user) {
        //increment login count
        user.setLoginAttempt(user.getLoginAttempt() + 1);

        //check for 3 times wrong attempt
        if (user.getLoginAttempt() >= 3) {
            user.setLockStatus(true);
        }
        bloodBankRepository.save(user);

    }

    //Adding signup users to list to show on admin dashboard
    public List<UserSignupDto> signupUsers() {
        Iterable<UserModel> allFetchUser = bloodBankRepository.findAll();
        List<UserSignupDto> usersList = new ArrayList<>();
        for (UserModel user : allFetchUser) {
            if (!user.getRole().equalsIgnoreCase("admin")) {
                usersList.add(convertToDto(user));
            }
        }
        return usersList;
    }

    //for updating password
    public boolean updatePassword(UserLoginDto userLoginDto) {
        UserModel user = bloodBankRepository.findByUsername(userLoginDto.getUsername());
        if(userLoginDto.getCurrentPassword().equals(user.getPassword())) {
            user.setPassword(userLoginDto.getPassword());
            user.setFirstLogin(false);  //now setting first login false
            bloodBankRepository.save(user);
            return true;
        }
        return false;
    }

    //to find all signup user list
    public List<UserSignupDto> findUsersList(String userName) {
        List<UserSignupDto> userSignupDtoList = new ArrayList<>();
        Iterable<UserModel> allusers = bloodBankRepository.findAll();
        if (userName.equals("Admin_1")) {
            for (UserModel user : allusers) {
                if (!user.getUsername().equals(userName))
                    userSignupDtoList.add(convertToDto(user));
            }
        } else {
            for (UserModel user : allusers) {
                if (user.getCreatedBy().equals(userName))
                    userSignupDtoList.add(convertToDto(user));
            }
        }
        //2nd Approach
//        boolean isAdminUser = userName.equals("Admin_1");
//        for (UserModel user : allusers) {
//            if ((isAdminUser && !user.getUsername().equals(userName)) || (!isAdminUser && user.getCreatedBy().equals(userName))) {
//                userSignupDtoList.add(convertToDto(user));
//            }
//        }

        return userSignupDtoList;
    }
}
