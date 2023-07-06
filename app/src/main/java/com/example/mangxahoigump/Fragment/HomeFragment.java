package com.example.mangxahoigump.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.BaiVietAdapter;
import com.example.mangxahoigump.Controller.DangBaiViet;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.Likes;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    View view;
    TextView tvdangbv;
    SwipeRefreshLayout rfhome;
    ArrayList<BaiViet> listbaiviet= new ArrayList<>();
    BaiVietAdapter adapter;
    ListView lvbaiviet ;
    CircleImageView imgavatar;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        if(DangNhap.hinh != null) {
            Picasso.with(getActivity()).load(ApiService.BASE_URL + DangNhap.hinh.substring(23, DangNhap.hinh.length())).into(imgavatar);
        }else{
            imgavatar.setImageResource(R.drawable.ic_avatar_default);
        }
        getBaiviet(ApiService.BASE_URL);
        tvdangbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentDangbv();
            }
        });
       rfhome.setOnRefreshListener(this);
        return view;
    }

    private void getBaiviet(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<BaiViet>> call = apiservice.getBaiviets();
        call.enqueue(new Callback<ArrayList<BaiViet>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiViet>> call, Response<ArrayList<BaiViet>> response) {
                listbaiviet = response.body();
                if(listbaiviet != null){
                    adapter = new BaiVietAdapter(getActivity(),R.layout.dong_bai_viet,listbaiviet);
                    lvbaiviet.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<BaiViet>> call, Throwable t) {
            }
        });
    }

    private void intentDangbv() {
        Intent intent = new Intent(getActivity(), DangBaiViet.class);
        startActivity(intent);
    }

    private void init() {
        tvdangbv = view.findViewById(R.id.tv_dang_bai_viet);
        imgavatar = view.findViewById(R.id.img_avatar_home);
        lvbaiviet = view.findViewById(R.id.lv_bai_viet);
        rfhome = view.findViewById(R.id.rf_home);
    }

    @Override
    public void onRefresh() {
        getBaiviet(ApiService.BASE_URL);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rfhome.setRefreshing(false);
            }
        },5000);
    }
}
