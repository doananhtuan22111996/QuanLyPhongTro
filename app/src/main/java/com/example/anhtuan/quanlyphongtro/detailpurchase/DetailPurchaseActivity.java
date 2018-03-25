package com.example.anhtuan.quanlyphongtro.detailpurchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.adapter.DetailPurchaseViewPagerAdapter;
import com.example.anhtuan.quanlyphongtro.personal.PersonalMyPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.postpurchase.PostPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.purchase.PurchaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPurchaseActivity extends AppCompatActivity implements IContract.IViewPurchase, View.OnClickListener {

    @BindView(R.id.img_back_detailpurchase)
    ImageView imgBackDetailpurchase;
    @BindView(R.id.tv_title_detailpurchase)
    TextView tvTitleDetailpurchase;
    @BindView(R.id.vp_image_fragdetailpurchase)
    ViewPager vpImageFragdetailpurchase;
    @BindView(R.id.tv_price_detailpurchase)
    TextView tvPriceDetailpurchase;
    @BindView(R.id.tv_acreage_detailpurchase)
    TextView tvAcreageDetailpurchase;
    @BindView(R.id.tv_address_detailpurchase)
    TextView tvAddressDetailpurchase;
    @BindView(R.id.tv_phone_detailpuchase)
    TextView tvPhoneDetailpuchase;
    @BindView(R.id.tv_decription_detailpurchase)
    TextView tvDecriptionDetailpurchase;
    @BindView(R.id.lnl_post_detailpurchase)
    LinearLayout lnlPostDetailpurchase;
    @BindView(R.id.lnl_home_detailpurchase)
    LinearLayout lnlHomeDetailpurchase;
    @BindView(R.id.lnl_user_detailpurchase)
    LinearLayout lnlUserDetailpurchase;

    DetailPurchaseViewPagerAdapter detailPurchaseViewPagerAdapter;
    SharedPreferences sharedPreferences;
    DetailPurchaseImp detailPurchaseImp;
    Bundle bundle;
    String apiTokenDetail;
    @BindView(R.id.img_edit_detailpurchase)
    ImageView imgEditDetailpurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_purchase);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        detailPurchaseImp = new DetailPurchaseImp(this);
        detailPurchaseViewPagerAdapter = new DetailPurchaseViewPagerAdapter(getSupportFragmentManager());

        vpImageFragdetailpurchase.setAdapter(detailPurchaseViewPagerAdapter);

        imgBackDetailpurchase.setOnClickListener(this);
        lnlHomeDetailpurchase.setOnClickListener(this);
        lnlPostDetailpurchase.setOnClickListener(this);
        lnlUserDetailpurchase.setOnClickListener(this);
        imgEditDetailpurchase.setOnClickListener(this);

        getToken();
    }

    @Override
    public void onSuccess() {
        showDetailPurchase();
        Log.d("PURCHASE", "SUCCESS");
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "FAILURE", Toast.LENGTH_SHORT).show();
        Log.d("PURCHASE", "FAILURE");
    }

    @Override
    public void getTokenSuccess() {
        getPurchase();
        if (apiTokenDetail.equals(detailPurchaseImp.getApi_token())) {
            imgEditDetailpurchase.setVisibility(View.VISIBLE);
        } else {
            imgEditDetailpurchase.setVisibility(View.GONE);
        }
        Log.d("TOKEN", "SUCCESS");
    }

    @Override
    public void getTokenFailure() {
        Log.d("TOKEN", "FAILURE");
    }

    @Override
    public void onClick(View v) {
        if (v == imgBackDetailpurchase) {
            onBackPressed();
        } else if (v == lnlHomeDetailpurchase) {
            Intent intent = new Intent(DetailPurchaseActivity.this, PurchaseActivity.class);
            startActivity(intent);
            finish();
        } else if (v == lnlPostDetailpurchase) {
            Intent intent = new Intent(DetailPurchaseActivity.this, PostPurchaseActivity.class);
            startActivity(intent);
        } else if (v == lnlUserDetailpurchase) {
            Intent intent = new Intent(DetailPurchaseActivity.this, PersonalMyPurchaseActivity.class);
            startActivity(intent);
        } else if (v == imgEditDetailpurchase){
            Intent intent = new Intent(DetailPurchaseActivity.this, PostPurchaseActivity.class);
            //TODO Changes button Dang ki -> Save
            startActivity(intent);
        }
    }

    private void getToken() {
        if (sharedPreferences != null) {
            detailPurchaseImp.getTokenSharePreference(sharedPreferences);
        }
        apiTokenDetail = bundle.getString(BaseStringKey.USER_TOKEN);
    }

    private void getPurchase() {
        detailPurchaseImp.getPurchaseBundel(bundle);
    }

    private void showDetailPurchase() {
        tvTitleDetailpurchase.setText(detailPurchaseImp.getPurchase().getTitle());
        tvPriceDetailpurchase.setText(String.valueOf(detailPurchaseImp.getPurchase().getPrice()));
        tvAcreageDetailpurchase.setText(String.valueOf(detailPurchaseImp.getPurchase().getAddress()));
        tvAddressDetailpurchase.setText(detailPurchaseImp.getPurchase().getAddress());
        tvPhoneDetailpuchase.setText(detailPurchaseImp.getPurchase().getPhone());
        tvDecriptionDetailpurchase.setText(detailPurchaseImp.getPurchase().getDescription());
    }

}
