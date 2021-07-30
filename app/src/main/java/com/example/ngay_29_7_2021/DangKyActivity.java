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

import com.example.ngay_29_7_2021.API.ApiClient;
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
                startActivity(new Intent(DangKyActivity.this,DangNhapActitivy.class));
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

                String str = tentk + ":" + mk;
                String encodedString = Base64.getEncoder().encodeToString(str.getBytes());

                Customer customer = new Customer(hoten, diachi, sdt);

                // rxjava
                Observable<MessageDangKy> observable = ApiClient.getClient(DangKyActivity.this).dangKyCustomer(encodedString, customer);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> result)
                        .subscribe(
                                response -> {
                                    Log.v("myLog", "Call api thành công ");
                                    if (response.getCode() == 200) {
                                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        Log.v("myLog", "Đăng ký thành công ");
                                        startActivity(new Intent(DangKyActivity.this, DangNhapActitivy.class));
                                    } else if (response.getCode() == 409) {
                                        Toast.makeText(DangKyActivity.this, "Tài khoản đã có người dùng", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Log.v("myLog", "err " + throwable.getMessage());
                                    Toast.makeText(DangKyActivity.this, "Tài khoản đã có người dùng", Toast.LENGTH_SHORT).show();
                                }
                        );


            }
        });

    }

    private boolean checkInput(String tentk, String mk, String hoten, String diachi, String sdt) {
        if (tentk.isEmpty() || mk.isEmpty() || mk.length()<6 || hoten.isEmpty() || diachi.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Không được để trống dữ liệu" + "\nMật khẩu phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
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