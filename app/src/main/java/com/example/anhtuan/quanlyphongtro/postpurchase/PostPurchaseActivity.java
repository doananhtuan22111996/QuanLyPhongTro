package com.example.anhtuan.quanlyphongtro.postpurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;
import com.example.anhtuan.quanlyphongtro.personal.PersonalMyPurchaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PostPurchaseActivity extends AppCompatActivity implements View.OnClickListener, IContract.IViewPurchase {


    @BindView(R.id.img_back_postpurchase)
    ImageView imgBackPostpurchase;

    @BindView(R.id.edt_title_postpurchase)
    EditText edtTitlePostpurchase;
    @BindView(R.id.edt_price_postpurchase)
    EditText edtPricePostpurchase;
    @BindView(R.id.edt_acreage_postpurchase)
    EditText edtAcreagePostpurchase;
    @BindView(R.id.edt_phone_postpurchase)
    EditText edtPhonePostpurchase;
    @BindView(R.id.edt_address_postpurchase)
    EditText edtAddressPostpurchase;
    @BindView(R.id.edt_decription_postpurchase)
    EditText edtDecriptionPostpurchase;
    @BindView(R.id.btn_postpurchase)
    Button btnPostpurchase;
    @BindView(R.id.lnl_home_postpurchase)
    LinearLayout lnlHomePostpurchase;
    @BindView(R.id.lnl_user_postpurchase)
    LinearLayout lnlUserPostpurchase;
    @BindView(R.id.btn_save_postpurchase)
    Button btnSavePostpurchase;
    @BindView(R.id.img_camera1_postpurchase)
    ImageView imgCamera1Postpurchase;
    @BindView(R.id.img_camera2_postpurchase)
    ImageView imgCamera2Postpurchase;
    @BindView(R.id.img_camera3_postpurchase)
    ImageView imgCamera3Postpurchase;

    SharedPreferences sharedPreferences;
    PostPurchaseImp postPurchaseImp;
    IApi iApi;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_purchase);
        ButterKnife.bind(this);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        bundle = getIntent().getExtras();
        postPurchaseImp = new PostPurchaseImp(this);

        imgBackPostpurchase.setOnClickListener(this);
        lnlHomePostpurchase.setOnClickListener(this);
        lnlUserPostpurchase.setOnClickListener(this);
        btnPostpurchase.setOnClickListener(this);
        btnSavePostpurchase.setOnClickListener(this);

        getToken();
        getFrag();
    }

    @Override
    public void onClick(View v) {
        if (v == btnPostpurchase) {
            postPurchase();
        } else if (v == imgBackPostpurchase) {
            onBackPressed();
        } else if (v == lnlHomePostpurchase) {
            Intent intent = new Intent(PostPurchaseActivity.this, Purchase.class);
            startActivity(intent);
            finish();
        } else if (v == lnlUserPostpurchase) {
            Intent intent = new Intent(PostPurchaseActivity.this, PersonalMyPurchaseActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btnSavePostpurchase) {
            updatePurchase();
        }
    }

    @Override
    public void onSuccess() {
        finish();
        Toast.makeText(this, "POST SUCCCESS", Toast.LENGTH_SHORT).show();
        Log.d("POST", "SUCCESS");
    }

    @Override
    public void onFailure() {
        Log.d("POST", "FAILURE");
    }

    @Override
    public void getTokenSuccess() {
        if (postPurchaseImp.getFlag() == 0) {
            btnSavePostpurchase.setVisibility(View.VISIBLE);
            btnPostpurchase.setVisibility(View.GONE);
        } else if (postPurchaseImp.getFlag() == 1) {
            btnSavePostpurchase.setVisibility(View.GONE);
            btnPostpurchase.setVisibility(View.VISIBLE);
        }
        Log.d("TOKEN", "SUCCESS");
    }

    @Override
    public void getTokenFailure() {
        Log.d("TOKEN", "FAILURE");
    }

    private void getToken() {
        postPurchaseImp.getTokenSharePreference(sharedPreferences);
    }

    private void getFrag() {
        postPurchaseImp.getFlag(bundle);
    }

    private void postPurchase() {
        PurchaseRequest purchaseRequest = new PurchaseRequest(postPurchaseImp.getApi_token(),
                edtTitlePostpurchase.getText().toString(), Float.parseFloat(edtPricePostpurchase.getText().toString()),
                Float.parseFloat(edtAcreagePostpurchase.getText().toString()), edtPhonePostpurchase.getText().toString(),
                edtAddressPostpurchase.getText().toString(), edtDecriptionPostpurchase.getText().toString());
        postPurchaseImp.postPurchase(iApi, purchaseRequest);
    }

    private void updatePurchase() {
        PurchaseRequest purchaseRequest = new PurchaseRequest(postPurchaseImp.getApi_token(),
                edtTitlePostpurchase.getText().toString(), Float.parseFloat(edtPricePostpurchase.getText().toString()),
                Float.parseFloat(edtAcreagePostpurchase.getText().toString()), edtPhonePostpurchase.getText().toString(),
                edtAddressPostpurchase.getText().toString(), edtDecriptionPostpurchase.getText().toString());
        postPurchaseImp.updatePurchase(iApi, purchaseRequest, postPurchaseImp.getId());
    }
}
