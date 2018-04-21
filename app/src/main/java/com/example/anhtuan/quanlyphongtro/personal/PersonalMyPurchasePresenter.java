package com.example.anhtuan.quanlyphongtro.personal;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.response.PurchaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalMyPurchasePresenter {

    private IContract.IViewPurchase iViewPurchase;
    private String api_token;
    private List<Purchase> purchaseList;

    PersonalMyPurchasePresenter(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        this.purchaseList = new ArrayList<>();
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iViewPurchase.getTokenSuccess();
        } else {
            iViewPurchase.getTokenFailure();
        }
    }

    public void getMyPurchase(IApi iApi) {
        Call<PurchaseResponse> call = iApi.getMyPurchase(api_token);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseResponse> call, @NonNull Response<PurchaseResponse> response) {
                if (Objects.requireNonNull(response.body()).getData() != null) {
                    purchaseList.addAll(Objects.requireNonNull(response.body()).getData());
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

    public void deleteMyPurchase(IApi iApi, int id) {
        Log.d("TOKEN", api_token);
        Call<BaseResponse> call = iApi.deletePurchase(api_token, id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    iViewPurchase.getTokenSuccess();
                } else {
                    iViewPurchase.getTokenFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                iViewPurchase.getTokenFailure();
            }
        });
    }
}
