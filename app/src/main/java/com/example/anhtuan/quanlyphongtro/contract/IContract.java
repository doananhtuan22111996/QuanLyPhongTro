package com.example.anhtuan.quanlyphongtro.contract;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

import java.util.List;

public interface IContract {

    interface IOnClickItemPurchaseListener {
        void onClickItemPurchase(int position);

        void onClickItemDeletePurchase(int postition);
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

        void getFlagSuccess();

        void getFlagFailure();
    }

    interface IViewMain {
        void getFlagSuccess();

        void getFlagFailure();
    }

    interface IPresenterMain {
        void getFlag(Bundle bundle);
    }

    interface IPresenterLogin {
        void getTokenSignIn(IApi iApi, AuthRequest authRequest, SharedPreferences sharedPreferences);

        void getTokenSignUp(IApi iApi, AuthRequest authRequest, SharedPreferences sharedPreferences);
    }

    interface IPresenterPurchase {
        void getTokenSharePreference(SharedPreferences sharedPreferences);

        void getPurchase(IApi iApi);
    }

    interface IPresenterPostPurchase {
        void getTokenSharePreference(SharedPreferences sharedPreferences);

        void postPurchase(IApi iApi, PurchaseRequest purchaseRequest);

        void getFlag(Bundle bundle);

        void updatePurchase(IApi iApi, PurchaseRequest purchaseRequest, int id);
    }

    interface IPresenterPersonalMyPurchase {
        void getTokenSharePreference(SharedPreferences sharedPreferences);

        void getMyPurchase(IApi iApi);

        void deleteMyPurchase(IApi iApi, int id);
    }

    interface IPresenterDetailPurchase {
        void getIdSharePreference(SharedPreferences sharedPreferences);

        void getPurchaseBundel(Bundle bundle);
    }
}
