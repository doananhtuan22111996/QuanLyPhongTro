package com.example.anhtuan.quanlyphongtro.postpurchase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PostPurchaseFragment extends Fragment implements View.OnClickListener, IContract.IViewPurchase.IViewPostPurchase {

    @BindView(R.id.img_camera1_postpurchase)
    ImageView imgCamera1Postpurchase;
    @BindView(R.id.img_camera2_postpurchase)
    ImageView imgCamera2Postpurchase;
    @BindView(R.id.img_camera3_postpurchase)
    ImageView imgCamera3Postpurchase;
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
    @BindView(R.id.btn_save_postpurchase)
    Button btnSavePostpurchase;
    @BindView(R.id.btn_postpurchase)
    Button btnPostpurchase;
    @BindView(R.id.lnl_insert_image)
    LinearLayout lnlInsertImage;

    private static final int PICK_FROM_GALLERY_1 = 1;
    private static final int PICK_FROM_GALLERY_2 = 2;
    private static final int PICK_FROM_GALLERY_3 = 3;
    SharedPreferences sharedPreferences;
    PostPurchasePresenter postPurchasePresenter;
    IApi iApi;
    Bundle bundle;
    Uri uri1, uri2, uri3;
    String api_token;

    public static PostPurchaseFragment newInstance() {
        return new PostPurchaseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_purchase, container, false);
        ButterKnife.bind(this, view);

        Retrofit retrofit = MainApplication.getRetrofit();
        iApi = retrofit.create(IApi.class);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(BaseStringKey.USER_FILE, Context.MODE_PRIVATE);
        bundle = getActivity().getIntent().getExtras();
        postPurchasePresenter = new PostPurchasePresenter(this);

        btnPostpurchase.setOnClickListener(this);
        btnSavePostpurchase.setOnClickListener(this);
        imgCamera1Postpurchase.setOnClickListener(this);
        imgCamera2Postpurchase.setOnClickListener(this);
        imgCamera3Postpurchase.setOnClickListener(this);

        getToken();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == imgCamera1Postpurchase) {
            try {
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY_1);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                    File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String strImage = imageFile.getPath();
                    Uri data = Uri.parse(strImage);
                    galleryIntent.setDataAndType(data, "image/*");
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY_1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == imgCamera2Postpurchase) {
            try {
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY_2);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                    File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String strImage = imageFile.getPath();
                    Uri data = Uri.parse(strImage);
                    galleryIntent.setDataAndType(data, "image/*");
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY_2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == imgCamera3Postpurchase) {
            try {
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY_3);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                    File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String strImage = imageFile.getPath();
                    Uri data = Uri.parse(strImage);
                    galleryIntent.setDataAndType(data, "image/*");
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY_3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY_1) {
            if (data != null) {
                uri1 = data.getData();
                try {
                    InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uri1);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                    imgCamera1Postpurchase.setImageBitmap(scaled);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PICK_FROM_GALLERY_2) {
            if (data != null) {
                uri2 = data.getData();
                try {
                    InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uri2);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                    imgCamera2Postpurchase.setImageBitmap(scaled);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == PICK_FROM_GALLERY_3) {
            if (data != null) {
                uri3 = data.getData();
                try {
                    InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uri3);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                    imgCamera3Postpurchase.setImageBitmap(scaled);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void callTokenSuccess(final String token) {
        this.api_token = token;
        getFrag();
        btnPostpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPurchase(token);
            }
        });
    }

    @Override
    public void callTokenFailure() {
        Toast.makeText(getActivity(), "GET TOKEN FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callFlagSuccess(final Purchase purchase, final int id, int flag) {
        if (flag == 1) {
            btnSavePostpurchase.setVisibility(View.VISIBLE);
            btnPostpurchase.setVisibility(View.GONE);
            lnlInsertImage.setVisibility(View.GONE);
            showDetail(purchase);
        }
        btnSavePostpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePurchase(id, api_token);
            }
        });
    }

    @Override
    public void callFlagFailure() {
        lnlInsertImage.setVisibility(View.VISIBLE);
        btnSavePostpurchase.setVisibility(View.GONE);
        btnPostpurchase.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostSuccess() {
        Toast.makeText(getActivity(), "POST SUCCESS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostFailure() {
        Toast.makeText(getActivity(), "POST FAILURE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(getActivity(), "UPDATE SUCCESS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateFailure() {
        Toast.makeText(getActivity(), "UPDATE FAILURE", Toast.LENGTH_SHORT).show();
    }

    private void getToken() {
        postPurchasePresenter.getTokenSharePreference(sharedPreferences);
    }

    private void getFrag() {
        postPurchasePresenter.getFlag(bundle);
    }

    private void showDetail(Purchase purchase) {
        edtTitlePostpurchase.setText(purchase.getTitle());
        edtPricePostpurchase.setText(String.valueOf(purchase.getPrice()));
        edtAcreagePostpurchase.setText(String.valueOf(purchase.getAcreage()));
        edtPhonePostpurchase.setText(String.valueOf(purchase.getPhone()));
        edtAddressPostpurchase.setText(purchase.getAddress());
        if (purchase.getDescription() != null) {
            edtDecriptionPostpurchase.setText(purchase.getDescription());
        }
    }

    private void postPurchase(String token) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(edtTitlePostpurchase.getText().toString(), Float.parseFloat(edtPricePostpurchase.getText().toString()),
                Float.parseFloat(edtAcreagePostpurchase.getText().toString()), edtPhonePostpurchase.getText().toString(),
                edtAddressPostpurchase.getText().toString(), edtDecriptionPostpurchase.getText().toString());
        postPurchasePresenter.postPurchase(iApi, purchaseRequest, token);
    }

    private void updatePurchase(int id, String token) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(edtTitlePostpurchase.getText().toString(), Float.parseFloat(edtPricePostpurchase.getText().toString()),
                Float.parseFloat(edtAcreagePostpurchase.getText().toString()), edtPhonePostpurchase.getText().toString(),
                edtAddressPostpurchase.getText().toString(), edtDecriptionPostpurchase.getText().toString());
        postPurchasePresenter.updatePurchase(iApi, purchaseRequest, id, token);
    }
}
