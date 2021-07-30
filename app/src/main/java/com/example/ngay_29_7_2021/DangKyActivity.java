package com.example.ngay_29_7_2021;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngay_29_7_2021.API.ApiService;
import com.example.ngay_29_7_2021.model.Customer;
import com.example.ngay_29_7_2021.model.MessageDangKy;
import com.example.ngay_29_7_2021.model.MessageDangNhap;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {
    EditText edt_tentk, edt_mk, edt_hoten, edt_diachi, edt_sdt;
    Button btn_dangky;
    TextView tv_dangnhapngay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();

        clickDangKy();

        clickDangNhap();

    }

    private void clickDangNhap() {
        tv_dangnhapngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKyActivity.this, DangNhapActitivy.class));
            }
        });
    }

    private void clickDangKy() {
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String tentk = edt_tentk.getText().toString().trim();
                String mk = edt_mk.getText().toString().trim();
                String hoten = edt_hoten.getText().toString().trim();
                String diachi = edt_diachi.getText().toString().trim();
                String sdt = edt_sdt.getText().toString().trim();

                if (!checkInput(tentk, mk, hoten, diachi, sdt)) {
                    return;
                }

                String str = "Basic " + tentk + ":" + mk;
                String str2 = Base64.getEncoder().encodeToString(str.getBytes());
                String encodedString = "Basic " + str2;

                Customer customer = new Customer(hoten, diachi, sdt);

                // rxjava
                Observable<ResponseBody> observable = ApiService.apiService.dangKyCustomer(encodedString, customer);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> result)
                        .subscribe(
                                responseBody -> {
                                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(DangKyActivity.this, DangNhapActitivy.class));
                                },
                                throwable -> {
                                    Log.v("myLog", "err " + throwable.getLocalizedMessage());
                                    String error = throwable.getLocalizedMessage().toLowerCase().trim();
                                    if (error.equals("HTTP 409".toLowerCase())) {
                                        Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DangKyActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

//                ApiService.apiService.dangKyCustomer(encodedString, customer).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Log.d("-------", "goi api thanhg cong");
//                        Log.d("-------", "" + response);
//                        Log.d("-------", "" + response.code());
//                        if (response.code() == 200){
//                            Toast.makeText(DangKyActivity.this, "Đăng Ký thành công", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(DangKyActivity.this, HomeActivity.class));
//                        }else if (response.code() == 409){
//                            Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.d("-------", t.getLocalizedMessage());
//                    }
//                });

            }
        });

    }

    private boolean checkInput(String tentk, String mk, String hoten, String diachi, String sdt) {
        if (tentk.isEmpty() || mk.isEmpty() || hoten.isEmpty() || diachi.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void anhxa() {
        edt_tentk = findViewById(R.id.edt_dk_tentk);
        edt_mk = findViewById(R.id.edt_dk_mk);
        edt_hoten = findViewById(R.id.edt_dk_hoten);
        edt_diachi = findViewById(R.id.edt_dk_diachi);
        edt_sdt = findViewById(R.id.edt_dk_sodt);
        btn_dangky = findViewById(R.id.btn_dangky);
        tv_dangnhapngay = findViewById(R.id.tv_dangnhap);
    }
}