package com.example.anhtuan.quanlyphongtro.login.presenter;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.User;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;
import com.example.anhtuan.quanlyphongtro.model.response.UserResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImp implements IContract.IPresenterLogin {

    private IContract.IViewLogin iView;
    private User user;

    public LoginPresenterImp(IContract.IViewLogin iView) {
        this.iView = iView;
        this.user = new User();
    }

    private void writeInfoUserSharePreference(SharedPreferences sharedPreferences, User user, String api_token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
        Gson gson = new Gson();
        editor.putString(BaseStringKey.USER_TOKEN, api_token);
        String tempUser = gson.toJson(user);
        editor.putString(BaseStringKey.INFO_USER, tempUser);
        editor.apply();
        Log.d("WRITE", "SUCCESS");
    }

    @Override
    public void getTokenSignIn(IApi iApi, AuthRequest authRequest, final SharedPreferences sharedPreferences) {
        Call<UserResponse> call = iApi.getUserTokenSignIn(authRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.body() != null) {
                    user = response.body().getData();
                    Log.d("MESSAGE", response.body().getMessage());
                    writeInfoUserSharePreference(sharedPreferences, user, user.getApiToken());
                    iView.onSuccess();
                } else {
                    iView.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                iView.onFailure();
            }
        });
    }

    @Override
    public void getTokenSignUp(IApi iApi, AuthRequest authRequest, final SharedPreferences sharedPreferences) {
        Call<UserResponse> call = iApi.getUserTokenSignUp(authRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.body() != null) {
                    user = response.body().getData();
                    writeInfoUserSharePreference(sharedPreferences, user, user.getApiToken());
                    iView.onSuccess();
                } else {
                    iView.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                iView.onFailure();
            }
        });
    }

    @Override
    public void checkTokenSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.USER_TOKEN, "").equals("")) {
            Log.d("TOKEN", sharedPreferences.getString(BaseStringKey.USER_TOKEN, ""));
            iView.checkUserSuccess();
        } else {
            iView.checkUserFailure();
        }

    }

}
