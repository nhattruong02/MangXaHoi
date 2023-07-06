package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.ThongBao;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongBaoAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<ThongBao> thongbaoList;

    public ThongBaoAdapter(Context context, int layout, List<ThongBao> thongbaoList) {
        this.context = context;
        this.layout = layout;
        this.thongbaoList = thongbaoList;
    }

    @Override
    public int getCount() {
        return  thongbaoList.size();
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
        TextView txtnoidung,txttgian,txtten;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThongBaoAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ThongBaoAdapter.ViewHolder();
            holder.imgavatar = view.findViewById(R.id.img_dong_tb);
            holder.txtnoidung= view.findViewById(R.id.txt_nd_tb);
            holder.txtten = view.findViewById(R.id.txtten_dong_tb);
            holder.txttgian = view.findViewById(R.id.txt_tg_tb);
            view.setTag(holder);
        } else {
            holder = (ThongBaoAdapter.ViewHolder) view.getTag();
        }
        ThongBao thongBao = thongbaoList.get(i);
        int mand = thongBao.getMand();
        ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
        Call<NguoiDung> call = apiService.getNguoidungsbl(mand);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if(response.body()!= null){
                    holder.txtten.setText(response.body().getHoten().toString().trim());
                    if(response.body().getHinhanh() == null){
                        holder.imgavatar.setImageResource(R.drawable.spcr);
                    }
                    else{
                        Picasso.with(context).load(ApiService.BASE_URL+response.body().getHinhanh().substring(23,response.body().getHinhanh().length())).into(holder.imgavatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
        long tgiantv = 0;
        long tghientai =0;
        long tgianbaiviet =0;
        String tgian = "";
        SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            tghientai = System.currentTimeMillis();
            tgian = format.format(thongBao.getThoigian());
            tgianbaiviet  = format.parse(tgian.trim()).getTime();
        } catch (ParseException e) {
        }
        tgiantv= tghientai - tgianbaiviet;
        if(TimeUnit.MILLISECONDS.toMinutes(tgiantv) <= 60)
        {
            holder.txttgian.setText(TimeUnit.MILLISECONDS.toMinutes(tgiantv) +" phút trước");
        }
        else if(TimeUnit.MILLISECONDS.toHours(tgiantv) <= 24){
            holder.txttgian.setText(TimeUnit.MILLISECONDS.toHours(tgiantv) +" giờ trước");
        }
        else if(TimeUnit.MILLISECONDS.toDays(tgiantv) >=1 ){
            holder.txttgian.setText(format.format(thongBao.getThoigian()).substring(0,10));
        }
        if(thongBao.getNoidung().length()<20) {
            holder.txtnoidung.setText(thongBao.getNoidung() + "...");
        }
        else{
            holder.txtnoidung.setText(thongBao.getNoidung().substring(0,20) + "...");
        }
        return view;
    }
}
