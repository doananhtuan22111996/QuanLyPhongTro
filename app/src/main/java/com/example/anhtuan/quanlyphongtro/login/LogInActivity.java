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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anhtuan.quanlyphongtro.R;
import com.example.anhtuan.quanlyphongtro.main.MainActivity;
import com.example.anhtuan.quanlyphongtro.api.IApi;
import com.example.anhtuan.quanlyphongtro.base.BaseStringKey;
import com.example.anhtuan.quanlyphongtro.base.MainApplication;
import com.example.anhtuan.quanlyphongtro.contract.IContract;
import com.example.anhtuan.quanlyphongtro.login.presenter.LoginPresenter;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class LogInActivity extends AppCompatActivity implements IContract.IViewLogin, View.OnClickListener {

    @BindView(R.id.btn_dangnhap)
    Button btnDangNhap;
    @BindView(R.id.edt_taikhoan)
    EditText edtTaikhoan;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_dangki)
    Button btnDangki;

    LoginPresenter loginPresenter;
    SharedPreferences sharedPreferences;
    IApi iApi;
    @BindView(R.id.pb_waitlogin)
    ProgressBar pbWaitlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        loginPresenter = new LoginPresenter(this);

        btnDangNhap.setOnClickListener(this);
        btnDangki.setOnClickListener(this);

    }

    @Override
    public void onSuccess() {
        Log.d("SIGNIN", "SUCCESS");
        pbWaitlogin.setVisibility(View.GONE);
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure() {
        pbWaitlogin.setVisibility(View.GONE);
        Toast.makeText(this, "LOGIN FAILURE", Toast.LENGTH_SHORT).show();
        Log.d("SIGNIN", "FAILURE");
    }

    @Override
    public void checkUserSuccess() {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        Log.d("SIGNIN", "SUCCESS");
    }

    @Override
    public void checkUserFailure() {
        Log.d("SIGNIN", "FAILURE");
    }

    @Override
    public void onClick(View v) {
        if (v == btnDangNhap) {
            pbWaitlogin.setVisibility(View.VISIBLE);
            if (edtTaikhoan.getText().length() != 0 || edtPassword.getText().length() != 0) {
                AuthRequest authRequest = new AuthRequest(edtTaikhoan.getText().toString(), edtPassword.getText().toString());
                loginPresenter.getTokenSignIn(iApi, authRequest, sharedPreferences);
            } else {
                onFailure();
            }
        }
        if (v == btnDangki) {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

}
