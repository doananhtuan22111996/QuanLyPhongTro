package com.example.anhtuan.quanlyphongtro.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.personal.PersonalMyPurchaseFragment;
import com.example.anhtuan.quanlyphongtro.postpurchase.PostPurchaseFragment;
import com.example.anhtuan.quanlyphongtro.purchase.PurchaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IContract.IViewMain {

    @BindView(R.id.rl_fragment)
    RelativeLayout rlFragment;
    @BindView(R.id.lnl_post_purchase)
    LinearLayout lnlPostPurchase;
    @BindView(R.id.lnl_home_purchase)
    LinearLayout lnlHomePurchase;
    @BindView(R.id.lnl_user_purchase)
    LinearLayout lnlUserPurchase;
    @BindView(R.id.img_postPurchase)
    ImageView imgPostPurchase;
    @BindView(R.id.tv_postPurchase)
    TextView tvPostPurchase;
    @BindView(R.id.img_homePurchase)
    ImageView imgHomePurchase;
    @BindView(R.id.tv_homePurchase)
    TextView tvHomePurchase;
    @BindView(R.id.img_userPurchase)
    ImageView imgUserPurchase;
    @BindView(R.id.tv_userPurchase)
    TextView tvUserPurchase;

    Bundle bundle;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        mainPresenter = new MainPresenter(this);

        lnlPostPurchase.setOnClickListener(this);
        lnlHomePurchase.setOnClickListener(this);
        lnlUserPurchase.setOnClickListener(this);

        getFlag();
    }

    @Override
    public void onClick(View v) {
        if (v == lnlPostPurchase) {
            replaceFragmentContent(PostPurchaseFragment.newInstance());
            setColorFooder(0);
        } else if (v == lnlHomePurchase) {
            replaceFragmentContent(PurchaseFragment.newInstance());
            setColorFooder(1);
            removeIntent();
        } else if (v == lnlUserPurchase) {
            replaceFragmentContent(PersonalMyPurchaseFragment.newInstance());
            setColorFooder(2);
            removeIntent();
        }
    }

    @Override
    public void getFlagSuccess() {
        if (mainPresenter.getFlag() == 1) {
            replaceFragmentContent(PostPurchaseFragment.newInstance());
            setColorFooder(0);
        }
    }

    @Override
    public void getFlagFailure() {
        replaceFragmentContent(PurchaseFragment.newInstance());
        setColorFooder(1);
    }

    private void getFlag() {
        mainPresenter.getFlag(bundle);
    }

    private void replaceFragmentContent(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rl_fragment, fragment);
            fragmentTransaction.commit();
        }
    }

    private void removeIntent() {
        getIntent().removeExtra(BaseStringKey.FLAG);
        getIntent().removeExtra(BaseStringKey.PURCHASE);
        getIntent().removeExtra(BaseStringKey.ID);
    }

    private void setColorFooder(int curPosition) {
        switch (curPosition) {
            case 0:
                tvPostPurchase.setTextColor(Color.parseColor("#f98900"));
//                imgPostPurchase.setImageResource(R.drawable.add_icon_new);
                tvHomePurchase.setTextColor(Color.parseColor("#000000"));
                imgHomePurchase.setImageResource(R.drawable.home_icon2);
                tvUserPurchase.setTextColor(Color.parseColor("#000000"));
                imgUserPurchase.setImageResource(R.drawable.user_male);
                break;
            case 1:
                tvPostPurchase.setTextColor(Color.parseColor("#000000"));
                imgPostPurchase.setImageResource(R.drawable.them_icon);
                tvHomePurchase.setTextColor(Color.parseColor("#f98900"));
//                imgHomePurchase.setImageResource(R.drawable.home_icon2_new);
                tvUserPurchase.setTextColor(Color.parseColor("#000000"));
                imgUserPurchase.setImageResource(R.drawable.user_male);
                break;
            case 2:
                tvPostPurchase.setTextColor(Color.parseColor("#000000"));
                imgPostPurchase.setImageResource(R.drawable.them_icon);
                tvHomePurchase.setTextColor(Color.parseColor("#000000"));
                imgHomePurchase.setImageResource(R.drawable.home_icon2);
                tvUserPurchase.setTextColor(Color.parseColor("#f98900"));
//                imgUserPurchase.setImageResource(R.drawable.user_icon_new);
                break;
            default:
                tvPostPurchase.setTextColor(Color.parseColor("#ffffff"));
                imgPostPurchase.setImageResource(R.drawable.them_icon);
                tvHomePurchase.setTextColor(Color.parseColor("#f98900"));
//                imgHomePurchase.setImageResource(R.drawable.home_icon2_new);
                tvUserPurchase.setTextColor(Color.parseColor("#ffffff"));
                imgUserPurchase.setImageResource(R.drawable.user_male);
        }
    }
}
