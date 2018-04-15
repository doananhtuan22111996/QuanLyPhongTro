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
import android.util.Log;
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
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class PostPurchaseFragment extends Fragment implements View.OnClickListener, IContract.IViewPurchase {

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
    PostPurchaseImp postPurchaseImp;
    IApi iApi;
    Bundle bundle;
    Uri uri1, uri2, uri3;

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
        postPurchaseImp = new PostPurchaseImp(this);

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
        if (v == btnPostpurchase) {
            postPurchase();
        } else if (v == btnSavePostpurchase) {
            updatePurchase();
        } else if (v == imgCamera1Postpurchase) {
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
            uri1 = data.getData();
            try {
                InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uri1);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                imgCamera1Postpurchase.setImageBitmap(scaled);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_FROM_GALLERY_2) {
            uri2 = data.getData();
            try {
                InputStream inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(uri2);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                imgCamera2Postpurchase.setImageBitmap(scaled);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_FROM_GALLERY_3) {
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

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(), "POST SUCCCESS", Toast.LENGTH_SHORT).show();
        Log.d("POST", "SUCCESS");
    }

    @Override
    public void onFailure() {
        Log.d("POST", "FAILURE");
    }

    @Override
    public void getTokenSuccess() {
        getFrag();
        Log.d("TOKEN", "SUCCESS");
    }

    @Override
    public void getTokenFailure() {
        Log.d("TOKEN", "FAILURE");
    }

    @Override
    public void getFlagSuccess() {
        if (postPurchaseImp.getFlag() == 1) {
            btnSavePostpurchase.setVisibility(View.VISIBLE);
            btnPostpurchase.setVisibility(View.GONE);
            lnlInsertImage.setVisibility(View.GONE);
            showDetail();
        }
        Log.d("FLAG", "SUCCESS");
    }

    @Override
    public void getFlagFailure() {
        lnlInsertImage.setVisibility(View.VISIBLE);
        btnSavePostpurchase.setVisibility(View.GONE);
        btnPostpurchase.setVisibility(View.VISIBLE);
        Log.d("FLAG", "FAILURE");
    }

    private void getToken() {
        postPurchaseImp.getTokenSharePreference(sharedPreferences);
    }

    private void getFrag() {
        postPurchaseImp.getFlag(bundle);
    }

    private void showDetail() {
        edtTitlePostpurchase.setText(postPurchaseImp.getPurchase().getTitle());
        edtPricePostpurchase.setText(String.valueOf(postPurchaseImp.getPurchase().getPrice()));
        edtAcreagePostpurchase.setText(String.valueOf(postPurchaseImp.getPurchase().getAcreage()));
        edtPhonePostpurchase.setText(String.valueOf(postPurchaseImp.getPurchase().getPhone()));
        edtAddressPostpurchase.setText(postPurchaseImp.getPurchase().getAddress());
        if (postPurchaseImp.getPurchase().getDescription() != null) {
            edtDecriptionPostpurchase.setText(postPurchaseImp.getPurchase().getDescription());
        }
    }

    private void postPurchase() {
        List<String> listImage = new ArrayList<>();
        //Truyền đây 1 list path của ảnh, string
        postPurchaseImp.postPurchase(iApi,
                edtTitlePostpurchase.getText().toString(),
                edtPricePostpurchase.getText().toString(),
                edtAcreagePostpurchase.getText().toString(),
                edtPhonePostpurchase.getText().toString(),
                edtAddressPostpurchase.getText().toString(),
                edtDecriptionPostpurchase.getText().toString(),
                listImage);
    }

    private void updatePurchase() {
        PurchaseRequest purchaseRequest = new PurchaseRequest(postPurchaseImp.getApi_token(),
                edtTitlePostpurchase.getText().toString(), Float.parseFloat(edtPricePostpurchase.getText().toString()),
                Float.parseFloat(edtAcreagePostpurchase.getText().toString()), edtPhonePostpurchase.getText().toString(),
                edtAddressPostpurchase.getText().toString(), edtDecriptionPostpurchase.getText().toString());
        postPurchaseImp.updatePurchase(iApi, purchaseRequest, postPurchaseImp.getId());
    }

}
