package com.example.mangxahoigump.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DSBanBeAdapter extends RecyclerView.Adapter<DSBanBeAdapter.NguoiDungViewHolder> {
    private Context context;
    private List<NguoiDung> nguoiDungList;
    ProgressDialog progressDialog;

    public DSBanBeAdapter(Context context) {
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
    public DSBanBeAdapter.NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_ds_ban_be, parent, false);
        return new DSBanBeAdapter.NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSBanBeAdapter.NguoiDungViewHolder holder, int position) {
        NguoiDung nguoiDung = nguoiDungList.get(position);
        if (nguoiDung == null) {

        } else {
            if (nguoiDung.getHinhanh().equals("")) {
                holder.imghinh.setImageResource(R.drawable.spcr);
            } else {
                Picasso.with(context).load(ApiService.BASE_URL + nguoiDung.getHinhanh().substring(23, nguoiDung.getHinhanh().length())).into(holder.imghinh);
            }
            holder.txtten.setText(nguoiDung.getHoten());
            holder.btnhuykb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Please wait ...");
                    progressDialog.show();
                    huyKbdsbanbe(ApiService.BASE_URL, DangNhap.idnd, nguoiDung.getMand(), nguoiDung);
                }
            });
        }
    }

    private void huyKbdsbanbe(String baseurl, int mand, int mandbb, NguoiDung nguoiDung) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiService.deleteBanbechoxn(mand, mandbb);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() == 1) {
                    nguoiDungList.remove(nguoiDung);
                    notifyDataSetChanged();
                    progressDialog.dismiss();
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
        private CircleImageView imghinh;
        private TextView txtten;
        private Button btnhuykb;

        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinh = itemView.findViewById(R.id.img_dong_ds_ban_be);
            txtten = itemView.findViewById(R.id.txtten_dong_ds_ban_be);
            btnhuykb = itemView.findViewById(R.id.btn_huy_kb_dong_ds_bb);
        }
    }

}
