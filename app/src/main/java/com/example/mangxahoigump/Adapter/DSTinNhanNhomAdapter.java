package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.Controller.NhanTin;
import com.example.mangxahoigump.Controller.NhanTinNhom;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DSTinNhanNhomAdapter extends RecyclerView.Adapter<DSTinNhanNhomAdapter.NguoiDungViewHolder> {
    private Context context;
    private List<Nhom> nhomsList;
    public static Nhom nhom1;
    public DSTinNhanNhomAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Nhom> list) {
        this.nhomsList = list;
        notifyDataSetChanged();
    }
    public void filterList(ArrayList<Nhom> filterlist) {
        nhomsList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DSTinNhanNhomAdapter.NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_ds_tin_nhan, parent, false);

        return new DSTinNhanNhomAdapter.NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSTinNhanNhomAdapter.NguoiDungViewHolder holder, int position) {
        final Nhom nhom = nhomsList.get(position);
        if (nhom == null) {

        } else {
            if (nhom.getHinhanh().equals("")) {
                holder.imghinh.setImageResource(R.drawable.spcr);
            } else {
                Picasso.with(context).load(ApiService.BASE_URL + nhom.getHinhanh().substring(23, nhom.getHinhanh().length())).into(holder.imghinh);
            }
            holder.txtten.setText(nhom.getTennhom());

        }
        holder.dongtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicktinnhan(nhom);
            }
        });
    }

    private void onClicktinnhan(Nhom nhom) {
        Intent intent = new Intent(context, NhanTinNhom.class);
        nhom1 = nhom;
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (nhomsList != null) {
            return nhomsList.size();
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
