package com.example.mangxahoigump.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.DSKetBanAdapter;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemBanBe extends AppCompatActivity {
    ImageView imgquaylai;
    EditText edttimkiem;
    RecyclerView rcv;
    Button btntimkiem;
    List<NguoiDung> nguoiDungList = new ArrayList<>();
    DSKetBanAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tim_kiem_bb);
        init();
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String tttk = edttimkiem.getText().toString().trim();
                getDsbanbe(ApiService.BASE_URL,tttk);
            }
        });
        imgquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentQuaylai();
            }
        });
    }

    private void intentQuaylai() {
        Intent intent = new Intent(TimKiemBanBe.this, MainActivity.class);
        startActivity(intent);
    }

    private void init(){
        imgquaylai = findViewById(R.id.img_quay_lai_tkbb);
        edttimkiem = findViewById(R.id.edt_ds_tk_ban_be);
        rcv = findViewById(R.id.rcv_ds_tk_ban_be);
        btntimkiem = findViewById(R.id.btn_tim_kiem);
    }
    private void getDsbanbe(String baseurl,String tttk) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<NguoiDung>> call = apiservice.getDStkbanbe(DangNhap.idnd,tttk);
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                Log.e("response", response.body()+"");
                if(!response.body().isEmpty()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        int ma = response.body().get(i).getMand();
                        String ten = response.body().get(i).getHoten();
                        String hinh = response.body().get(i).getHinhanh();
                        NguoiDung nguoiDung = new NguoiDung(ma, ten, hinh);
                        nguoiDungList.add(nguoiDung);
                    }
                    adapter = new DSKetBanAdapter(TimKiemBanBe.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TimKiemBanBe.this, RecyclerView.VERTICAL, false);
                    rcv.setLayoutManager(linearLayoutManager);
                    adapter.setData(nguoiDungList);
                    rcv.setAdapter(adapter);
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(TimKiemBanBe.this, "Không có bạn bè cần tìm kiếm!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
                Toast.makeText(TimKiemBanBe.this, t + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
