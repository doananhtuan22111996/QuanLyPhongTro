package com.example.anhtuan.quanlyphongtro.personal;


import android.content.SharedPreferences;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;

import java.util.List;

public class PersonalMyPurchasePresenter implements IContract.IPurchaseInteractor.IPersonalMyPurchaseInteractor {

    private IContract.IViewPurchase.IViewPersonalMyPurchase iViewPersonalMyPurchase;
    private PersonalMyPurchaseInteractor personalMyPurchaseInteractor;

    PersonalMyPurchasePresenter(IContract.IViewPurchase.IViewPersonalMyPurchase iViewPersonalMyPurchase) {
        this.iViewPersonalMyPurchase = iViewPersonalMyPurchase;
        personalMyPurchaseInteractor = new PersonalMyPurchaseInteractor(this);
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            personalMyPurchaseInteractor.getTokenSharePreference(sharedPreferences);
        } else {
            iViewPersonalMyPurchase.callTokenFailure();
        }
    }

    public void getMyPurchase(IApi iApi, String api_token) {
        if (api_token.isEmpty()) {
            iViewPersonalMyPurchase.callMyPurchaseFailure();
        } else {
            personalMyPurchaseInteractor.getMyPurchase(iApi, api_token);
        }
    }

    public void deleteMyPurchase(IApi iApi, int id, String api_token) {
        if (String.valueOf(id).isEmpty() || api_token.isEmpty()) {
            iViewPersonalMyPurchase.ondDeleteFailure();
        } else {
            personalMyPurchaseInteractor.deleteMyPurchase(iApi, id, api_token);
        }
    }

    @Override
    public void getTokenSuccess(String token) {
        if (token.isEmpty()) {
            iViewPersonalMyPurchase.callTokenFailure();
        } else {
            iViewPersonalMyPurchase.callTokenSuccess(token);
        }
    }

    @Override
    public void getTokenFailure() {
        iViewPersonalMyPurchase.callTokenFailure();
    }

    @Override
    public void getMyPurchaseSuccess(List<Purchase> purchasesList) {
        if (purchasesList.isEmpty()) {
            iViewPersonalMyPurchase.callMyPurchaseFailure();
        } else {
            iViewPersonalMyPurchase.callMyPurchaseSuccess(purchasesList);
        }
    }

    @Override
    public void getMyPurchaseFailure() {
        iViewPersonalMyPurchase.callMyPurchaseFailure();
    }

    @Override
    public void deleteSuccess() {
        iViewPersonalMyPurchase.onDeleteSuccess();
    }

    @Override
    public void deleteFailure() {
        iViewPersonalMyPurchase.ondDeleteFailure();
    }
}
