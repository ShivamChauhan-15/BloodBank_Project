package com.example;

import com.example.service.AdminService;
import com.example.service.BloodStockService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShivamBloodBankApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(ShivamBloodBankApplication.class, args);

        AdminService admin = run.getBean(AdminService.class);
        admin.setAdmin();   //setting admin at the time of table creation
        BloodStockService bloodStockService=run.getBean(BloodStockService.class);
        bloodStockService.initializeBloodStock();  //setting blood groups with coin value

    }
    @Bean
    public ModelMapper getModelInstance(){
        return new ModelMapper();
    }

}
