package com.example.mangxahoigump.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Controller.DSBanBe;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.TrangCaNhan;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DSKetBanAdapter extends RecyclerView.Adapter<DSKetBanAdapter.NguoiDungViewHolder> {
    private Context context;
    private List<NguoiDung> nguoiDungList;

    public DSKetBanAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NguoiDung> list) {
        this.nguoiDungList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_ban_be, parent, false);

        return new NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungViewHolder holder, int position) {
        NguoiDung nguoiDung = nguoiDungList.get(position);
        if (nguoiDung == null) {

        } else {
            if (nguoiDung.getHinhanh() == null) {

            } else {
                Picasso.with(context).load(ApiService.BASE_URL + nguoiDung.getHinhanh().substring(23, nguoiDung.getHinhanh().length())).into(holder.imghinh);
            }
            holder.txtten.setText(nguoiDung.getHoten());

            holder.btnkb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.btnkb.getText().equals("Huỷ")){
                        holder.btnkb.setText("Kết bạn");
                        holder.btnan.setVisibility(View.VISIBLE);
                        deleteBbchoxn(ApiService.BASE_URL, DangNhap.idnd,nguoiDung.getMand(),nguoiDung);
                    }else{
                        holder.btnkb.setText("Huỷ");
                        holder.btnan.setVisibility(View.INVISIBLE);
                        postDsbbchoxn(ApiService.BASE_URL, DangNhap.idnd,nguoiDung.getMand());
                    }
                }
            });
            holder.btnan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nguoiDungList.remove(nguoiDung);
                    notifyDataSetChanged();
                }
            });
            holder.imghinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TrangCaNhan.class);
                    intent.putExtra("mand", nguoiDung.getMand());
                    context.startActivity(intent);
                }
            });
        }
    }

    private void deleteBbchoxn(String baseurl, int mand, int mandbb,NguoiDung nguoiDung) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiservice.deleteBanbechoxn(mand,mandbb);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                    nguoiDungList.remove(nguoiDung);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context,t+ "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postDsbbchoxn(String baseurl,int mand,int mandbb) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiservice.postBanbechoxn(mand,mandbb);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    @Override
    public int getItemCount() {
        if (nguoiDungList != null) {
            return nguoiDungList.size();
        }
        return 0;
    }

    public class NguoiDungViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout dongbb;
        private CircleImageView imghinh;
        private TextView txtten;
        private Button btnkb, btnan;

        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);
            dongbb = itemView.findViewById(R.id.dong_ban_be);
            imghinh = itemView.findViewById(R.id.img_dong_ban_be);
            txtten = itemView.findViewById(R.id.txtten_dong_ban_be);
            btnkb = itemView.findViewById(R.id.btn_ket_ban);
            btnan = itemView.findViewById(R.id.btn_an);
        }
    }

}
