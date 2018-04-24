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

public class PersonalMyPurchaseInteractor {

    private IContract.IPurchaseInteractor.IPersonalMyPurchaseInteractor iPersonalMyPurchaseInteractor;

    PersonalMyPurchaseInteractor(IContract.IPurchaseInteractor.IPersonalMyPurchaseInteractor iPersonalMyPurchaseInteractor) {
        this.iPersonalMyPurchaseInteractor = iPersonalMyPurchaseInteractor;
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            String api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iPersonalMyPurchaseInteractor.getTokenSuccess(api_token);
        } else {
            iPersonalMyPurchaseInteractor.getTokenFailure();
        }
    }

    public void getMyPurchase(IApi iApi, String api_token) {
        Call<PurchaseResponse> call = iApi.getMyPurchase(api_token);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<PurchaseResponse> call, @NonNull Response<PurchaseResponse> response) {
                if (Objects.requireNonNull(response.body()).getData() != null) {
                    List<Purchase> purchaseList = new ArrayList<>(Objects.requireNonNull(response.body()).getData());
                    Log.d("TAG", String.valueOf(purchaseList.size()));
                    iPersonalMyPurchaseInteractor.getMyPurchaseSuccess(purchaseList);
                } else {
                    iPersonalMyPurchaseInteractor.getMyPurchaseFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PurchaseResponse> call, @NonNull Throwable t) {
                iPersonalMyPurchaseInteractor.getMyPurchaseFailure();
            }
        });
    }

    public void deleteMyPurchase(IApi iApi, int id, String api_token) {
        Call<BaseResponse> call = iApi.deletePurchase(api_token, id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    iPersonalMyPurchaseInteractor.deleteSuccess();
                } else {
                    iPersonalMyPurchaseInteractor.deleteFailure();;
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                iPersonalMyPurchaseInteractor.deleteFailure();
            }
        });
    }
}
