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
import com.example.ngay_29_7_2021.model.MessageDangNhap;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActitivy extends AppCompatActivity {
    EditText edt_tentk, edt_mk;
    Button btn_dangnhap;
    TextView tv_dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_actitivy);

        anhxa();

        clickDangNhap();

        clickDangky();
    }

    private void clickDangky() {
        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActitivy.this, DangKyActivity.class));
            }
        });

    }

    private void clickDangNhap() {
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String tentk = edt_tentk.getText().toString().trim();
                String mk = edt_mk.getText().toString().trim();
                String str = tentk + ":" + mk;
                if (!CheckInput(tentk, mk)) {
                    return;
                }
                String str2 = Base64.getEncoder().encodeToString(str.getBytes());
                String encodedString = "Basic " + str2;

                String strRequestBody = "body";
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);

                Observable<ResponseBody> observable = ApiService.apiService.dangNhapCustomer(encodedString, requestBody);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> result)
                        .subscribe(
                                responseBody -> {
                                    Toast.makeText(DangNhapActitivy.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(DangNhapActitivy.this, HomeActivity.class));
                                },
                                throwable -> {
                                    Log.v("myLog", "err " + throwable.getLocalizedMessage());
                                    Toast.makeText(DangNhapActitivy.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                                }
                        );
            }
        });
    }

    private boolean CheckInput(String tentk, String mk) {
        if (tentk.isEmpty() || mk.isEmpty() || mk.length() < 6) {
            Toast.makeText(this, "Bạn chưa nhập thông tin" + "\nMật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void anhxa() {
        edt_tentk = findViewById(R.id.edt_dn_tentk);
        edt_mk = findViewById(R.id.edt_dn_mk);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
        tv_dangky = findViewById(R.id.tv_dangky);
    }
}