package com.example.anhtuan.quanlyphongtro.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class PurchaseRequest {

    @SerializedName("Http-Auth-Token")
    @Expose
    String api_token;
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
    private List<String> images;

    public PurchaseRequest(String api_token, String title, float price, float acreage, String phone, String address, String description, List<String> images) {
        this.api_token = api_token;
        this.title = title;
        this.price = price;
        this.acreage = acreage;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.images = images;
    }

    public PurchaseRequest(String api_token, String title, float price, float acreage, String phone, String address, String description) {
        this.api_token = api_token;
        this.title = title;
        this.price = price;
        this.acreage = acreage;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    public PurchaseRequest(String api_token) {
        this.api_token = api_token;
    }
}
