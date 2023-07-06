package com.example.mangxahoigump.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.BaiVietAdapter;
import com.example.mangxahoigump.Adapter.DSTinNhanNhomAdapter;
import com.example.mangxahoigump.Adapter.ThemThanhVienAdapter;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTVNhom extends AppCompatActivity {
    ImageView imgquaylai;
    EditText edttimkiem;
    ListView lv;
    List<NguoiDung> nguoiDungList = new ArrayList<>();
    ThemThanhVienAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_tv_nhom);
        init();
        getThemtvnhom(ApiService.BASE_URL);
        imgquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemTVNhom.this,ThongTinNhom.class);
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
        for (NguoiDung n: nguoiDungList) {
            if (n.getHoten().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(n);
            }
        }
        adapter.filterList(filteredList);
    }
    private void getThemtvnhom(String baseurl) {
        int manhom = DSTinNhanNhomAdapter.nhom1.getManhom();
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<NguoiDung>> call = apiservice.getDSthemndnhom(DangNhap.idnd,manhom);
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                nguoiDungList = response.body();
                if(nguoiDungList != null){
                    adapter = new ThemThanhVienAdapter(ThemTVNhom.this,R.layout.dong_them_tv_nhom,nguoiDungList);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
            }
        });
    }
    private void init(){
        imgquaylai = findViewById(R.id.img_quay_lai_themtv);
        edttimkiem = findViewById(R.id.edt_tk_them_tv);
        lv = findViewById(R.id.lv_them_tv);
    }
}
