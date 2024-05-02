package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blood_bank_id;
    @NotEmpty
    private String role;
    @NotNull
    private LocalDateTime createdOn;
    @NotEmpty
    private String username;
    @NotEmpty
    private String name;
    @NotNull
    private Date dob;
    @NotEmpty
    private String bloodGroup;
    @NotEmpty
    private String password;
    @NotEmpty
    private String createdBy;
    private boolean firstLogin;
    private long loginAttempt = 0;
    private boolean lockStatus = false;
    @NotEmpty
    private String address;
    private float commision;
    private Long coins=0l;

    public String getCreatedBy() {
        return createdBy;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroup() {
        return bloodGroup;
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

    public void setCommision(float commision) {
        this.commision = commision;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Date getDob() {
        return dob;
    }
}
