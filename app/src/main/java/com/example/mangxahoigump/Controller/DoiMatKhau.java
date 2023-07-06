package com.example.mangxahoigump.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.CheckInternet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiMatKhau extends AppCompatActivity {
    EditText edttk,edtmk,edtmkmoi,edtxnmk;
    Button btndoimk,btnthoat;
    ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_doi_mat_khau);
        init();
        progressDialog = new ProgressDialog(this);
        btndoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (CheckInternet.checkI(DoiMatKhau.this)) {
                    String tk = edttk.getText().toString().trim();
                    Call<NguoiDung> call = apiservice.kiemTratk(tk);
                    call.enqueue(new Callback<NguoiDung>() {
                        @Override
                        public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                            if(response.body() != null){
                                String mk = response.body().getMatkhau().trim();
                                putMatkhau(mk);
                            }else{
                                Toast.makeText(DoiMatKhau.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<NguoiDung> call, Throwable t) {
                        }
                    });
                } else {
                    Toast.makeText(DoiMatKhau.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentThoat();
            }
        });
    }

    private void putMatkhau(String mk) {
        String strtk = edttk.getText().toString().trim();
        String strmk = edtmk.getText().toString().trim();
        String strmkmoi = edtmkmoi.getText().toString().trim();
        String strmkxn = edtxnmk.getText().toString().trim();
        if (strtk.equals("") || strmk.equals("") || strmkmoi.equals("") || strmkxn.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else if(!strmkmoi.equals(strmkxn)){
            Toast.makeText(this, "Mật khẩu xác nhận không trùng khớp!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else if(!strmk.equals(mk)){
            Toast.makeText(this, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else {
            Call<NguoiDung> call = apiservice.putMatkhau(strtk,strmkmoi);
            call.enqueue(new Callback<NguoiDung>() {
                @Override
                public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                    NguoiDung postresult = response.body();
                    if (postresult != null) {
                        intentDangNhap();
                        Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<NguoiDung> call, Throwable t) {
                }
            });
        }
    }

    private void intentDangNhap() {
        Intent intent = new Intent(DoiMatKhau.this,DangNhap.class);
        startActivity( intent);
    }

    private void intentThoat() {
        Intent intent = new Intent(DoiMatKhau.this,DangNhap.class);
        startActivity( intent);
    }

    private void init(){
        edttk = findViewById(R.id.edt_tkdmk);
        edtmk = findViewById(R.id.edt_mkdmk);
        edtmkmoi = findViewById(R.id.edt_mkmoi);
        edtxnmk = findViewById(R.id.edt_xnmk);
        btndoimk = findViewById(R.id.btndoimk);
        btnthoat = findViewById(R.id.btnthoatdmk);
    }
}
