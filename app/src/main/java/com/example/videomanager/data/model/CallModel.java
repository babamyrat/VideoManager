package com.example.videomanager.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallModel {
    @SerializedName("registration_token")
    @Expose
    private String registrationToken;
    @SerializedName("manager_id")
    @Expose
    private Integer managerId;
    @SerializedName("type")
    @Expose
    private String type;

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
