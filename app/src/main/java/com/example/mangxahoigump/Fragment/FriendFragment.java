package com.example.mangxahoigump.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.BaiVietAdapter;
import com.example.mangxahoigump.Adapter.DSChoKetBanAdapter;
import com.example.mangxahoigump.Adapter.DSKetBanAdapter;
import com.example.mangxahoigump.Controller.DSBanBe;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.TimKiemBanBe;
import com.example.mangxahoigump.Controller.TrangCaNhan;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    View view;
    RecyclerView rcv, rcv1;
    DSKetBanAdapter adapter;
    DSChoKetBanAdapter adapter1;
    ImageView imgtimkiem;
    ArrayList<NguoiDung> nguoiDungList = new ArrayList<>();
    ArrayList<NguoiDung> ndchoxnList = new ArrayList<>();
    TextView txtbanbe;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        init();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        getDsbanbe(ApiService.BASE_URL);
        getDSchoxn(ApiService.BASE_URL);
        imgtimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimKiemBanBe.class);
                startActivity(intent);
            }
        });
        txtbanbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DSBanBe.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getDSchoxn(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<NguoiDung>> call = apiservice.getDSbanbechoxn1(DangNhap.idnd);
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    int ma = response.body().get(i).getMand();
                    String ten = response.body().get(i).getHoten();
                    String hinh = response.body().get(i).getHinhanh();
                    NguoiDung nguoiDung = new NguoiDung(ma, ten, hinh);
                    ndchoxnList.add(nguoiDung);
                }
                adapter1 = new DSChoKetBanAdapter(getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                rcv1.setLayoutManager(linearLayoutManager);
                adapter1.setData(ndchoxnList);
                rcv1.setAdapter(adapter1);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
            }
        });
    }

    private void getDsbanbe(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<NguoiDung>> call = apiservice.getDSbanbe(DangNhap.idnd);
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    int ma = response.body().get(i).getMand();
                    String ten = response.body().get(i).getHoten();
                    String hinh = response.body().get(i).getHinhanh();
                    NguoiDung nguoiDung = new NguoiDung(ma, ten, hinh);
                    nguoiDungList.add(nguoiDung);
                }
                adapter = new DSKetBanAdapter(getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                rcv.setLayoutManager(linearLayoutManager);
                adapter.setData(nguoiDungList);
                rcv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {
                Toast.makeText(getActivity(), t + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        imgtimkiem= view.findViewById(R.id.img_search_bb);
        rcv = view.findViewById(R.id.rcv_nguoi_dung_kb);
        rcv1 = view.findViewById(R.id.rcv_nguoi_dung_cho_xn);
        txtbanbe =  view.findViewById(R.id.txt_ds_bb);
    }

}