package com.example.anhtuan.quanlyphongtro.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.purchase.PurchaseActivity;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.login.presenter.LoginPresenterImp;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, IContract.IViewLogin {

    @BindView(R.id.imgb_back)
    ImageButton imgbBack;
    @BindView(R.id.edt_taikhoan)
    EditText edtTaikhoan;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_confirmation_password)
    EditText edtConfirmationPassword;
    @BindView(R.id.btn_dangki)
    Button btnDangki;

    SharedPreferences sharedPreferences;
    LoginPresenterImp loginPresenterImp;
    IApi iApi;
    @BindView(R.id.pb_waitsignup)
    ProgressBar pbWaitsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = getSharedPreferences("OBJECT_USER", Context.MODE_PRIVATE);
        loginPresenterImp = new LoginPresenterImp(this);

        imgbBack.setOnClickListener(this);
        btnDangki.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgbBack) {
            onBackPressed();
        } else if (v == btnDangki) {
            pbWaitsignup.setVisibility(View.VISIBLE);
            if (edtTaikhoan.getText().length() != 0 || edtPassword.getText().length() != 0 || edtConfirmationPassword.getText().length() != 0) {
                AuthRequest authRequest = new AuthRequest(edtTaikhoan.getText().toString(),
                        edtPassword.getText().toString(), edtConfirmationPassword.getText().toString());
                loginPresenterImp.getTokenSignUp(iApi, authRequest, sharedPreferences);
            } else {
                onFailure();
            }
        }
    }

    @Override
    public void onSuccess() {
        pbWaitsignup.setVisibility(View.GONE);
        Intent intent = new Intent(SignUpActivity.this, PurchaseActivity.class);
        startActivity(intent);
        finish();
        Log.d("SIGNUP", "SUCCESS");
    }

    @Override
    public void onFailure() {
        pbWaitsignup.setVisibility(View.GONE);
        Toast.makeText(this, "SIGN UP FAILURE", Toast.LENGTH_SHORT).show();
        Log.d("SIGNUP", "FAILURE");
    }

    @Override
    public void checkUserSuccess() {

    }

    @Override
    public void checkUserFailure() {

    }
}
