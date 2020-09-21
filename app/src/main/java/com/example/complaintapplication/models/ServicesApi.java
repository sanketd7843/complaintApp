package com.example.complaintapplication.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesApi {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return serviceName;
    }

    public void setCity(String city) {
        this.serviceName = city;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
