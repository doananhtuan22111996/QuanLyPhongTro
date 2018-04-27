package com.example.anhtuan.quanlyphongtro.detailpurchase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.adapter.DetailPurchaseViewPagerAdapter;
import com.example.anhtuan.quanlyphongtro.main.MainActivity;
import com.example.anhtuan.quanlyphongtro.model.Purchase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPurchaseActivity extends AppCompatActivity implements IContract.IViewPurchase.IViewDetailPurchase, View.OnClickListener {

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
    @BindView(R.id.img_edit_detailpurchase)
    ImageView imgEditDetailpurchase;

    DetailPurchaseViewPagerAdapter detailPurchaseViewPagerAdapter;
    SharedPreferences sharedPreferences;
    DetailPurchasePresenter detailPurchasePresenter;
    Bundle bundle;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_purchase);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        detailPurchasePresenter = new DetailPurchasePresenter(this);
        detailPurchaseViewPagerAdapter = new DetailPurchaseViewPagerAdapter(getSupportFragmentManager());

        vpImageFragdetailpurchase.setAdapter(detailPurchaseViewPagerAdapter);

        imgBackDetailpurchase.setOnClickListener(this);

        getId();
    }

    @Override
    public void callPurchaseSuccess(final Purchase purchase) {
        if (this.id == purchase.getUser().getId()) {
            imgEditDetailpurchase.setVisibility(View.VISIBLE);
        } else {
            imgEditDetailpurchase.setVisibility(View.GONE);
        }
        showDetailPurchase(purchase);
        imgEditDetailpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPurchaseActivity.this, MainActivity.class);
                intent.putExtra(BaseStringKey.ID, purchase.getId());
                intent.putExtra(BaseStringKey.FLAG, 1);
                intent.putExtra(BaseStringKey.PURCHASE, purchase);
                startActivity(intent);
            }
        });
    }

    @Override
    public void callPurchaseFailure() {
        Toast.makeText(this, "GET PURCHASE FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callIdSuccess(int id) {
        this.id = id;
        getPurchase();
    }

    @Override
    public void callIdFailure() {
        Toast.makeText(this, "GET ID FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == imgBackDetailpurchase) {
            onBackPressed();
        }
    }

    private void getId() {
        detailPurchasePresenter.getIdSharePreference(sharedPreferences);
    }

    private void getPurchase() {
        detailPurchasePresenter.getPurchaseBundel(bundle);
    }

    private void showDetailPurchase(final Purchase purchase) {
        tvTitleDetailpurchase.setText(purchase.getTitle());
        tvPriceDetailpurchase.setText(String.valueOf(purchase.getPrice()));
        tvAcreageDetailpurchase.setText(String.valueOf(purchase.getAddress()));
        tvAddressDetailpurchase.setText(purchase.getAddress());
        tvPhoneDetailpuchase.setText(purchase.getPhone());
        tvDecriptionDetailpurchase.setText(purchase.getDescription());
        tvPhoneDetailpuchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPermissionGranted(purchase);
            }
        });
    }

    public void isPermissionGranted(Purchase purchase) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                call_action(purchase);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            call_action(purchase);
        }
    }

    @SuppressLint("MissingPermission")
    private void call_action(Purchase purchase) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + purchase.getPhone()));
        startActivity(callIntent);
    }
}
