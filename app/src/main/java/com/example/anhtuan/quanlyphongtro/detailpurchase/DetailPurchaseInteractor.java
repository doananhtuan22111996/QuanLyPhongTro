package com.example.anhtuan.quanlyphongtro.detailpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.User;
import com.google.gson.Gson;

public class DetailPurchaseInteractor {

    private IContract.IPurchaseInteractor.IDetailPurchaseInteractor iDetailPurchaseInteractor;
    private int id;
    private Purchase purchase;

    DetailPurchaseInteractor(IContract.IPurchaseInteractor.IDetailPurchaseInteractor iDetailPurchaseInteractor) {
        this.iDetailPurchaseInteractor = iDetailPurchaseInteractor;
        purchase = new Purchase();
    }

    public void getIdSharePreference(SharedPreferences sharedPreferences) {
        if (!sharedPreferences.getString(BaseStringKey.INFO_USER, "").equals("")) {
            String tempUser = sharedPreferences.getString(BaseStringKey.INFO_USER, "");
            Gson gson = new Gson();
            User user = gson.fromJson(tempUser, User.class);
            id = user.getId();
            iDetailPurchaseInteractor.getIdSuccess(id);
        } else {
            iDetailPurchaseInteractor.getIdFailure();
        }
    }

    public void getPurchaseBundel(Bundle bundle) {
        if (bundle != null) {
            purchase = (Purchase) bundle.getSerializable(BaseStringKey.PURCHASE);
            iDetailPurchaseInteractor.getPurchaseSuccess(purchase);
        } else {
            iDetailPurchaseInteractor.getPurchaseFailure();
        }
    }
}
