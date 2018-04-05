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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.detailpurchase.DetailPurchaseActivity;
import com.example.anhtuan.quanlyphongtro.personal.adapter.PersonalMyPurchaseRecyclerViewAdapter;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PersonalMyPurchaseFragment extends Fragment implements IContract.IViewPurchase {

    @BindView(R.id.pb_waitmypurchase)
    ProgressBar pbWaitmypurchase;
    @BindView(R.id.rcv_items_personalmypurchase)
    RecyclerView rcvItemsPersonalmypurchase;

    SharedPreferences sharedPreferences;
    PersonalMyPurchaseImp personalMyPurchaseImp;
    IApi iApi;

    public static PersonalMyPurchaseFragment newInstance() {
        return new PersonalMyPurchaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_mypurchase, container, false);
        ButterKnife.bind(this, view);

        pbWaitmypurchase.setVisibility(View.VISIBLE);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        personalMyPurchaseImp = new PersonalMyPurchaseImp(this);
        rcvItemsPersonalmypurchase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));

        getToken();
        return view;
    }

    @Override
    public void onSuccess() {
        pbWaitmypurchase.setVisibility(View.GONE);
        showMyPurchase();
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
    public void getFlagSuccess() {

    }

    @Override
    public void getFlagFailure() {

    }

    private void getToken() {
        if (sharedPreferences != null) {
            personalMyPurchaseImp.getTokenSharePreference(sharedPreferences);
        }
    }

    private void getMyPurchase() {
        personalMyPurchaseImp.getMyPurchase(iApi);
    }

    private void showMyPurchase() {
        PersonalMyPurchaseRecyclerViewAdapter personalMyPurchaseRecyclerViewAdapter = new PersonalMyPurchaseRecyclerViewAdapter(getActivity(), personalMyPurchaseImp.getPurchaseList());
        rcvItemsPersonalmypurchase.setAdapter(personalMyPurchaseRecyclerViewAdapter);
        personalMyPurchaseRecyclerViewAdapter.setiOnClickItemPurchaseListener(new IContract.IOnClickItemPurchaseListener() {
            @Override
            public void onClickItemPurchase(int position) {
                Intent intent = new Intent(getActivity(), DetailPurchaseActivity.class);
                intent.putExtra(BaseStringKey.PURCHASE, personalMyPurchaseImp.getPurchaseList().get(position));
                startActivity(intent);
            }
        });
        personalMyPurchaseRecyclerViewAdapter.notifyDataSetChanged();
    }

}
