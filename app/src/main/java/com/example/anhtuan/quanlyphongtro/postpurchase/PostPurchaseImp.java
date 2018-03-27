package com.example.anhtuan.quanlyphongtro.postpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPurchaseImp implements IContract.IPresenterPostPurchase {

    private IContract.IViewPurchase iViewPurchase;
    private BaseResponse baseResponse;
    private String api_token;
    private int flag, id;

    PostPurchaseImp(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        this.baseResponse = new BaseResponse();
    }

    String getApi_token() {
        return api_token;
    }

    public int getFlag() {
        return flag;
    }

    public int getId() {
        return id;
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
    public void postPurchase(IApi iApi, PurchaseRequest purchaseRequest) {
        Call<BaseResponse> call = iApi.postPurchase(api_token, purchaseRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    baseResponse = response.body();
                    assert baseResponse != null;
                    Log.d("STATUS", baseResponse.getStatus().toString());
                    iViewPurchase.onSuccess();
                } else {
                    iViewPurchase.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                iViewPurchase.onFailure();
            }
        });
    }

    @Override
    public void getFlag(Bundle bundle) {
        if (bundle != null) {
            flag = bundle.getInt(BaseStringKey.FLAG);
            id = bundle.getInt(BaseStringKey.ID);
            iViewPurchase.getTokenSuccess();
        } else {
            iViewPurchase.getTokenFailure();
        }
    }

    @Override
    public void updatePurchase(IApi iApi, PurchaseRequest purchaseRequest, int id) {
        Call<BaseResponse> call = iApi.putPurchase(purchaseRequest, id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body().getStatus() != null) {
                    if (response.body().getStatus().equals("true")) {
                        iViewPurchase.onSuccess();
                    } else {
                        iViewPurchase.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                iViewPurchase.onFailure();
            }
        });
    }

}
