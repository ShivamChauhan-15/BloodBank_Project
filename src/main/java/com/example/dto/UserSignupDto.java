package com.example.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;

public class UserSignupDto {
    @NotEmpty  //prevent from null and empty
    private String username;
    @NotEmpty
    private String name;
    @NotNull
    private Date dob;
    @NotEmpty
    private String bloodGroup;

    @NotEmpty
    private String address;
    private float commision;
    private String password;
    private String role;
    private String createdBy;
    private LocalDateTime createdOn;
    private boolean isFirstLogin;
    private long loginAttempt=0;
    private boolean lockStatus=false;
    private Long coins;

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }

    public long getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(long loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public boolean isLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }
    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getCommision() {
        return commision;
    }

    public void setCommision(float commision) {this.commision = commision;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    public LocalDateTime getCreatedOn() {return createdOn;}

    public void setCreatedOn(LocalDateTime createdOn) {this.createdOn = createdOn;}

    public String getCreatedBy() {return createdBy;}

    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    public String getBloodGroup() {return bloodGroup;}

    public void setBloodGroup(String bloodGroup) {this.bloodGroup = bloodGroup;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setDob(Date dob){this.dob=dob;}

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    public Date getDob(){return dob;}

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }
}

