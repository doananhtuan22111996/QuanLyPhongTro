package com.example.anhtuan.quanlyphongtro.detailpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.User;
import com.google.gson.Gson;

public class DetailPurchaseImp implements IContract.IPresenterDetailPurchase {

    private IContract.IViewPurchase iViewPurchase;
    private int id;
    private Purchase purchase;

    DetailPurchaseImp(IContract.IViewPurchase iViewPurchase) {
        this.iViewPurchase = iViewPurchase;
        this.purchase = new Purchase();
    }

    public int getId() {
        return id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    @Override
    public void getIdSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.INFO_USER, "").equals("")) {
            String tempUser = sharedPreferences.getString(BaseStringKey.INFO_USER, "");
            Gson gson = new Gson();
            User user = gson.fromJson(tempUser, User.class);
            id = user.getId();
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
