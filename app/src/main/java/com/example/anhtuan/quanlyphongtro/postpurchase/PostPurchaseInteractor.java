package com.example.anhtuan.quanlyphongtro.postpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPurchaseInteractor {

    private IContract.IPurchaseInteractor.IPostPurchaseInteractor iPostPurchaseInteractor;

    PostPurchaseInteractor(IContract.IPurchaseInteractor.IPostPurchaseInteractor iPostPurchaseInteractor) {
        this.iPostPurchaseInteractor = iPostPurchaseInteractor;
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            String api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iPostPurchaseInteractor.getTokenSuccess(api_token);
        } else {
            iPostPurchaseInteractor.getTokenFailure();
        }
    }

    public void postPurchase(IApi iApi, PurchaseRequest purchaseRequest, String api_token) {
        Call<BaseResponse> call = iApi.postPurchase(api_token, purchaseRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    iPostPurchaseInteractor.postSuccess();
                } else {
                    iPostPurchaseInteractor.postFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                iPostPurchaseInteractor.postFailure();
            }
        });
    }

    public void getFlag(Bundle bundle) {
        if (bundle != null) {
            int flag = bundle.getInt(BaseStringKey.FLAG);
            int id = bundle.getInt(BaseStringKey.ID);
            Purchase purchase = (Purchase) bundle.getSerializable(BaseStringKey.PURCHASE);
            bundle.remove(BaseStringKey.PURCHASE);
            bundle.remove(BaseStringKey.ID);
            bundle.remove(BaseStringKey.FLAG);
            iPostPurchaseInteractor.getFlagSuccess(purchase, id, flag);
        } else {
            iPostPurchaseInteractor.getFlagFailure();
        }
    }

    public void updatePurchase(IApi iApi, PurchaseRequest purchaseRequest, int id, String api_token) {
        Call<BaseResponse> call = iApi.putPurchase(api_token, purchaseRequest, id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    iPostPurchaseInteractor.updateSuccess();
                } else {
                    iPostPurchaseInteractor.updateFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                iPostPurchaseInteractor.updateFailure();
            }
        });
    }
}
