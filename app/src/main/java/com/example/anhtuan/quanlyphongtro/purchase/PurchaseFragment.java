package com.example.anhtuan.quanlyphongtro.purchase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.DetailPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.purchase.adapter.PurchaseRecyclerViewAdaper;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PurchaseFragment extends Fragment implements IContract.IViewPurchase {

    @BindView(R.id.pb_waitpurchase)
    ProgressBar pbWaitpurchase;
    @BindView(R.id.rcv_list_purchase)
    RecyclerView rcvListPurchase;

    SharedPreferences sharedPreferences;
    PurchasePresenter purchasePresenter;
    PurchaseRecyclerViewAdaper purchaseRecyclerViewAdaper;
    IApi iApi;

    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        ButterKnife.bind(this, view);

        pbWaitpurchase.setVisibility(View.VISIBLE);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        purchasePresenter = new PurchasePresenter(this);
        rcvListPurchase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        getToken();

        return view;
    }

    @Override
    public void callPurchaseSuccess(List<Purchase> purchaseList) {
        pbWaitpurchase.setVisibility(View.GONE);
        showListPurchase(purchaseList);
    }

    @Override
    public void callPurchaseFailure() {
        pbWaitpurchase.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "GET PURCHASE FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callTokenSuccess(String token) {
        getPurchaseList(token);
    }

    @Override
    public void callTokenFailure() {
        pbWaitpurchase.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "GET TOKEN FAILURE", Toast.LENGTH_SHORT).show();
    }

    private void getToken() {
        purchasePresenter.getTokenSharePreference(sharedPreferences);
    }

    private void getPurchaseList(String api_token) {
        purchasePresenter.getPurchase(iApi, api_token);
    }

    private void showListPurchase(final List<Purchase> purchaseList) {
        purchaseRecyclerViewAdaper = new PurchaseRecyclerViewAdaper(getActivity(), purchaseList);
        rcvListPurchase.setAdapter(purchaseRecyclerViewAdaper);
        purchaseRecyclerViewAdaper.setiOnClickItemPurchaseListener(new IContract.IOnClickItemPurchaseListener() {
            @Override
            public void onClickItemPurchase(int position) {
                Intent intent = new Intent(getActivity(), DetailPurchaseActivity.class);
                intent.putExtra(BaseStringKey.PURCHASE, purchaseList.get(position));
                startActivity(intent);
            }

            @Override
            public void onClickItemDeletePurchase(int postition) {

            }
        });
        purchaseRecyclerViewAdaper.notifyDataSetChanged();
    }


}
