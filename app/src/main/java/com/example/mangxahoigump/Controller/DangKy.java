package com.example.mangxahoigump.Controller;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.CheckInternet;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKy extends AppCompatActivity {
    Button btndk, btnthoat;
    EditText edttkdk, edtmkdk, edthoten, edtgt, edtns, edtsdt;
    DatePickerDialog.OnDateSetListener dateOnClickListener;
    ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_ky);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        edtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int nam = cal.get(Calendar.YEAR);
                int thang = cal.get(Calendar.MONTH);
                int ngay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        DangKy.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateOnClickListener,
                        nam, thang, ngay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateOnClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                thang += 1;
                String date = ngay + "/" + thang + "/" + nam;
                edtns.setText(date);
            }
        };
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (CheckInternet.checkI(DangKy.this)) {
                    String tk = edttkdk.getText().toString().trim();
                    Call<NguoiDung> call = apiservice.kiemTratk(tk);
                    call.enqueue(new Callback<NguoiDung>() {
                        @Override
                        public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                            if(response.body() == null){
                                postTaiKhoan();
                            }else{
                                Toast.makeText(DangKy.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<NguoiDung> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(DangKy.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
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


    private void postTaiKhoan() {
        String strtkdk = edttkdk.getText().toString().trim();
        String strmkdk = edtmkdk.getText().toString().trim();
        String strhoten = edthoten.getText().toString().trim();
        String strgt = edtgt.getText().toString().trim();
        SimpleDateFormat format = new SimpleDateFormat("dd/m/yyyy");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
        String strns = "";
        try {
            strns = format1.format(format.parse(edtns.getText().toString().trim())) +"";
        } catch (ParseException e) {
        }
        String strsdt = edtsdt.getText().toString().trim();
        if (strtkdk.equals("") || strmkdk.equals("") || strhoten.equals("") || strsdt.equals("") || strns.equals("")) {
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            Call<NguoiDung> call = apiservice.postNguoidung(strtkdk,strmkdk,strhoten,strgt,strns,strsdt);
            call.enqueue(new Callback<NguoiDung>() {
                @Override
                public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                    NguoiDung postresult = response.body();
                    if (postresult != null) {
                        intentDangKy();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<NguoiDung> call, Throwable t) {
                }
            });
        }
    }


    private void intentThoat() {
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
    }

    private void intentDangKy() {
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
    }

    private void init() {
        btndk = (Button) findViewById(R.id.btndangkytk);
        btnthoat = (Button) findViewById(R.id.btnthoatdk);
        edttkdk = (EditText) findViewById(R.id.edt_tkdk);
        edtmkdk = (EditText) findViewById(R.id.edt_mkdk);
        edthoten = (EditText) findViewById(R.id.edt_htdk);
        edtgt = (EditText) findViewById(R.id.edt_gtdk);
        edtsdt = (EditText) findViewById(R.id.edt_sdtdk);
        edtns = (EditText) findViewById(R.id.edt_nsdk);
    }
}
