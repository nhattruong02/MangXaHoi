package com.example.mangxahoigump.Controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.DSBanBeAdapter;
import com.example.mangxahoigump.Adapter.DSChoKetBanAdapter;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DSBanBe extends AppCompatActivity {
    RecyclerView rcv;
    DSBanBeAdapter adapter;
    ArrayList<NguoiDung> nguoiDungList = new ArrayList<>();
    EditText edttimkiem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danh_sach_ban_be);
        init();
        getDsbanbe(ApiService.BASE_URL);
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
        edttimkiem = findViewById(R.id.edt_tim_kiem_ds_ban_be);
        rcv = (RecyclerView) findViewById(R.id.rcv_ds_ban_be);
    }
    private void getDsbanbe(String baseurl) {
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
                adapter = new DSBanBeAdapter(DSBanBe.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DSBanBe.this, RecyclerView.VERTICAL, false);
                rcv.setLayoutManager(linearLayoutManager);
                adapter.setData(nguoiDungList);
                rcv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
                Toast.makeText(DSBanBe.this,t+ "", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
