package com.example.anhtuan.quanlyphongtro.contract;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

public interface IContract {

    interface IOnClickItemPurchaseListener {
        void onClickItemPurchase(int position);
    }

    interface IViewLogin {
        void onSuccess();

        void onFailure();

        void checkUserSuccess();

        void checkUserFailure();
    }

    interface IViewPurchase {
        void onSuccess();

        void onFailure();

        void getTokenSuccess();

        void getTokenFailure();
    }

    interface IPresenterLogin {
        void getTokenSignIn(IApi iApi, AuthRequest authRequest, SharedPreferences sharedPreferences);

        void getTokenSignUp(IApi iApi, AuthRequest authRequest, SharedPreferences sharedPreferences);

        void checkTokenSharePreference(SharedPreferences sharedPreferences);
    }

    interface IPresenterPurchase {
        void getTokenSharePreference(SharedPreferences sharedPreferences);

        void getPurchase(IApi iApi, PurchaseRequest purchaseRequest);
    }

    interface IPresenterPostPurchase {
        void getTokenSharePreference(SharedPreferences sharedPreferences);

        void postPurchase(IApi iApi, PurchaseRequest purchaseRequest);

        void getFlag(Bundle bundle);

        void updatePurchase(IApi iApi, PurchaseRequest purchaseRequest, int id);
    }

    interface IPresenterPersonalMyPurchase {
        void getTokenSharePreference(SharedPreferences sharedPreferences);

        void getMyPurchase(IApi iApi, PurchaseRequest purchaseRequest);
    }

    interface IPresenterDetailPurchase {
        void getIdSharePreference(SharedPreferences sharedPreferences);

        void getPurchaseBundel(Bundle bundle);
    }
}
