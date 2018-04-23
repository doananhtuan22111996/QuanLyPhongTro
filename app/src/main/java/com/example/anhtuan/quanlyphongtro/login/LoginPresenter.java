package com.example.anhtuan.quanlyphongtro.login;

import android.content.SharedPreferences;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;

public class LoginPresenter implements IContract.ILoginInteractor {

    private IContract.IViewLogin iView;
    private LoginInteractor loginInteractor;

    LoginPresenter(IContract.IViewLogin iView) {
        this.iView = iView;
        loginInteractor = new LoginInteractor(this);
    }

    public void getTokenSignIn(IApi iApi, AuthRequest authRequest, final SharedPreferences sharedPreferences) {
        if ((authRequest.getEmail().isEmpty() || authRequest.getPassword().isEmpty())) {
            iView.onFailure();
        } else {
            loginInteractor.checkLoginSignIn(iApi, authRequest, sharedPreferences);
        }
    }

    public void getTokenSignUp(IApi iApi, AuthRequest authRequest, final SharedPreferences sharedPreferences) {
        if ((authRequest.getEmail().isEmpty() || authRequest.getPassword().isEmpty() || authRequest.getPassword_confirmation().isEmpty())) {
            iView.onFailure();
        } else {
            loginInteractor.checkLoginSignUp(iApi, authRequest, sharedPreferences);
        }
    }

    @Override
    public void onCheckSuccess() {
        iView.onSuccess();
    }

    @Override
    public void onCheckFailure() {
        iView.onFailure();
    }
}
