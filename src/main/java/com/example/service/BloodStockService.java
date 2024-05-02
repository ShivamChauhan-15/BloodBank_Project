package com.example.service;

import com.example.dto.BloodStockDto;
import com.example.dto.UserRequestDto;
import com.example.model.BloodStockModel;
import com.example.model.UserRequestModel;
import com.example.repository.BloodStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class BloodStockService {
    @Autowired
    private BloodStockRepository bloodStockRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BloodReportService bloodReportService;

    public void initializeBloodStock() {
        HashMap<String, Long> bloodGroups = new HashMap<>();
        bloodGroups.put("A+", 10l);
        bloodGroups.put("A-", 5l);
        bloodGroups.put("B+", 15l);
        bloodGroups.put("B-", 12l);
        bloodGroups.put("O+", 25l);
        bloodGroups.put("O-", 20l);
        bloodGroups.put("AB+", 30l);
        bloodGroups.put("AB-", 35l);
        // Initialize data with blood group names and initial quantity 0
        bloodGroups.forEach((k, v) -> {
            BloodStockModel bloodStock = new BloodStockModel();
            bloodStock.setBloodGroup(k);
            bloodStock.setCoins(v);
            bloodStock.setAvailableUnit(0);
            bloodStock.setLastUpdate(LocalDateTime.now());
            List<BloodStockModel> byBloodGroup = bloodStockRepository.findAll();
            boolean flag = true;
            for (BloodStockModel bloodStockModel : byBloodGroup) {
                if (bloodStockModel.getBloodGroup().equalsIgnoreCase(k)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                bloodStockRepository.save(bloodStock);
            }

        });
    }

    public List<BloodStockDto> getBloodStock() {
        return convertToDto(bloodStockRepository.findAll());
    }

    private List<BloodStockDto> convertToDto(List<BloodStockModel> stockModels) {
        List<BloodStockDto> users = new ArrayList<>();
        for (BloodStockModel user : stockModels) {
            users.add(modelMapper.map(user, BloodStockDto.class));
        }
        return users;
    }

    public String setBloodStockRepository(UserRequestModel userRequestModel) {
        BloodStockModel bloodStockModel = bloodStockRepository.getByBloodGroup(userRequestModel.getBloodGroup());
        int availableUnit = bloodStockModel.getAvailableUnit();
        if (userRequestModel.getType().equalsIgnoreCase("donor")) {
            System.out.println("in setBloodStock");
            bloodStockModel.setAvailableUnit(availableUnit + userRequestModel.getQuantity());
            bloodStockModel.setLastUpdate(LocalDateTime.now());
            return "ok";
        } else {
            int remaningBloodUnit = availableUnit - userRequestModel.getQuantity();
            if (remaningBloodUnit >= 0) {
                bloodStockModel.setAvailableUnit(remaningBloodUnit);
                bloodStockModel.setLastUpdate(LocalDateTime.now());
                return "ok";
            } else
                return "Insufficient blood amount";

        }
    }
}
