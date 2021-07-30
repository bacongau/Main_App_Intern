package com.example.ngay_29_7_2021.API;

import com.example.ngay_29_7_2021.model.Customer;
import com.example.ngay_29_7_2021.model.MessDangNhap2;
import com.example.ngay_29_7_2021.model.MessageDangKy;
import com.example.ngay_29_7_2021.model.MessageDangNhap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auths/customer/login")
    Observable<MessageDangNhap> dangNhapCustomer(@Header("Authorization") String encodedString, @Body RequestBody requestBody);

//    @POST("api/auths/customer/login")
//    Single<ResponseBody> dangNhapCustomer(@Body RequestBody requestBody);

    @POST("api/auths/customers/register")
    Observable<MessageDangKy> dangKyCustomer(@Header("Authorization") String encodedString, @Body Customer customer);

}
