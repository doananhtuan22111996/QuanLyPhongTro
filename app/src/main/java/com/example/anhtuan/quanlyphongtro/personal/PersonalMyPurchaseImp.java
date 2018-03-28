package com.example.anhtuan.quanlyphongtro.personal;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.response.PurchaseResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalMyPurchaseImp implements IContract.IPresenterPersonalMyPurchase {

    private IContract.IViewPurchase iViewPurchase;
    private String api_token;
    private List<Purchase> purchaseList;

    PersonalMyPurchaseImp(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        this.purchaseList = new ArrayList<>();
    }

    public String getApi_token() {
        return api_token;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    @Override
    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iViewPurchase.getTokenSuccess();
        } else {
            iViewPurchase.getTokenFailure();
        }
    }

    @Override
    public void getMyPurchase(IApi iApi) {
        Call<PurchaseResponse> call = iApi.getMyPurchase(api_token);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseResponse> call, @NonNull Response<PurchaseResponse> response) {
                if (response.body().getData() != null) {
                    purchaseList.addAll(response.body().getData());
                    Log.d("TAG", String.valueOf(purchaseList.size()));
                    iViewPurchase.onSuccess();
                } else {
                    iViewPurchase.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseResponse> call, @NonNull Throwable t) {
                iViewPurchase.onFailure();
            }
        });
    }
}