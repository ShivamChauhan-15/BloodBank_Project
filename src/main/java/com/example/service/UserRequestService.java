package com.example.service;

import com.example.dto.UserRequestDto;
import com.example.dto.UserSignupDto;
import com.example.model.BloodStockModel;
import com.example.model.UserModel;
import com.example.model.UserRequestModel;
import com.example.repository.BloodBankRepository;
import com.example.repository.BloodStockRepository;
import com.example.repository.UserRequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRequestService {
    @Autowired
    private UserRequestRepository userRequestRepository;
    @Autowired
    private BloodStockRepository bloodStockRepository;
    @Autowired
    private BloodBankRepository bloodBankRepository;
    @Autowired
    private BloodStockService bloodStockService;
    @Autowired
    private BloodReportService bloodReportService;

    public String handleUserRequest(UserRequestDto userRequestDto, String userName) {
        UserRequestModel userRequestModel = new UserRequestModel();
        UserModel userModel = bloodBankRepository.findByUsername(userName);
        if (userModel != null) {
            userRequestModel.setBloodGroup(userRequestDto.getBloodGroup());
            userRequestModel.setType(userRequestDto.getType());
            userRequestModel.setQuantity(userRequestDto.getQuantity());
            userRequestModel.setCreatedOn(LocalDateTime.now());
            userRequestModel.setUpdatedAt(LocalDateTime.now());
            userRequestModel.setCreatedBy(userModel.getUsername());
            userRequestModel.setUpdatedBy("admin");
            userRequestModel.setStatus("pending");
            userRequestModel.setRemark("-");
            userRequestModel.setUser(userModel);
            userRequestModel.setAge(calculateAge(userModel));
            if (userRequestDto.getType().equalsIgnoreCase("donor")) {
                if (!isEligibleForDonation(userModel)) {
                    return "Please Donate Blood After 3 months";
                }
                userRequestRepository.save(userRequestModel);

            }
            userRequestRepository.save(userRequestModel);
            return "Request is succssfully send";

        }
        return null;


    }

    //Calculate age based on date of birth
    public int calculateAge(UserModel userModel) {
        Date dob = userModel.getDob();
        LocalDate today = LocalDate.now();
        LocalDate birthDate = dob.toLocalDate();
        int age = Period.between(birthDate, today).getYears();
        return age;
    }

    //List of all Requested Users
    public List<HashMap<String, Object>> userRequestedList() {
        List<UserRequestModel> list = userRequestRepository.findAll();
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        for (UserRequestModel stock : list) {
            HashMap<String, Object> myResult = new HashMap<>();

            myResult.put("id", stock.getId());
            myResult.put("bloodGroup", stock.getBloodGroup());
            myResult.put("type", stock.getType());
            myResult.put("status", stock.getStatus());
            myResult.put("quantity", stock.getQuantity());
            myResult.put("createdBy", stock.getUser().getUsername());
            myResult.put("agent", stock.getUser().getCreatedBy());
            myResult.put("dob", stock.getUser().getDob());
            myResult.put("createdAt", stock.getCreatedOn());
            myResult.put("age", stock.getAge());
            myResult.put("remark", stock.getRemark());
            resultList.add(myResult);
        }
        Collections.reverse(resultList);
        return resultList;
    }

    //for approved request
    public String requestApproved(String id) {
        UserRequestModel userRequestModel = userRequestRepository.getById(Long.valueOf(id));
        String checkBloodUnitStatus = bloodStockService.setBloodStockRepository(userRequestModel);
        if (checkBloodUnitStatus.equalsIgnoreCase("ok")) {
            userRequestModel.setStatus("approved");
            userRequestRepository.save(userRequestModel);
            coinUpdate(Long.valueOf(id));   //for updating coin value in table
            return "ok";
        } else
            return checkBloodUnitStatus;

    }

    //for rejected request
    public void requestRejected(String id, String remark) {
        UserRequestModel userRequestModel = userRequestRepository.getById(Long.valueOf(id));
        userRequestModel.setRemark(remark);
        userRequestModel.setStatus("rejected");
        userRequestRepository.save(userRequestModel);
    }

    //for showing user request on agent dashboard and admin
    public List<HashMap<String, Object>> agentUserRequest(List<HashMap<String, Object>> allRequestedUsers, String userNameToCheck) {
        List<HashMap<String, Object>> selectedReuqest = new ArrayList<>();
        for (HashMap<String, Object> userMap : allRequestedUsers) {
            String createdBy = (String) userMap.get("agent");
            if (createdBy != null && createdBy.equals(userNameToCheck)) {
                selectedReuqest.add(userMap);
            }
        }
        return selectedReuqest;
    }

    //for showing all requests by each user
    public List<HashMap<String, Object>> findEnduserRequest(List<HashMap<String, Object>> allRequestedUsers, String userNameToCheck) {
        List<HashMap<String, Object>> selectedRequest = new ArrayList<>();
        for (HashMap<String, Object> userMap : allRequestedUsers) {
            String userName = (String) userMap.get("createdBy");
            if (userName != null && userName.equals(userNameToCheck)) {
                selectedRequest.add(userMap);
            }
        }
        return selectedRequest;
    }

    //For blood request date filter
    public List<HashMap<String, Object>> filterBloodRequestByDate(List<HashMap<String, Object>> list, String startDate, String endDate) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(endDate, formatter).atTime(23, 59, 59);
        // Filter the list based on createdAt field
        return list.stream()
                .filter(user -> {
                    LocalDateTime userDateTime = (LocalDateTime) user.get("createdAt");
                    return userDateTime.compareTo(startDateTime) >= 0 && userDateTime.compareTo(endDateTime) <= 0;
                })
                .collect(Collectors.toList());

    }

    // filter by status, username and agent
    public List<HashMap<String, Object>> filterBloodRequest(List<HashMap<String, Object>> list, String filterBy, String input, String status, String startDate, String endDate) throws ParseException {
        if (filterBy == null)
            return list;
        switch (filterBy) {
            case "byStatus":
                return list.stream().filter(i -> i.get("status").equals(status)).collect(Collectors.toList());
            case "byAgent":
                return list.stream().filter(i -> !i.get("agent").toString().equalsIgnoreCase("auto")).collect(Collectors.toList());
            case "byUsername":
                return list.stream().filter(i -> i.get("createdBy").toString().equals(input)).collect(Collectors.toList());
            case "byDate":
                return filterBloodRequestByDate(list, startDate, endDate);
            default:
                return list;
        }
    }

    //for updating coins of admin,agent and enduser in table
    public void coinUpdate(Long reqID) {
        UserRequestModel userRequestModel = userRequestRepository.getById(reqID);
        String bloodGroup = userRequestModel.getBloodGroup();
        String type = userRequestModel.getType();
        int quantity = userRequestModel.getQuantity();
        Long coinRate = bloodStockRepository.getByBloodGroup(bloodGroup).getCoins();
        String username = userRequestModel.getUser().getUsername();
        UserModel byUsername = bloodBankRepository.findByUsername(username);
        String createdBy = byUsername.getCreatedBy();
        Long userCoinValue = byUsername.getCoins();
        UserModel adminModel = bloodBankRepository.findByUsername("Admin_1");
        Long adminCoinValue = adminModel.getCoins();
        if (type.equalsIgnoreCase("donor")) {
            userCoinValue = userCoinValue + (quantity * coinRate);
            byUsername.setCoins(userCoinValue);        //update coins of enduser
            if (createdBy.equalsIgnoreCase("auto")) {
                adminCoinValue = adminCoinValue + (quantity * coinRate);
            } else {
                UserModel agentModel = bloodBankRepository.findByUsername(createdBy);
                Long agentCoinValue = agentModel.getCoins();
                float commision = agentModel.getCommision();
                Long agentNewCoinValue = (long) ((quantity * coinRate * commision) / 100);
                agentCoinValue = (long) (agentCoinValue + agentNewCoinValue);
                agentModel.setCoins(agentCoinValue);     //update coins of particular agent
                adminCoinValue = adminCoinValue + (userCoinValue - agentNewCoinValue);
                bloodBankRepository.save(agentModel);
            }
            adminModel.setCoins(adminCoinValue);  //update coin value of admin
            bloodBankRepository.save(byUsername);
            bloodBankRepository.save(adminModel);

        }
    }

    //blocking user for 3 months
    public boolean isEligibleForDonation(UserModel userModel) {
        List<UserRequestModel> allByuserId = userRequestRepository.findAllByuser(userModel);
        List<UserRequestModel> collect = allByuserId.stream().filter(userRequestModel -> userRequestModel.getType().equalsIgnoreCase("donor"))
                .filter(userRequestModel -> userRequestModel.getStatus().equalsIgnoreCase("approved"))
                .collect(Collectors.toList());
        // Find the nearest update based on the time difference
        Optional<UserRequestModel> nearestUpdate = collect.stream()
                .min(Comparator.comparingLong(userRequestModel ->
                        Math.abs(userRequestModel.getUpdatedAt().until(LocalDateTime.now(), java.time.temporal.ChronoUnit.SECONDS))));
        if (nearestUpdate.isEmpty()) {
            return true;
        }
        // Calculate current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Check if three months have passed since last donation
        return nearestUpdate.get().getUpdatedAt().plusMonths(3).isBefore(currentDateTime);

    }
}
