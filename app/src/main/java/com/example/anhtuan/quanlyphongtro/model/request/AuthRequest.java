package com.example.anhtuan.quanlyphongtro.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRequest {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("password_confirmation")
    @Expose
    private String password_confirmation;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthRequest(String email, String password, String password_confirmation) {
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }
}
