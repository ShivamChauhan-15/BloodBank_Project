package com.example.dto;

import java.time.LocalDateTime;

public class BloodStockDto {
    private String bloodGroup;
    private int availableUnit=0;
    private LocalDateTime lastUpdate;
    private Long coins;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }

    public int getAvailableUnit() {
        return availableUnit;
    }

    public void setAvailableUnit(int availableUnit) {
        this.availableUnit = availableUnit;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
