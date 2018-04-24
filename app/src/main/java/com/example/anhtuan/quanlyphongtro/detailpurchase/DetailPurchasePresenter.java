package com.example.anhtuan.quanlyphongtro.detailpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;

public class DetailPurchasePresenter implements IContract.IPurchaseInteractor.IDetailPurchaseInteractor {

    private IContract.IViewPurchase.IViewDetailPurchase iViewDetailPurchase;
    private DetailPurchaseInteractor detailPurchaseInteractor;

    DetailPurchasePresenter(IContract.IViewPurchase.IViewDetailPurchase iViewDetailPurchase) {
        this.iViewDetailPurchase = iViewDetailPurchase;
        detailPurchaseInteractor = new DetailPurchaseInteractor(this);
    }

    public void getIdSharePreference(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            detailPurchaseInteractor.getIdSharePreference(sharedPreferences);
        } else {
            iViewDetailPurchase.callIdFailure();
        }
    }

    public void getPurchaseBundel(Bundle bundle) {
        if (bundle != null) {
            detailPurchaseInteractor.getPurchaseBundel(bundle);
        } else {
            iViewDetailPurchase.callPurchaseFailure();
        }
    }

    @Override
    public void getPurchaseSuccess(Purchase purchase) {
        if (purchase == null) {
            iViewDetailPurchase.callPurchaseFailure();
        } else {
            iViewDetailPurchase.callPurchaseSuccess(purchase);
        }
    }

    @Override
    public void getPurchaseFailure() {
        iViewDetailPurchase.callPurchaseFailure();
    }

    @Override
    public void getIdSuccess(int id) {
        if (String.valueOf(id).isEmpty()) {
            iViewDetailPurchase.callIdFailure();
        } else {
            iViewDetailPurchase.callIdSuccess(id);
        }
    }

    @Override
    public void getIdFailure() {
        iViewDetailPurchase.callIdFailure();
    }
}
