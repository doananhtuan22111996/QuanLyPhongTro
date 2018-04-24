package com.example.anhtuan.quanlyphongtro.postpurchase;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

public class PostPurchasePresenter implements IContract.IPurchaseInteractor.IPostPurchaseInteractor {

    private IContract.IViewPurchase.IViewPostPurchase iViewPostPurchase;
    private PostPurchaseInteractor postPurchaseInteractor;

    PostPurchasePresenter(IContract.IViewPurchase.IViewPostPurchase iViewPostPurchase) {
        this.iViewPostPurchase = iViewPostPurchase;
        postPurchaseInteractor = new PostPurchaseInteractor(this);
    }

    public void getTokenSharePreference(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            postPurchaseInteractor.getTokenSharePreference(sharedPreferences);
        } else {
            iViewPostPurchase.callTokenFailure();
        }
    }

    public void postPurchase(IApi iApi, PurchaseRequest purchaseRequest, String token) {
        if (purchaseRequest.getTitle().isEmpty() || String.valueOf(purchaseRequest.getAcreage()).isEmpty() ||
                purchaseRequest.getAddress().isEmpty() || purchaseRequest.getPhone().isEmpty() ||
                String.valueOf(purchaseRequest.getPrice()).isEmpty()) {
            iViewPostPurchase.onPostFailure();
        } else {
            postPurchaseInteractor.postPurchase(iApi, purchaseRequest, token);
        }
    }

    public void getFlag(Bundle bundle) {
        if (bundle != null) {
            postPurchaseInteractor.getFlag(bundle);
        } else {
            iViewPostPurchase.callFlagFailure();
        }
    }

    public void updatePurchase(IApi iApi, PurchaseRequest purchaseRequest, int id, String token) {
        if (purchaseRequest.getTitle().isEmpty() || String.valueOf(purchaseRequest.getAcreage()).isEmpty() ||
                purchaseRequest.getAddress().isEmpty() || purchaseRequest.getPhone().isEmpty() ||
                String.valueOf(purchaseRequest.getPrice()).isEmpty()) {
            iViewPostPurchase.onPostFailure();
        } else {
            postPurchaseInteractor.updatePurchase(iApi, purchaseRequest, id, token);
        }
    }


    @Override
    public void getTokenSuccess(String token) {
        if (token.isEmpty()) {
            iViewPostPurchase.callTokenFailure();
        } else {
            iViewPostPurchase.callTokenSuccess(token);
        }
    }

    @Override
    public void getTokenFailure() {
        iViewPostPurchase.callTokenFailure();
    }

    @Override
    public void getFlagSuccess(Purchase purchase, int id, int flag) {
        if (purchase == null || String.valueOf(id).isEmpty() || String.valueOf(flag).isEmpty()) {
            iViewPostPurchase.callFlagFailure();
        } else {
            iViewPostPurchase.callFlagSuccess(purchase, id, flag);
        }
    }

    @Override
    public void getFlagFailure() {
        iViewPostPurchase.callFlagFailure();
    }

    @Override
    public void postSuccess() {
        iViewPostPurchase.onPostSuccess();
    }

    @Override
    public void postFailure() {
        iViewPostPurchase.onPostFailure();
    }

    @Override
    public void updateSuccess() {
        iViewPostPurchase.onUpdateSuccess();
    }

    @Override
    public void updateFailure() {
        iViewPostPurchase.onUpdateFailure();
    }
}
