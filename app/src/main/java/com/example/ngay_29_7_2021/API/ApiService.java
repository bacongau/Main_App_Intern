package com.example.ngay_29_7_2021.API;

import com.example.ngay_29_7_2021.model.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ApiService.class);


    @POST("api/auths/customer/login")
    Observable<ResponseBody> dangNhapCustomer(@Header("Authorization") String encodedString,@Body RequestBody requestBody);

//    @POST("api/auths/customer/login")
//    Single<ResponseBody> dangNhapCustomer(@Body RequestBody requestBody);

    @POST("api/auths/customers/register")
    Observable<ResponseBody> dangKyCustomer(@Header("Authorization") String encodedString, @Body Customer customer);

}
