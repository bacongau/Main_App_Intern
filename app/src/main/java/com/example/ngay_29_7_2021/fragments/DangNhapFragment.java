package com.example.ngay_29_7_2021.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngay_29_7_2021.API.ApiService;
import com.example.ngay_29_7_2021.MainActitivy;
import com.example.ngay_29_7_2021.R;
import com.example.ngay_29_7_2021.model.CustomerRealm;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangNhapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangNhapFragment extends Fragment {
    EditText edt_tentk, edt_mk;
    Button btn_dangnhap, btn_dangnhap_realm;
    TextView tv_dangky;
    View view;
    CheckBox cb_remember, cb_remember_realm;

    SharedPreferences sharedPreferences;

    MainActitivy mMainActivity;
    FragmentManager fragmentManager;

    final int[] checkFirstTime = {1};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DangNhapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangNhapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangNhapFragment newInstance(String param1, String param2) {
        DangNhapFragment fragment = new DangNhapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dang_nhap, container, false);

        mMainActivity = (MainActitivy) getActivity();
        fragmentManager = mMainActivity.getSupportFragmentManager();
        anhxa();

        layDuLieuDangNhapTuSharedPreferences();
        layDuLieuDangNhapTuRealmDB();

        clickDangNhap();
        clickDangky();
        clickDangNhapRealm();

        return view;
    }

    private void clickDangNhapRealm() {
        btn_dangnhap_realm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentk = edt_tentk.getText().toString().trim();
                String mk = edt_mk.getText().toString().trim();
                String str = tentk + ":" + mk;
                if (!CheckInput(tentk, mk)) {
                    return;
                }

                String str2 = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    str2 = Base64.getEncoder().encodeToString(str.getBytes());
                }
                String encodedString = "Basic " + str2;

                String strRequestBody = "body";
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);

                // rxjava
                Observable<ResponseBody> observable = ApiService.apiService.dangNhapCustomer(encodedString, requestBody);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> result)
                        .subscribe(
                                responseBody -> {
                                    Toast.makeText(mMainActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    fragmentManager.beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                            .replace(R.id.fragment_container, mMainActivity.homeFragment)
                                            .addToBackStack(null)
                                            .commit();
                                    luuDangNhapVaoRealmDatabase(tentk, mk);
                                },
                                throwable -> {
                                    Log.v("myLog", "err " + throwable.getLocalizedMessage());
                                    Toast.makeText(mMainActivity, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                                }
                        );
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

                // rxjava
                Observable<ResponseBody> observable = ApiService.apiService.dangNhapCustomer(encodedString, requestBody);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(result -> result)
                        .subscribe(
                                responseBody -> {
                                    Toast.makeText(mMainActivity, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    fragmentManager.beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                            .replace(R.id.fragment_container, mMainActivity.homeFragment)
                                            .addToBackStack(null)
                                            .commit();
                                    luuDangNhapVaoSharedPreferences(tentk, mk);
                                },
                                throwable -> {
                                    Log.v("myLog", "err " + throwable.getLocalizedMessage());
                                    Toast.makeText(mMainActivity, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                                }
                        );
            }
        });
    }

    private void luuDangNhapVaoRealmDatabase(String tentk, String mk) {
        if (cb_remember_realm.isChecked()) {
            // realm database

            CustomerRealm customerRealm = new CustomerRealm();

            Number id = mMainActivity.realm.where(CustomerRealm.class).max("id");

            int nextId;
            if (id == null) {
                nextId = 1;
            } else {
                nextId = id.intValue() + 1;
            }

            customerRealm.setId(nextId);
            customerRealm.setTentk(tentk);
            customerRealm.setMatkhau(mk);
            customerRealm.setGhinho(true);

            mMainActivity.realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgrealm) {
                    bgrealm.copyToRealm(customerRealm);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Toast.makeText(mMainActivity, "Lưu vào realm thành công", Toast.LENGTH_SHORT).show();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Toast.makeText(mMainActivity, "Lưu vào realm thất bại", Toast.LENGTH_SHORT).show();
                    Log.d("err", "" + error);
                }
            });
        } else {
            // xoa data
            mMainActivity.realm.beginTransaction();
            mMainActivity.realm.deleteAll();
            mMainActivity.realm.commitTransaction();
        }
    }

    private void luuDangNhapVaoSharedPreferences(String tentk, String mk) {
        if (cb_remember.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Login_tentaikhoan", tentk);
            editor.putString("Login_matkhau", mk);
            editor.putBoolean("Login_nhodangnhap", true);
            editor.apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("Login_tentaikhoan");
            editor.remove("Login_matkhau");
            editor.remove("Login_nhodangnhap");
            editor.apply();
        }
    }

    private void layDuLieuDangNhapTuRealmDB() {
        if (!cb_remember.isChecked()) {
            ArrayList<CustomerRealm> arrayList = new ArrayList<>();
            RealmResults<CustomerRealm> userRealmRealmResults = mMainActivity.realm.where(CustomerRealm.class).findAll();
            for (CustomerRealm customerRealm : userRealmRealmResults) {
                arrayList.add(customerRealm);
            }
            if (arrayList.size() > 0) {
                if (!cb_remember.isChecked()) {
                    cb_remember_realm.setChecked(arrayList.get(0).isGhinho());
                    edt_tentk.setText(arrayList.get(0).getTentk());
                    edt_mk.setText(arrayList.get(0).getMatkhau());


                    // Vào màn hình Home nếu đã lưu thông tin đăng nhập
                    String tentaikhoan = edt_tentk.getText().toString();
                    String matkhau = edt_mk.getText().toString();
                    if (!tentaikhoan.equals("") && !matkhau.equals("") && checkFirstTime[0] == 1) {
                        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                fragmentManager.beginTransaction()
                                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                        .replace(R.id.fragment_container, mMainActivity.homeFragment)
                                        .addToBackStack(null)
                                        .commit();
                                checkFirstTime[0] = 2;
                            }
                        };

                        countDownTimer.start();
                    }
                }
            } else {
                edt_tentk.setText("");
                edt_mk.setText("");
                cb_remember_realm.setChecked(false);
            }
        }
    }

    private void layDuLieuDangNhapTuSharedPreferences() {
        // Lấy thông tin đăng nhập và gán lên các edittext.
        edt_tentk.setText(sharedPreferences.getString("Login_tentaikhoan", ""));
        edt_mk.setText(sharedPreferences.getString("Login_matkhau", ""));
        cb_remember.setChecked(sharedPreferences.getBoolean("Login_nhodangnhap", false));

        // Vào màn hình Home nếu đã lưu thông tin đăng nhập
        String tentaikhoan = edt_tentk.getText().toString();
        String matkhau = edt_mk.getText().toString();
        if (!tentaikhoan.equals("") && !matkhau.equals("") && checkFirstTime[0] == 1) {
            CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.fragment_container, mMainActivity.homeFragment)
                            .addToBackStack(null)
                            .commit();
                    checkFirstTime[0] = 2;
                }
            };

            countDownTimer.start();
        }

    }

    private void taoEncryptChoSharedPreferences() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            sharedPreferences = EncryptedSharedPreferences.create(
                    "DangNhap_Encrypt_sharedPreferences",
                    masterKeyAlias,
                    mMainActivity,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean CheckInput(String tentk, String mk) {
        if (tentk.isEmpty() || mk.isEmpty() || mk.length() < 6) {
            Toast.makeText(mMainActivity, "Bạn chưa nhập thông tin" + "\nMật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clickDangky() {
        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, mMainActivity.dangKyFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void anhxa() {
        edt_tentk = view.findViewById(R.id.edt_dn_tentk);
        edt_mk = view.findViewById(R.id.edt_dn_mk);
        btn_dangnhap = view.findViewById(R.id.btn_dangnhap);
        btn_dangnhap_realm = view.findViewById(R.id.btn_dangnhap_realm);
        tv_dangky = view.findViewById(R.id.tv_dangky);
        cb_remember = view.findViewById(R.id.checkBox_remember_dangnhap);
        cb_remember_realm = view.findViewById(R.id.checkBox_remember_dangnhap_realm);

        // tạo Encrypt cho SharedPreferences
        taoEncryptChoSharedPreferences();
    }
}