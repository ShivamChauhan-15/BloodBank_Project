package com.example.service;

import com.example.dto.UserSignupDto;
import com.example.model.UserModel;
import com.example.repository.BloodBankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SortAndFilterService {
    @Autowired
    BloodBankRepository bloodBankRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserSignupDto convertToDto(UserModel userModel) {
        return modelMapper.map(userModel, UserSignupDto.class);
    }

    //for sorting
    public List<UserSignupDto> sortUsers(String by, List<UserSignupDto> usersList) {
        switch (by) {
            case "name":
                return usersList.stream()
                        .sorted(Comparator.comparing(userSignupDto -> userSignupDto.getUsername().toLowerCase()))
                        .collect(Collectors.toList());
            case "createdBy":
                return usersList.stream()
                        .sorted(Comparator.comparing(userSignupDto -> userSignupDto.getCreatedBy().toLowerCase()))
                        .collect(Collectors.toList());
            case "bloodGroup":
                return usersList.stream()
                        .sorted(Comparator.comparing(UserSignupDto::getBloodGroup))
                        .collect(Collectors.toList());
            default:
                return usersList;

        }
    }
    //for filter

    public List<UserSignupDto> filterByAgent(String agent,List<UserSignupDto> allUsers) {
        return allUsers.stream().filter(user -> user.getCreatedBy().equalsIgnoreCase(agent))
                .collect(Collectors.toList());
    }

    public List<UserSignupDto> filterByUsername(String username, List<UserSignupDto> allUsers) {
        return allUsers.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
    }

    public List<UserSignupDto> filterByDate(String startDate, String endDate, List<UserSignupDto> allUsers) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate, formatter).atTime(23, 59, 59);

        return allUsers.stream()
                .filter(user -> {
                    LocalDateTime userDateTime = user.getCreatedOn();
                    return userDateTime.compareTo(startDateTime) >= 0 && userDateTime.compareTo(endDateTime) <= 0;
                })
                .collect(Collectors.toList());
    }

    //Not logged in user
    private static final Map<String, Boolean> allActiveUsers = new HashMap<>();

    public void updateActiveUsers(String userName, String cause) {
        if (userName != null) {
            if (cause.equalsIgnoreCase("add")) {
                allActiveUsers.put(userName, true);
            }
            if (cause.equalsIgnoreCase("remove")) {
                allActiveUsers.remove(userName);
            }
        }
    }

    public Map<String, Boolean> getAllActiveUsers() {
        return allActiveUsers;
    }

    public List<UserSignupDto> notLoggedInUser(List<UserSignupDto> allUserList) {
        List<UserSignupDto> notLoggedInUsers = new ArrayList<>();
        for (UserSignupDto user : allUserList) {
            if (!allActiveUsers.containsKey(user.getUsername())) {
                notLoggedInUsers.add(user);
            }
        }
        return notLoggedInUsers;
    }

}
