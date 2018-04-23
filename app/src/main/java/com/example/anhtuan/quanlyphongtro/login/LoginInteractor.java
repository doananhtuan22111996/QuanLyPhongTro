package com.example.anhtuan.quanlyphongtro.login;

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

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor {

    private IContract.ILoginInteractor loginInteractor;
    private User user;

    LoginInteractor(IContract.ILoginInteractor loginInteractor) {
        this.loginInteractor = loginInteractor;
        user = new User();
    }

    public void checkLoginSignIn(IApi iApi, AuthRequest authRequest, final SharedPreferences sharedPreferences) {
        Call<UserResponse> call = iApi.getUserTokenSignIn(authRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.body() != null) {
                    user = Objects.requireNonNull(response.body()).getData();
                    writeInfoUserSharePreference(sharedPreferences, user, user.getApiToken());
                    loginInteractor.onCheckSuccess();
                } else {
                    loginInteractor.onCheckFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                loginInteractor.onCheckFailure();
            }
        });
    }

    public void checkLoginSignUp(IApi iApi, AuthRequest authRequest, final SharedPreferences sharedPreferences) {
        Call<UserResponse> call = iApi.getUserTokenSignUp(authRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.body() != null) {
                    user = Objects.requireNonNull(response.body()).getData();
                    writeInfoUserSharePreference(sharedPreferences, user, user.getApiToken());
                    loginInteractor.onCheckSuccess();
                } else {
                    loginInteractor.onCheckFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                loginInteractor.onCheckFailure();
            }
        });
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


}
