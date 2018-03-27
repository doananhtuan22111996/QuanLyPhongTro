package com.example.anhtuan.quanlyphongtro.model.response;

import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PurchaseResponse extends BaseResponse implements Serializable{

    @SerializedName("data")
    @Expose
    private List<Purchase> data;

    public List<Purchase> getData() {
        return data;
    }

    public void setData(List<Purchase> data) {
        this.data = data;
    }
}
