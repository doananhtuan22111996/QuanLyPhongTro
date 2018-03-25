package com.example.anhtuan.quanlyphongtro.api;

import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;
import com.example.anhtuan.quanlyphongtro.model.request.UserRequest;
import com.example.anhtuan.quanlyphongtro.model.response.PurchaseResponse;
import com.example.anhtuan.quanlyphongtro.model.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IApi {
    String BASE_URL = "https://matas-app.herokuapp.com/api/v1/";

    @POST("auth/sign_in")
    Call<UserResponse> getUserTokenSignIn(@Body AuthRequest authRequest);

    @POST("auth/sign_up")
    Call<UserResponse> getUserTokenSignUp(@Body AuthRequest authRequest);

    @GET("purchase/my_purchases")
        //Danh sach token da dang
    Call<PurchaseResponse> getMyPurchase(@Body PurchaseRequest purchaseRequest);

    @GET("purchase")
        //Danh sach tro
    Call<PurchaseResponse> getPurchase(@Body PurchaseRequest purchaseRequest);

    @POST("purchase")
        //Dang tro
    Call<BaseResponse> postPurchase(@Body PurchaseRequest purchaseRequest);

    @DELETE("purchase/{id}")
        //Xoa tro token dang
    Call<BaseResponse> deletePurchase(@Body String api_token, @Path("id") int id);

    @PUT("purchase/{id}")
        //Update tro token
    Call<PurchaseResponse> putPurchase(@Body PurchaseRequest purchaseRequest, @Path("id") int id);

    @PUT("user/change_password")
        //Change password token
    Call<BaseResponse> putChangePassword(@Body UserRequest userRequest);

    @PUT("user")
        //Get data user
    Call<UserResponse> putUser(@Body UserRequest userRequest);
}
