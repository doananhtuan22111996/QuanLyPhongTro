package com.example.anhtuan.quanlyphongtro.postpurchase;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostPurchasePresenter {

    private IContract.IViewPurchase iViewPurchase;
    private BaseResponse baseResponse;
    private Purchase purchase;
    private String api_token;
    private int flag, id;

    PostPurchasePresenter(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        this.baseResponse = new BaseResponse();
        this.purchase = new Purchase();
        this.flag = 0;
        this.id = 0;
    }

    public int getFlag() {
        return flag;
    }

    public int getId() {
        return id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            api_token = sharedPreferences.getString(BaseStringKey.USER_TOKEN, "");
            iViewPurchase.getTokenSuccess();
        } else {
            iViewPurchase.getTokenFailure();
        }

    }

    public void postPurchase(IApi iApi, PurchaseRequest purchaseRequest) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("title", purchaseRequest.getTitle());
        builder.addFormDataPart("price", String.valueOf(purchaseRequest.getPrice()));
        builder.addFormDataPart("acreage", String.valueOf(purchaseRequest.getAcreage()));
        builder.addFormDataPart("phone", purchaseRequest.getPhone());
        builder.addFormDataPart("address", purchaseRequest.getAddress());
        builder.addFormDataPart("description", purchaseRequest.getDescription());
        for (File file : purchaseRequest.getImages()) {
            builder.addFormDataPart("images[][image]", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }

        Call<BaseResponse> call = iApi.postPurchase(api_token, builder.build());
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
                    baseResponse = response.body();
                    assert baseResponse != null;
                    Log.d("STATUSsssssssssssssss", baseResponse.getStatus().toString());
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

    public void getFlag(Bundle bundle) {
        if (bundle != null) {
            flag = bundle.getInt(BaseStringKey.FLAG);
            id = bundle.getInt(BaseStringKey.ID);
            purchase = (Purchase) bundle.getSerializable(BaseStringKey.PURCHASE);
            Log.d("FLAG_POST", flag + "/" + id);
            bundle.remove(BaseStringKey.PURCHASE);
            bundle.remove(BaseStringKey.ID);
            bundle.remove(BaseStringKey.FLAG);
            iViewPurchase.getFlagSuccess();
        } else {
            flag = 0;
            iViewPurchase.getFlagFailure();
        }
    }

    public void updatePurchase(IApi iApi, PurchaseRequest purchaseRequest, int id) {
        Call<BaseResponse> call = iApi.putPurchase(api_token, purchaseRequest, id);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                if (response.body() != null) {
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

}
