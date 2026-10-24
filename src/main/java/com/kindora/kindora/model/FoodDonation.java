package com.kindora.kindora.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FoodDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String donorName;
    private String foodName;
    private int quantity;
    private String location;
    private String safeUntil;
    private String status = "AVAILABLE";
@Column(name = "claimed_by")
private String claimedBy;
    public FoodDonation() {
    }

    public Long getId() {
        return id;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSafeUntil() {
        return safeUntil;
    }

    public void setSafeUntil(String safeUntil) {
        this.safeUntil = safeUntil;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    public String getClaimedBy() {
    return claimedBy;
}

public void setClaimedBy(String claimedBy) {
    this.claimedBy = claimedBy;
}
}
