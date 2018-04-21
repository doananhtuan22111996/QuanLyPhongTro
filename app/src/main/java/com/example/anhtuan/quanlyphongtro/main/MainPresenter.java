package com.example.anhtuan.quanlyphongtro.main;

import android.os.Bundle;
import android.util.Log;

import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;

public class MainPresenter {

    private IContract.IViewMain iViewMain;
    private int flag;

    MainPresenter(IContract.IViewMain iViewMain) {
        this.iViewMain = iViewMain;
        this.flag = 0;
    }

    public int getFlag() {
        return flag;
    }

    public void getFlag(Bundle bundle) {
        if (bundle != null) {
            flag = bundle.getInt(BaseStringKey.FLAG);
            Log.d("FLAG", String.valueOf(flag));
            iViewMain.getFlagSuccess();
        } else {
            iViewMain.getFlagFailure();
        }
    }
}
