package com.example.anhtuan.quanlyphongtro.detailpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;

public class DetailPurchaseImp implements IContract.IPresenterDetailPurchase {

    private IContract.IViewPurchase iViewPurchase;
    private String api_token;
    private Purchase purchase;

    public DetailPurchaseImp(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        this.purchase = new Purchase();
    }

    public String getApi_token() {
        return api_token;
    }

    public Purchase getPurchase() {
        return purchase;
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
    public void getPurchaseBundel(Bundle bundle) {
        if (bundle != null) {
            purchase = (Purchase) bundle.getSerializable(BaseStringKey.PURCHASE);
            iViewPurchase.onSuccess();
        } else {
            iViewPurchase.onFailure();
        }
    }
}
