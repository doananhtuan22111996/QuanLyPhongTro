package com.example.anhtuan.quanlyphongtro.purchase;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.anhtuan.quanlyphongtro.api.IApi;
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

public class PurchaseInteractor {

    private IContract.IPurchaseInteractor iPurchaseInteractor;

    PurchaseInteractor(IContract.IPurchaseInteractor iPurchaseInteractor) {
        this.iPurchaseInteractor = iPurchaseInteractor;
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            String api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iPurchaseInteractor.getTokenSuccess(api_token);
        } else {
            iPurchaseInteractor.getTokenFailure();
        }
    }

    public void getPurchase(IApi iApi, String api_token) {
        Call<PurchaseResponse> call = iApi.getPurchase(api_token);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseResponse> call, @NonNull Response<PurchaseResponse> response) {
                if (Objects.requireNonNull(response.body()).getData() != null) {
                    List<Purchase> purchaseList = new ArrayList<>(Objects.requireNonNull(response.body()).getData());
                    iPurchaseInteractor.getPurchaseSuccess(purchaseList);
                } else {
                    iPurchaseInteractor.getPurchaseFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseResponse> call, @NonNull Throwable t) {
                iPurchaseInteractor.getPurchaseFailure();
            }
        });
    }
}
