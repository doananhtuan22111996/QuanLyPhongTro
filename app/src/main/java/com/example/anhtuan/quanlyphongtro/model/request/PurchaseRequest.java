package com.example.anhtuan.quanlyphongtro.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class PurchaseRequest {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("acreage")
    @Expose
    private float acreage;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images[image]")
    @Expose
    private List<File> images;

    public PurchaseRequest(String title, float price, float acreage, String phone, String address, String description, List<File> images) {
        this.title = title;
        this.price = price;
        this.acreage = acreage;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.images = images;
    }

    public PurchaseRequest(String title, float price, float acreage, String phone, String address, String description) {
        this.title = title;
        this.price = price;
        this.acreage = acreage;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public float getAcreage() {
        return acreage;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public List<File> getImages() {
        return images;
    }
}
