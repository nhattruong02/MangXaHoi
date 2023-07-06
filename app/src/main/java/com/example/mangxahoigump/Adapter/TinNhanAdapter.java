package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.NhanTin;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.TinNhan;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinNhanAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<TinNhan> tinnhanList;
    final int DONG_GUI = 1;
    final int DONG_NHAN = 2;

    public TinNhanAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TinNhan> list) {
        this.tinnhanList = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DONG_GUI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_tin_nhan_gui, parent, false);
            return new TinNhanGuiHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_tin_nhan_nhan, parent, false);
            return new TinNhanNhanHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        TinNhan tinNhan = tinnhanList.get(position);
        if (tinNhan.getMand() == DangNhap.idnd) {
            return DONG_GUI;
        } else {
            return DONG_NHAN;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TinNhan tinNhan = tinnhanList.get(position);
        if (holder.getClass() == TinNhanGuiHolder.class) {
            TinNhanGuiHolder tinNhanGuiHolder = (TinNhanGuiHolder) holder;
            tinNhanGuiHolder.txtnoidunggui.setText(tinNhan.getNoidung());
            getAvatargui(ApiService.BASE_URL, tinNhanGuiHolder, tinNhan.getMand());
        } else {
            TinNhanNhanHolder tinNhanNhanHolder = (TinNhanNhanHolder) holder;
            tinNhanNhanHolder.txtnoidungnhan.setText(tinNhan.getNoidung());
            getAvatarnhan(ApiService.BASE_URL, tinNhanNhanHolder, tinNhan.getMand());
        }
    }

    private void getAvatarnhan(String baseurl, TinNhanNhanHolder tinNhanNhanHolder, int mand) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<NguoiDung> call = apiService.getNguoidungsbl(mand);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.body().getHinhanh() == null) {
                    tinNhanNhanHolder.imgavatarnhan.setImageResource(R.drawable.spcr);
                } else {
                    Picasso.with(context).load(ApiService.BASE_URL + response.body().getHinhanh().substring(23, response.body().getHinhanh().length())).into(tinNhanNhanHolder.imgavatarnhan);
                }

            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
    }

    private void getAvatargui(String baseurl, TinNhanGuiHolder tinNhanGuiHolder, int mand) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<NguoiDung> call = apiService.getNguoidungsbl(mand);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.body().getHinhanh() == null) {
                    tinNhanGuiHolder.imgavatargui.setImageResource(R.drawable.spcr);
                } else {
                    Picasso.with(context).load(ApiService.BASE_URL + response.body().getHinhanh().substring(23, response.body().getHinhanh().length())).into(tinNhanGuiHolder.imgavatargui);
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (tinnhanList != null) {
            return tinnhanList.size();
        }
        return 0;
    }

    public class TinNhanNhanHolder extends RecyclerView.ViewHolder {
        private TextView txtnoidungnhan;
        private ImageView imgavatarnhan;

        public TinNhanNhanHolder(@NonNull View itemView) {
            super(itemView);
            txtnoidungnhan = itemView.findViewById(R.id.txt_tin_nhan_nhan);
            imgavatarnhan = itemView.findViewById(R.id.img_dong_tin_nhan_nhan);
        }
    }

    public class TinNhanGuiHolder extends RecyclerView.ViewHolder {
        private TextView txtnoidunggui;
        private ImageView imgavatargui;

        public TinNhanGuiHolder(@NonNull View itemView) {
            super(itemView);
            txtnoidunggui = itemView.findViewById(R.id.txt_tin_nhan_gui);
            imgavatargui = itemView.findViewById(R.id.img_dong_tin_nhan_gui);

        }
    }

}
