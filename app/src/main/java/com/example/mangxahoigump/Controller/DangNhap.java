package com.example.mangxahoigump.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.CheckInternet;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DangNhap extends AppCompatActivity {
    Button btndangnhap;
    TextView btndangky,txtdoimk;
    EditText edttk,edtmk;
    public static int idnd;
    public static String tennd;
    public static String hinh;
    ProgressDialog progressDialog;
    private ArrayList<NguoiDung> listnguoidung = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_nhap);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        getListTaiKhoan(ApiService.BASE_URL);
        txtdoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentDoimk();
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentDangKy();
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckInternet.checkI(DangNhap.this)){
                    progressDialog.show();
                    intentDangNhap();
                }
                else{
                    Toast.makeText(DangNhap.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void intentDoimk() {
        Intent intent = new Intent(this,DoiMatKhau.class);
        startActivity(intent);
    }

    private void getListTaiKhoan(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<NguoiDung>> call = apiservice.getNguoidungs();
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                listnguoidung = response.body();
            }
            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
            }
        });
    }

    private void intentDangNhap() {
        String strtaikhoan = edttk.getText().toString().trim();
        String strmatkhau = edtmk.getText().toString().trim();
        if(strtaikhoan.equals("") || strmatkhau.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else {
            if (listnguoidung == null || listnguoidung.isEmpty()) {

            }
            boolean hasTaiKhoan = false;
            for (NguoiDung nguoidung : listnguoidung) {
                if (strtaikhoan.equals(nguoidung.getTaikhoan().trim()) && strmatkhau.equals(nguoidung.getMatkhau().trim())) {
                    idnd = nguoidung.getMand();
                    tennd = nguoidung.getHoten();
                    hinh = nguoidung.getHinhanh();
                    Intent intent = new Intent(DangNhap.this,MainActivity.class);
                    intent.putExtra("mand",nguoidung.getMand());
                    intent.putExtra("tennd",nguoidung.getHoten());
                    intent.putExtra("hinh",nguoidung.getHinhanh());
                    startActivity(intent);
                    hasTaiKhoan = true;
                    progressDialog.dismiss();
                    break;
                }
            }
            if (!hasTaiKhoan) {
                Toast.makeText(this, "Sai tài khoản mật khẩu!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }
    }

    private void intentDangKy() {
        Intent intent = new Intent(this,DangKy.class);
        startActivity(intent);
    }

    private void init(){
        txtdoimk = findViewById(R.id.txtdoimk);
        btndangnhap = (Button) findViewById(R.id.btndangnhap);
        btndangky = (TextView) findViewById(R.id.txtdangky);
        edttk = (EditText) findViewById(R.id.edt_tk);
        edtmk = (EditText) findViewById(R.id.edt_mk);
    }


}