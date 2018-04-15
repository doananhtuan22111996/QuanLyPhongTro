package com.example.anhtuan.quanlyphongtro.api;

import com.example.anhtuan.quanlyphongtro.base.BaseResponse;
import com.example.anhtuan.quanlyphongtro.model.request.PurchaseRequest;
import com.example.anhtuan.quanlyphongtro.model.request.AuthRequest;
import com.example.anhtuan.quanlyphongtro.model.response.PurchaseResponse;
import com.example.anhtuan.quanlyphongtro.model.response.UserResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IApi {
    String BASE_URL = "https://matas-app.herokuapp.com/api/v1/";

    @POST("auth/sign_in")
        //Sign in
    Call<UserResponse> getUserTokenSignIn(@Body AuthRequest authRequest);

    @POST("auth/sign_up")
        //Sign up
    Call<UserResponse> getUserTokenSignUp(@Body AuthRequest authRequest);

    @GET("purchase/my_purchases")
        //Danh sach token da dang
    Call<PurchaseResponse> getMyPurchase(@Header("Http-Auth-Token") String api_token);

    @GET("purchase")
        //Danh sach tro
    Call<PurchaseResponse> getPurchase(@Header("Http-Auth-Token") String api_token);

    @POST("purchase")
        //Dang tro
    Call<BaseResponse> postPurchase(@Header("Http-Auth-Token") String api_token,
                                    @Body RequestBody requestBody);

    @PUT("purchase/{id}")
        //Update tro token
    Call<BaseResponse> putPurchase(@Header("Http-Auth-Token") String api_token, @Body PurchaseRequest purchaseRequest, @Path("id") int id);

    @DELETE("purchase/{id}")
        //Delete purchase
    Call<BaseResponse> deletePurchase(@Header("Http-Auth-Token") String api_token, @Path("id") int id);
}
