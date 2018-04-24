package com.example.anhtuan.quanlyphongtro.purchase;

import android.content.SharedPreferences;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;

import java.util.List;

public class PurchasePresenter implements IContract.IPurchaseInteractor {

    private IContract.IViewPurchase iViewPurchase;
    private PurchaseInteractor purchaseInteractor;

    PurchasePresenter(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        purchaseInteractor = new PurchaseInteractor(this);
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            purchaseInteractor.getTokenSharePreference(sharedPreferences);
        } else {
            iViewPurchase.callTokenFailure();
        }
    }

    public void getPurchase(IApi iApi, String token) {
        if (token.isEmpty()) {
            iViewPurchase.callPurchaseFailure();
        } else {
            purchaseInteractor.getPurchase(iApi, token);
        }
    }

    @Override
    public void getPurchaseSuccess(List<Purchase> purchaseList) {
        if (purchaseList.isEmpty()) {
            iViewPurchase.callPurchaseFailure();
        } else {
            iViewPurchase.callPurchaseSuccess(purchaseList);
        }
    }

    @Override
    public void getPurchaseFailure() {
        iViewPurchase.callPurchaseFailure();
    }

    @Override
    public void getTokenSuccess(String token) {
        if (token.isEmpty()) {
            iViewPurchase.callTokenFailure();
        } else {
            iViewPurchase.callTokenSuccess(token);
        }
    }

    @Override
    public void getTokenFailure() {
        iViewPurchase.callTokenFailure();
    }
}
