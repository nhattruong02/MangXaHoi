package com.example.mangxahoigump.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.DSBanBeAdapter;
import com.example.mangxahoigump.Adapter.DSTinNhanAdapter;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.R;
import com.example.mangxahoigump.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DSTinNhan extends AppCompatActivity {
    RecyclerView rcv;
    DSTinNhanAdapter adapter;
    ImageView imgback;
    EditText edttimkiem;
    ArrayList<NguoiDung> nguoiDungList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ds_tin_nhan);
        init();
        getDstinnhan(ApiService.BASE_URL);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DSTinNhan.this, MainActivity.class);
                startActivity(intent);
            }
        });
        edttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<NguoiDung> filteredList = new ArrayList<>();
        for (NguoiDung nd : nguoiDungList) {
            if (nd.getHoten().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(nd);
            }
        }
        adapter.filterList(filteredList);
    }
    private void init(){
        rcv = findViewById(R.id.rcv_ds_tin_nhan);
        imgback = findViewById(R.id.img_quay_lai_dstn);
        edttimkiem = findViewById(R.id.edt_tk_ds_tin_nhan);
    }

    private void getDstinnhan(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<NguoiDung>> call = apiservice.getDSbanbedakb(DangNhap.idnd);
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    int ma = response.body().get(i).getMand();
                    String ten = response.body().get(i).getHoten();
                    String hinh = "";
                    if(response.body().get(i).getHinhanh() == null)
                    {
                        hinh = "";
                    }else {
                        hinh = response.body().get(i).getHinhanh().trim();
                    }
                    NguoiDung nguoiDung = new NguoiDung(ma, ten, hinh);
                    nguoiDungList.add(nguoiDung);
                }
                Log.e("ds",nguoiDungList+"");
                adapter = new DSTinNhanAdapter(DSTinNhan.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DSTinNhan.this, RecyclerView.VERTICAL, false);
                rcv.setLayoutManager(linearLayoutManager);
                adapter.setData(nguoiDungList);
                rcv.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
                Toast.makeText(DSTinNhan.this,t+ "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
