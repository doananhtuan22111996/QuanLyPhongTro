package com.example.anhtuan.quanlyphongtro.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequest {

    @SerializedName("Http-Auth-Token")
    @Expose
    String api_token;
    @SerializedName("old_password")
    @Expose
    String old_password;
    @SerializedName("new_password")
    @Expose
    String new_password;
    @SerializedName("new_password_confirmation")
    @Expose
    String new_password_confirmation;

    public UserRequest(String api_token, String old_password, String new_password, String new_password_confirmation) {
        this.api_token = api_token;
        this.old_password = old_password;
        this.new_password = new_password;
        this.new_password_confirmation = new_password_confirmation;
    }

    public UserRequest(String api_token) {
        this.api_token = api_token;
    }
}
