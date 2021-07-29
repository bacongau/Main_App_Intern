package com.example.ngay_29_7_2021.API;

import com.example.ngay_29_7_2021.model.Customer;
import com.example.ngay_29_7_2021.model.MessDangNhap2;
import com.example.ngay_29_7_2021.model.MessageDangKy;
import com.example.ngay_29_7_2021.model.MessageDangNhap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    // link api dang nhap: https://63d759a008b0.ngrok.io/api/auths/customer/login
    // link api dang ky: https://63d759a008b0.ngrok.io/api/auths/customers/register

    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd  HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://4e06c5bd3878.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiService.class);


    @POST("api/auths/customer/login")
    Call<MessageDangNhap> dangNhapCustomer(@Header("Authorization") String encodedString,@Body RequestBody requestBody);

//    @POST("api/auths/customer/login")
//    Single<ResponseBody> dangNhapCustomer(@Body RequestBody requestBody);

    @POST("api/auths/customers/register")
    Call<MessageDangKy> dangKyCustomer(@Header("Authorization") String encodedString, @Body Customer customer);

}