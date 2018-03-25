package com.example.anhtuan.quanlyphongtro.personal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.DetailPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;
import com.example.anhtuan.quanlyphongtro.personal.adapter.MyPurchaseRecyclerViewAdapter;
import com.example.anhtuan.quanlyphongtro.postpurchase.PostPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.purchase.PurchaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PersonalMyPurchaseActivity extends AppCompatActivity implements IContract.IViewPurchase, View.OnClickListener {

    @BindView(R.id.img_back_personalmypurchase)
    ImageView imgBackPersonalmypurchase;
    @BindView(R.id.rcv_items_personalmypurchase)
    RecyclerView rcvItemsPersonalmypurchase;
    @BindView(R.id.lnl_postpurchase_personalmypurhcase)
    LinearLayout lnlPostpurchasePersonalmypurhcase;
    @BindView(R.id.lnl_home_personalmypurchase)
    LinearLayout lnlHomePersonalmypurchase;
    @BindView(R.id.lnl_user_personalmypurchase)
    LinearLayout lnlUserPersonalmypurchase;
    @BindView(R.id.pb_waitmypurchase)
    ProgressBar pbWaitmypurchase;

    SharedPreferences sharedPreferences;
    MyPurchaseRecyclerViewAdapter myPurchaseRecyclerViewAdapter;
    PersonalMyPurchaseImp personalMyPurchaseImp;
    IApi iApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_mypurchase);
        ButterKnife.bind(this);

        pbWaitmypurchase.setVisibility(View.VISIBLE);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        personalMyPurchaseImp = new PersonalMyPurchaseImp(this);
        rcvItemsPersonalmypurchase.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        imgBackPersonalmypurchase.setOnClickListener(this);
        lnlHomePersonalmypurchase.setOnClickListener(this);
        lnlPostpurchasePersonalmypurhcase.setOnClickListener(this);

        getToken();
    }

    @Override
    public void onSuccess() {
        pbWaitmypurchase.setVisibility(View.GONE);
        showMyPurchase();
        myPurchaseRecyclerViewAdapter.notifyDataSetChanged();
        Log.d("MYPURCHASE", "SUCCESS");
    }

    @Override
    public void onFailure() {
        Log.d("MYPURCHASE", "FAILURE");
    }

    @Override
    public void getTokenSuccess() {
        getMyPurchase();
        Log.d("TOKEN", "SUCCESS");
    }

    @Override
    public void getTokenFailure() {
        Log.d("TOKEN", "FAILURE");
    }

    @Override
    public void onClick(View v) {
        if (v == imgBackPersonalmypurchase) {
            onBackPressed();
        } else if (v == lnlHomePersonalmypurchase) {
            Intent intent = new Intent(PersonalMyPurchaseActivity.this, PurchaseActivity.class);
            startActivity(intent);
            finish();
        } else if (v == lnlPostpurchasePersonalmypurhcase) {
            Intent intent = new Intent(PersonalMyPurchaseActivity.this, PostPurchaseActivity.class);
            startActivity(intent);
        }
    }

    private void getToken() {
        if (sharedPreferences != null) {
            personalMyPurchaseImp.getTokenSharePreference(sharedPreferences);
        }
    }

    private void getMyPurchase() {
        PurchaseRequest purchaseRequest = new PurchaseRequest(personalMyPurchaseImp.getApi_token());
        personalMyPurchaseImp.getMyPurchase(iApi, purchaseRequest);
    }

    private void showMyPurchase() {
        MyPurchaseRecyclerViewAdapter myPurchaseRecyclerViewAdapter = new MyPurchaseRecyclerViewAdapter(this, personalMyPurchaseImp.getPurchaseList());
        rcvItemsPersonalmypurchase.setAdapter(myPurchaseRecyclerViewAdapter);
        myPurchaseRecyclerViewAdapter.setiOnClickItemPurchaseListener(new IContract.IOnClickItemPurchaseListener() {
            @Override
            public void onClickItemPurchase(int position) {
                Intent intent = new Intent(PersonalMyPurchaseActivity.this, DetailPurchaseActivity.class);
                intent.putExtra(BaseStringKey.USER_TOKEN, personalMyPurchaseImp.getApi_token());
                startActivity(intent);
            }
        });
    }

}
