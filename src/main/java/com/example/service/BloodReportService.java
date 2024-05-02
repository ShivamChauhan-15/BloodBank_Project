package com.example.service;

import com.example.dto.UserSignupDto;
import com.example.model.BloodStockModel;
import com.example.model.UserModel;
import com.example.model.UserRequestModel;
import com.example.repository.BloodBankRepository;
import com.example.repository.BloodStockRepository;
import com.example.repository.UserRequestRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BloodReportService {
    @Autowired
    private BloodStockRepository bloodStockRepository;
    @Autowired
    private UserRequestRepository userRequestRepository;
    @Autowired
    private BloodBankRepository bloodBankRepository;

    //for showing report on admin of donor
    public List<HashMap<String, String>> getRequestCount(String userName, String role) {
        List<UserRequestModel> allRequestsList = new ArrayList<>();
        if (role.equalsIgnoreCase("admin"))
            allRequestsList = userRequestRepository.findAll();
        else if (role.equalsIgnoreCase("agent")) {
            allRequestsList = userRequestRepository.findAll().stream()
                    .filter(userRequestModel -> userRequestModel.getUser().getCreatedBy().equals(userName))
                    .collect(Collectors.toList());
        }
        List<HashMap<String, String>> typeOfRequest = new ArrayList<>();
        Map<String, Integer> approvedRequests = new HashMap<>();
        Map<String, Integer> rejectedRequests = new HashMap<>();
        Map<String, Integer> quantityOfEachRequest = new HashMap<>();

        for (UserRequestModel user : allRequestsList) {
            if (user.getStatus().equalsIgnoreCase("approved") && user.getType().equalsIgnoreCase("donor")) {
                approvedRequests.put(user.getBloodGroup(), approvedRequests.getOrDefault(user.getBloodGroup(), 0) + 1);
                quantityOfEachRequest.put(user.getBloodGroup(), quantityOfEachRequest.getOrDefault(user.getBloodGroup(), 0) + user.getQuantity());
            } else if (user.getStatus().equals("rejected") && user.getType().equalsIgnoreCase("donor"))
                rejectedRequests.put(user.getBloodGroup(), rejectedRequests.getOrDefault(user.getBloodGroup(), 0) + 1);

        }
        List<BloodStockModel> all = bloodStockRepository.findAll();
        for (BloodStockModel bloodStockModel : all) {
            HashMap<String, String> blood = new HashMap<>();
            int quantity = quantityOfEachRequest.getOrDefault(bloodStockModel.getBloodGroup(), 0);
            int approved = approvedRequests.getOrDefault(bloodStockModel.getBloodGroup(), 0);
            int reject = rejectedRequests.getOrDefault(bloodStockModel.getBloodGroup(), 0);
            blood.put("bloodGroup", bloodStockModel.getBloodGroup());
            blood.put("coins", String.valueOf(bloodStockModel.getCoins() * quantity));
            blood.put("approved", String.valueOf(approved));
            blood.put("rejected", String.valueOf(reject));
            typeOfRequest.add(blood);
        }
        return typeOfRequest;

    }

    //find coin value by bloodgroup and rate
    public HashMap<String, Object[]> findCoinByBloodGroup(String userNameToCheck) {
        List<UserRequestModel> allUsersRequest = userRequestRepository.findAll();
        HashMap<String, Object[]> bloodGroupCoinsMap = new HashMap<>();
        List<UserRequestModel> collect = allUsersRequest.stream()
                .filter(userRequestModel -> userRequestModel.getUser().getCreatedBy().equals(userNameToCheck))
                .filter(userRequestModel -> userRequestModel.getType().equals("donor"))
                .filter(userRequestModel -> userRequestModel.getStatus().equals("approved"))
                .collect(Collectors.toList());
        UserModel byUsername = bloodBankRepository.findByUsername(userNameToCheck);
        for (UserRequestModel userRequestModel : collect) {
            String bloodGroup = userRequestModel.getBloodGroup();
            BloodStockModel byBloodGroup = bloodStockRepository.getByBloodGroup(bloodGroup);
            double rateOfCoins = byBloodGroup.getCoins();
            int userCoins = (int) (userRequestModel.getQuantity() * rateOfCoins);
            double rate = byUsername.getCommision();
            int agentCoins = (int) (userCoins * byUsername.getCommision() / 100); // Assuming agent gets 10% of user coins

            // Update blood group coins map
            if (bloodGroupCoinsMap.containsKey(bloodGroup)) {
                Object[] coinsAndRate = bloodGroupCoinsMap.get(bloodGroup);
                coinsAndRate[0] = (int) coinsAndRate[0] + userCoins; // Update user coins
                coinsAndRate[1] = (int) coinsAndRate[1] + agentCoins; // Update agent coins
            } else {
                Object[] coinsAndRate = new Object[]{userCoins, agentCoins, rate};
                bloodGroupCoinsMap.put(bloodGroup, coinsAndRate);

            }
        }
        return bloodGroupCoinsMap;
    }


    //find coin value of each request for showing on admin dashboard
    public List<HashMap<String, String>> requestedUserCoins() {
        List<UserRequestModel> all = userRequestRepository.findAll();
        List<UserRequestModel> collect = all.stream()
                .filter(userRequestModel -> userRequestModel.getType().equalsIgnoreCase("donor"))
                .filter(userRequestModel -> userRequestModel.getStatus().equalsIgnoreCase("approved"))
                .collect(Collectors.toList());
        return collect.stream()
                .map(request -> {
                    HashMap<String, String> requestMap = new HashMap<>();
                    requestMap.put("createdBy", request.getUser().getUsername());
                    requestMap.put("bloodGroup", request.getBloodGroup());
                    Long endUserCoins = request.getUser().getCoins();
                    requestMap.put("endUserCoins", String.valueOf(endUserCoins));
                    String createdBy = request.getUser().getCreatedBy();

                    if (createdBy.equalsIgnoreCase("auto") || createdBy.equalsIgnoreCase("self")
                            || createdBy.equalsIgnoreCase("Admin")) {
                        requestMap.put("agentCoins", " - ");
                        requestMap.put("adminCoins", String.valueOf(endUserCoins));
                    } else {
                        int agentCoins = (int) ((endUserCoins * bloodBankRepository.findByUsername(createdBy).getCommision())/ 100);
                        requestMap.put("agentCoins", String.valueOf(agentCoins));
                        requestMap.put("adminCoins", String.valueOf(endUserCoins - agentCoins));
                    }

                    return requestMap;
                }).collect(Collectors.toList());
    }

}
