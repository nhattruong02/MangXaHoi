package com.example.mangxahoigump.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.DSBinhLuanAdapter;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.DongBinhLuan;
import com.example.mangxahoigump.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BinhLuan extends AppCompatActivity {
    RecyclerView rcv;
    DSBinhLuanAdapter adapter;
    ImageView imgback,imgbl;
    EditText edtbl;
    ArrayList<DongBinhLuan> binhLuanList = new ArrayList<>();
    public static ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dong_binh_luan);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        int mabv = getIntent().getIntExtra("mabv",0);
        getDSbinhluan(ApiService.BASE_URL,mabv);
        imgbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postBinhluan(ApiService.BASE_URL,mabv);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMain();
            }
        });
    }

    private void postBinhluan(String baseurl,int mabv) {
        progressDialog.show();
        String noidung = edtbl.getText().toString().trim();
        String hinh = "1";
        DateFormat format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String tg = format.format(Calendar.getInstance().getTime());
        int mand = DangNhap.idnd;
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<DongBinhLuan> call = apiService.postDongbl(mabv,mand,noidung,tg,hinh);
        call.enqueue(new Callback<DongBinhLuan>() {
            @Override
            public void onResponse(Call<DongBinhLuan> call, Response<DongBinhLuan> response) {
                if(response.body() != null){
                    getDSbinhluan(ApiService.BASE_URL,mabv);
                    edtbl.setText("");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DongBinhLuan> call, Throwable t) {

            }
        });
    }

    private void getDSbinhluan(String baseurl,int mabv) {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<DongBinhLuan>> call = apiService.getDongbl(mabv);
        call.enqueue(new Callback<ArrayList<DongBinhLuan>>() {
            @Override
            public void onResponse(Call<ArrayList<DongBinhLuan>> call, Response<ArrayList<DongBinhLuan>> response) {
                binhLuanList = response.body();
                if(!binhLuanList.isEmpty()){
                    adapter = new DSBinhLuanAdapter(BinhLuan.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BinhLuan.this, RecyclerView.VERTICAL, false);
                    rcv.setLayoutManager(linearLayoutManager);;
                    adapter.setData(binhLuanList);
                    rcv.setAdapter(adapter);
                }else{
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DongBinhLuan>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void intentMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void init(){
        rcv = findViewById(R.id.rcv_dong_binh_luan);
        imgback = findViewById(R.id.img_quay_lai_bl);
        edtbl = findViewById(R.id.edt_binh_luan);
        imgbl = findViewById(R.id.img_gui_binh_luan);
    }
}
