package com.example.ngay_29_7_2021.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngay_29_7_2021.API.ApiService;
import com.example.ngay_29_7_2021.MainActitivy;
import com.example.ngay_29_7_2021.R;
import com.example.ngay_29_7_2021.model.Customer;

import java.util.Base64;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangKyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangKyFragment extends Fragment {
    EditText edt_tentk, edt_mk, edt_hoten, edt_diachi, edt_sdt;
    Button btn_dangky;
    TextView tv_dangnhapngay;
    View view;

    MainActitivy mMainActivity;
    FragmentManager fragmentManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DangKyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangKyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DangKyFragment newInstance(String param1, String param2) {
        DangKyFragment fragment = new DangKyFragment();
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
        view = inflater.inflate(R.layout.fragment_dang_ky, container, false);

        mMainActivity = (MainActitivy) getActivity();
        fragmentManager = mMainActivity.getSupportFragmentManager();
        anhxa();
        clickDangKy();
        clickDangNhap();

        return view;
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
                                    Toast.makeText(mMainActivity, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    fragmentManager.beginTransaction()
                                            .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                                            .replace(R.id.fragment_container,mMainActivity.dangNhapFragment)
                                            .addToBackStack(null)
                                            .commit();
                                },
                                throwable -> {
                                    Log.v("myLog", "err " + throwable.getLocalizedMessage());
                                    String error = throwable.getLocalizedMessage().toLowerCase().trim();
                                    if (error.equals("HTTP 409".toLowerCase())) {
                                        Toast.makeText(mMainActivity, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mMainActivity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
            }
        });
    }

    private void clickDangNhap() {
        tv_dangnhapngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.fragment_container,mMainActivity.dangNhapFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private boolean checkInput(String tentk, String mk, String hoten, String diachi, String sdt) {
        if (tentk.isEmpty() || mk.isEmpty() || hoten.isEmpty() || diachi.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(mMainActivity, "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void anhxa() {
        edt_tentk = view.findViewById(R.id.edt_dk_tentk);
        edt_mk = view.findViewById(R.id.edt_dk_mk);
        edt_hoten = view.findViewById(R.id.edt_dk_hoten);
        edt_diachi = view.findViewById(R.id.edt_dk_diachi);
        edt_sdt = view.findViewById(R.id.edt_dk_sodt);
        btn_dangky = view.findViewById(R.id.btn_dangky);
        tv_dangnhapngay = view.findViewById(R.id.tv_dangnhap);
    }
}