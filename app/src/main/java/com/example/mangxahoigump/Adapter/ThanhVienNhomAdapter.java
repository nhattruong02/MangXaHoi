package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThanhVienNhomAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<NguoiDung> nguoiDungList;
    public ThanhVienNhomAdapter(Context context, int layout, List<NguoiDung> nguoiDungList) {
        this.context = context;
        this.layout = layout;
        this.nguoiDungList = nguoiDungList;
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
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThanhVienNhomAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ThanhVienNhomAdapter.ViewHolder();
            holder.imgavatar = view.findViewById(R.id.img_dong_tv_nhom);
            holder.txtten = view.findViewById(R.id.txtten_dong_tv_nhom);
            view.setTag(holder);
        } else {
            holder = (ThanhVienNhomAdapter.ViewHolder) view.getTag();
        }
        NguoiDung nguoiDung = nguoiDungList.get(i);
        if (nguoiDung.getHinhanh() == null) {
            holder.imgavatar.setImageResource(R.drawable.spcr);
        } else {
            Picasso.with(context).load(ApiService.BASE_URL + nguoiDung.getHinhanh().substring(23, nguoiDung.getHinhanh().length())).into(holder.imgavatar);
        }
        holder.txtten.setText(nguoiDung.getHoten());
        return view;
    }
}
