package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.R;
import com.google.android.gms.common.api.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemThanhVienAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<NguoiDung> nguoiDungList;

    public ThemThanhVienAdapter(Context context, int layout, List<NguoiDung> nguoiDungList) {
        this.context = context;
        this.layout = layout;
        this.nguoiDungList = nguoiDungList;
    }
    public void filterList(ArrayList<NguoiDung> filterlist) {
        nguoiDungList = filterlist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return nguoiDungList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        CircleImageView imgavatar;
        TextView txtten;
        Button btnthem;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThemThanhVienAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ThemThanhVienAdapter.ViewHolder();
            holder.imgavatar = view.findViewById(R.id.img_dong_them_tv_nhom);
            holder.txtten = view.findViewById(R.id.txtten_dong_them_tv_nhom);
            holder.btnthem= view.findViewById(R.id.btn_dong_them_tv_nhom);
            view.setTag(holder);
        } else {
            holder = (ThemThanhVienAdapter.ViewHolder) view.getTag();
        }
        NguoiDung nguoiDung =  nguoiDungList.get(i);
        if (nguoiDung.getHinhanh() == null) {
            holder.imgavatar.setImageResource(R.drawable.spcr);
        } else {
            Picasso.with(context).load(ApiService.BASE_URL + nguoiDung.getHinhanh().substring(23, nguoiDung.getHinhanh().length())).into(holder.imgavatar);
        }
        holder.txtten.setText(nguoiDung.getHoten());
        holder.btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mand = nguoiDung.getMand();
                postNhomnd(ApiService.BASE_URL,mand,nguoiDung);
            }
        });
        return view;
    }

    private void postNhomnd(String baseurl,int mand,NguoiDung nguoiDung) {
        int manhom = DSTinNhanNhomAdapter.nhom1.getManhom();
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiservice.postTvnhom(manhom,mand);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                    Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    nguoiDungList.remove(nguoiDung);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
}
