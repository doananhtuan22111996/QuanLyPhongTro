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

public class PurchasePresenter {

    private IContract.IViewPurchase iView;
    private String api_token;
    private List<Purchase> purchaseList;

    PurchasePresenter(IContract.IViewPurchase iView) {
        this.iView = iView;
        purchaseList = new ArrayList<>();
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iView.getTokenSuccess();
        } else {
            iView.getTokenFailure();
        }
    }

    public void getPurchase(IApi iApi) {
        Call<PurchaseResponse> call = iApi.getPurchase(api_token);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseResponse> call, @NonNull Response<PurchaseResponse> response) {
                if (Objects.requireNonNull(response.body()).getData() != null) {
                    purchaseList.addAll(Objects.requireNonNull(response.body()).getData());
                    iView.onSuccess();
                } else {
                    iView.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseResponse> call, @NonNull Throwable t) {
                iView.onFailure();
            }
        });
    }
}
