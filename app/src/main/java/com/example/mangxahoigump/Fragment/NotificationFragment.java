package com.example.mangxahoigump.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.ThongBaoAdapter;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.TrangCaNhan;
import com.example.mangxahoigump.Model.ThongBao;
import com.example.mangxahoigump.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    View view;
    ListView lvthongbao;
    ThongBaoAdapter adapter;
    List<ThongBao> thongBaoList = new ArrayList<>();
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        init();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        getThongbao(ApiService.BASE_URL);
        lvthongbao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ThongBao thongBao = thongBaoList.get(i);
                Intent intent = new Intent(getActivity(), TrangCaNhan.class);
                intent.putExtra("mand",thongBao.getMand());
                startActivity(intent);
            }
        });
        return view;
    }

    private void getThongbao(String baseurl) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<ThongBao>> call = apiService.getthongbao(DangNhap.idnd);
        call.enqueue(new Callback<ArrayList<ThongBao>>() {
            @Override
            public void onResponse(Call<ArrayList<ThongBao>> call, Response<ArrayList<ThongBao>> response) {
                thongBaoList = response.body();
                if(thongBaoList != null){
                    thongBaoList = response.body();
                    adapter = new ThongBaoAdapter(getContext(),R.layout.dong_thong_bao,thongBaoList);
                    lvthongbao.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ThongBao>> call, Throwable t) {
                Toast.makeText(getContext(), t+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        lvthongbao = view.findViewById(R.id.lv_thong_bao);
    }
}
