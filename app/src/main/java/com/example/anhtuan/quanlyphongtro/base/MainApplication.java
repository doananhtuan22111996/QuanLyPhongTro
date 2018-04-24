package com.example.anhtuan.quanlyphongtro.base;

import android.app.Application;

import com.example.anhtuan.quanlyphongtro.api.IApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainApplication extends Application {

    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(IApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
