package com.example.dto;

import javax.validation.constraints.NotEmpty;


public class UserLoginDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String currentPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
