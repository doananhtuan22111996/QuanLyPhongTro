package com.example.anhtuan.quanlyphongtro.purchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.DetailPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;
import com.example.anhtuan.quanlyphongtro.personal.PersonalMyPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.postpurchase.PostPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.purchase.adapter.PurchaseRecyclerViewAdaper;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PurchaseActivity extends AppCompatActivity implements IContract.IViewPurchase, View.OnClickListener {

    @BindView(R.id.pb_waitpurchase)
    ProgressBar pbWaitPurchase;
    @BindView(R.id.rcv_list_purchase)
    RecyclerView rcvListPurchase;
    @BindView(R.id.lnl_post_purchase)
    LinearLayout lnlPostPurchase;
    @BindView(R.id.lnl_user_purchase)
    LinearLayout lnlUserPurchase;
    @BindView(R.id.lnl_home_purchase)
    LinearLayout lnlHomePurchase;

    SharedPreferences sharedPreferences;
    PurchasePresenterImp purchasePresenterImp;
    PurchaseRecyclerViewAdaper purchaseRecyclerViewAdaper;
    IApi iApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);

        pbWaitPurchase.setVisibility(View.VISIBLE);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        purchasePresenterImp = new PurchasePresenterImp(this);
        rcvListPurchase.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        lnlPostPurchase.setOnClickListener(this);
        lnlHomePurchase.setOnClickListener(this);

        getToken();
    }

    @Override
    public void onSuccess() {
        pbWaitPurchase.setVisibility(View.GONE);
        showListPurchase();
        Log.d("PURCHASE", "SUCCESS");
    }

    @Override
    public void onFailure() {
        Log.d("PURCHASE", "SUCCESS");
    }

    @Override
    public void getTokenSuccess() {
        getPurchase();
        Log.d("TOKEN", "SUCCESS");
    }

    @Override
    public void getTokenFailure() {
        Log.d("TOKEN", "FAILURE");
    }

    private void getToken() {
        if (sharedPreferences != null) {
            purchasePresenterImp.getTokenSharePreference(sharedPreferences);
        }
    }

    private void getPurchase() {
        PurchaseRequest purchaseRequest = new PurchaseRequest(purchasePresenterImp.getApi_token());
        purchasePresenterImp.getPurchase(iApi, purchaseRequest);
    }

    private void showListPurchase() {
        purchaseRecyclerViewAdaper = new PurchaseRecyclerViewAdaper(this, purchasePresenterImp.getPurchaseList());
        rcvListPurchase.setAdapter(purchaseRecyclerViewAdaper);
        purchaseRecyclerViewAdaper.setiOnClickItemPurchaseListener(new IContract.IOnClickItemPurchaseListener() {
            @Override
            public void onClickItemPurchase(int position) {
                Intent intent = new Intent(PurchaseActivity.this, DetailPurchaseActivity.class);
                intent.putExtra(BaseStringKey.PURCHASE, purchasePresenterImp.getPurchaseList().get(position));
                startActivity(intent);
            }
        });
        purchaseRecyclerViewAdaper.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == lnlPostPurchase) {
            Intent intent = new Intent(PurchaseActivity.this, PostPurchaseActivity.class);
            intent.putExtra(BaseStringKey.FLAG, 1);
            startActivity(intent);
        } else if (v == lnlUserPurchase) {
            Intent intent = new Intent(PurchaseActivity.this, PersonalMyPurchaseActivity.class);
            startActivity(intent);
        } else if (v == lnlHomePurchase) {
            purchasePresenterImp.getPurchaseList().clear();
            pbWaitPurchase.setVisibility(View.VISIBLE);
            getPurchase();
        }
    }
}
