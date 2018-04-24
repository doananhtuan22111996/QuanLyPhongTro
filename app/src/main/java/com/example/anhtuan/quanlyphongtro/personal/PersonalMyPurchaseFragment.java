package com.example.anhtuan.quanlyphongtro.personal;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.DetailPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.login.LogInActivity;
import com.example.anhtuan.quanlyphongtro.model.Purchase;
import com.example.anhtuan.quanlyphongtro.personal.adapter.PersonalMyPurchaseRecyclerViewAdapter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PersonalMyPurchaseFragment extends Fragment implements IContract.IViewPurchase.IViewPersonalMyPurchase, View.OnClickListener {

    @BindView(R.id.pb_waitmypurchase)
    ProgressBar pbWaitmypurchase;
    @BindView(R.id.rcv_items_personalmypurchase)
    RecyclerView rcvItemsPersonalmypurchase;
    @BindView(R.id.img_logout_personalmypurchase)
    ImageView imgLogoutPersonalmypurchase;

    SharedPreferences sharedPreferences;
    PersonalMyPurchasePresenter personalMyPurchasePresenter;
    IApi iApi;
    String token;

    public static PersonalMyPurchaseFragment newInstance() {
        return new PersonalMyPurchaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_mypurchase, container, false);
        ButterKnife.bind(this, view);

        pbWaitmypurchase.setVisibility(View.VISIBLE);
        imgLogoutPersonalmypurchase.setOnClickListener(this);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        personalMyPurchasePresenter = new PersonalMyPurchasePresenter(this);
        rcvItemsPersonalmypurchase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        getToken();
        return view;
    }

    @Override
    public void callTokenSuccess(String token) {
        this.token = token;
        getMyPurchase(token);
    }

    @Override
    public void callTokenFailure() {
        pbWaitmypurchase.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "GET TOKEN FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callMyPurchaseSuccess(List<Purchase> purchasesList) {
        pbWaitmypurchase.setVisibility(View.GONE);
        showMyPurchase(purchasesList);
    }

    @Override
    public void callMyPurchaseFailure() {
        pbWaitmypurchase.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "GET PURCHASE FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess() {
        getMyPurchase(token);
    }

    @Override
    public void ondDeleteFailure() {
        pbWaitmypurchase.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "DELETE FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == imgLogoutPersonalmypurchase) {
            SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
            Intent intent = new Intent(getActivity(), LogInActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void getToken() {
        personalMyPurchasePresenter.getTokenSharePreference(sharedPreferences);
    }

    private void getMyPurchase(String token) {
        personalMyPurchasePresenter.getMyPurchase(iApi, token);
    }

    private void showMyPurchase(final List<Purchase> purchaseList) {
        final PersonalMyPurchaseRecyclerViewAdapter personalMyPurchaseRecyclerViewAdapter = new PersonalMyPurchaseRecyclerViewAdapter(getActivity(), purchaseList);
        rcvItemsPersonalmypurchase.setAdapter(personalMyPurchaseRecyclerViewAdapter);
        personalMyPurchaseRecyclerViewAdapter.setiOnClickItemPurchaseListener(new IContract.IOnClickItemPurchaseListener() {
            @Override
            public void onClickItemPurchase(int position) {
                Intent intent = new Intent(getActivity(), DetailPurchaseActivity.class);
                intent.putExtra(BaseStringKey.PURCHASE, purchaseList.get(position));
                startActivity(intent);
            }

            @Override
            public void onClickItemDeletePurchase(int postition) {
                int id = purchaseList.get(postition).getId();
                personalMyPurchasePresenter.deleteMyPurchase(iApi, id, token);
                purchaseList.clear();
                pbWaitmypurchase.setVisibility(View.VISIBLE);
            }
        });
        personalMyPurchaseRecyclerViewAdapter.notifyDataSetChanged();
    }

}
