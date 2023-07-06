package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.NhanTin;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DSTinNhanAdapter extends RecyclerView.Adapter<DSTinNhanAdapter.NguoiDungViewHolder> {
    private Context context;
    private List<NguoiDung> nguoiDungList;

    public DSTinNhanAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NguoiDung> list) {
        this.nguoiDungList = list;
        notifyDataSetChanged();
    }
    public void filterList(ArrayList<NguoiDung> filterlist) {
        nguoiDungList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DSTinNhanAdapter.NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_ds_tin_nhan, parent, false);

        return new DSTinNhanAdapter.NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSTinNhanAdapter.NguoiDungViewHolder holder, int position) {
        final NguoiDung nguoiDung = nguoiDungList.get(position);
        if (nguoiDung == null) {

        } else {
            if (nguoiDung.getHinhanh().equals("")) {
                holder.imghinh.setImageResource(R.drawable.spcr);
            } else {
                Picasso.with(context).load(ApiService.BASE_URL + nguoiDung.getHinhanh().substring(23, nguoiDung.getHinhanh().length())).into(holder.imghinh);
            }
            holder.txtten.setText(nguoiDung.getHoten());

        }
        holder.dongtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicktinnhan(nguoiDung);
            }
        });
    }

    private void onClicktinnhan(NguoiDung nguoiDung) {
        Intent intent = new Intent(context, NhanTin.class);
        intent.putExtra("tinnhan",1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("nd",nguoiDung);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (nguoiDungList != null) {
            return nguoiDungList.size();
        }
        return 0;
    }

    public class NguoiDungViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout dongtn;
        private CircleImageView imghinh;
        private TextView txtten;

        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);
            dongtn = itemView.findViewById(R.id.dong_ds_tin_nhan);
            imghinh = itemView.findViewById(R.id.img_dong_ds_tin_nhan);
            txtten = itemView.findViewById(R.id.txtten_dong_ds_tin_nhan);
        }
    }

}