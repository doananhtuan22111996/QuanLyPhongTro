package com.example.anhtuan.quanlyphongtro.purchase;

import android.content.SharedPreferences;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;
import com.example.anhtuan.quanlyphongtro.model.response.PurchaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasePresenterImp implements IContract.IPresenterPurchase {

    IContract.IViewPurchase iView;
    String api_token;
    List<Purchase> purchaseList;

    public PurchasePresenterImp(IContract.IViewPurchase iView) {
        this.iView = iView;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public String getApi_token() {
        return api_token;
    }

    @Override
    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iView.getTokenSuccess();
        } else {
            iView.getTokenFailure();
        }
    }

    @Override
    public void getPurchase(IApi iApi, PurchaseRequest purchaseRequest) {
        Call<PurchaseResponse> call = iApi.getPurchase(purchaseRequest);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(Call<PurchaseResponse> call, Response<PurchaseResponse> response) {
                if (response.body() != null) {
                    Purchase purchase = response.body().getData();
                    purchaseList.add(purchase);
                    iView.onSuccess();
                } else {
                    iView.onFailure();
                }
            }

            @Override
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                iView.onFailure();
            }
        });
    }
}
