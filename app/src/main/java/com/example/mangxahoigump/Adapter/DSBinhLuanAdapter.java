package com.example.mangxahoigump.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Controller.BinhLuan;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.DongBinhLuan;
import com.example.mangxahoigump.Model.NguoiDung;
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

public class DSBinhLuanAdapter extends RecyclerView.Adapter<DSBinhLuanAdapter.BinhLuanViewHolder> {
    private Context context;
    private List<DongBinhLuan> binhLuanList;
    private PopupMenu popupMenu;
    ProgressDialog progressDialog;
    public DSBinhLuanAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DongBinhLuan> list) {
        this.binhLuanList = list;
        //notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DSBinhLuanAdapter.BinhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_binh_luan, parent, false);

        return new DSBinhLuanAdapter.BinhLuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DSBinhLuanAdapter.BinhLuanViewHolder holder, int position) {
        DongBinhLuan binhLuan = binhLuanList.get(position);
        if (binhLuan == null) {

        } else {
            int mand = binhLuan.getMand();
            ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
            Call<NguoiDung> call = apiService.getNguoidungsbl(mand);
            call.enqueue(new Callback<NguoiDung>() {
                @Override
                public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                    if(response.body()!= null){
                        holder.txtten.setText(response.body().getHoten().toString().trim());
                        if(response.body().getHinhanh() == null){
                            holder.imgdongbl.setImageResource(R.drawable.spcr);
                        }
                        else{
                            Picasso.with(context).load(ApiService.BASE_URL+response.body().getHinhanh().substring(23,response.body().getHinhanh().length())).into(holder.imgdongbl);
                        }
                        BinhLuan.progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<NguoiDung> call, Throwable t) {

                }
            });
            holder.txtnoidung.setText(binhLuan.getNoidung());
            long tgiantv = 0;
            long tghientai =0;
            long tgianbaiviet =0;
            String tgian = "";
            SimpleDateFormat format= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            try {
                tghientai = System.currentTimeMillis();
                tgian = format.format(binhLuan.getThoigian());
                tgianbaiviet  = format.parse(tgian.trim()).getTime();
            } catch (ParseException e) {
            }
            tgiantv= tghientai - tgianbaiviet;
            if(TimeUnit.MILLISECONDS.toMinutes(tgiantv) <= 60)
            {
                holder.txttg.setText(TimeUnit.MILLISECONDS.toMinutes(tgiantv) +" phút trước");
            }
            else if(TimeUnit.MILLISECONDS.toHours(tgiantv) <= 24){
                holder.txttg.setText(TimeUnit.MILLISECONDS.toHours(tgiantv) +" giờ trước");
            }
            else if(TimeUnit.MILLISECONDS.toDays(tgiantv) >=1 ){
                holder.txttg.setText(format.format(binhLuan.getThoigian()).substring(0,10));
            }
            if(binhLuan.getMand() == DangNhap.idnd){
                holder.imgmenu.setVisibility(View.VISIBLE);
                holder.imgmenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("1002",binhLuan+"");
                        popupMenu = new PopupMenu(context,view);
                        MenuInflater inflater = popupMenu.getMenuInflater();
                        inflater.inflate(R.menu.menu_bai_viet,popupMenu.getMenu());
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.menu_sua_bv:
                                        int madblsua = binhLuan.getMadbl();
                                        Log.e("122111",madblsua+"");
                                        progressDialog = new ProgressDialog(context);
                                        progressDialog.setMessage("Please wait ...");
                                        suaBinhluan(ApiService.BASE_URL,madblsua,binhLuan);
                                        break;
                                    case R.id.menu_xoa_bv:
                                        progressDialog = new ProgressDialog(context);
                                        progressDialog.setMessage("Please wait ...");
                                        progressDialog.show();
                                        int madbl = binhLuan.getMadbl();
                                        deleteXoabinhluan(ApiService.BASE_URL,madbl,binhLuan);
                                        break;
                                }
                                return false;
                            }
                        });
                    }
                });
            }
            else{
                holder.imgmenu.setVisibility(View.INVISIBLE);
            }
        }
    }




    @Override
    public int getItemCount() {
        if (binhLuanList != null) {
            return binhLuanList.size();
        }
        return 0;
    }

    public class BinhLuanViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout dong_bl;
        private CircleImageView imgdongbl;
        private TextView txtten,txtnoidung,txttg;
        private ImageView imgmenu;
        public BinhLuanViewHolder(@NonNull View itemView) {
            super(itemView);
            dong_bl = itemView.findViewById(R.id.dong_ds_bl);
            imgdongbl = itemView.findViewById(R.id.img_dong_bl);
            txtten = itemView.findViewById(R.id.txtten_dong_bl);
            txtnoidung = itemView.findViewById(R.id.txt_nd_dong_bl);
            txttg = itemView.findViewById(R.id.txt_tg_bl);
            imgmenu = itemView.findViewById(R.id.img_menu_bl);
        }
    }
    private void suaBinhluan(String baseurl, int madbl, DongBinhLuan binhLuan) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sua_dong_binh_luan);
        Button btnsua = dialog.findViewById(R.id.btnsuabl);
        Button btnhuy = dialog.findViewById(R.id.btnhuysuabl);
        EditText edtnd = dialog.findViewById(R.id.edt_noi_dung_suabl);
        dialog.show();
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String nd = edtnd.getText().toString().trim();
                ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
                Call<DongBinhLuan> call = apiService.putDongbinhluans(madbl,nd);
                call.enqueue(new Callback<DongBinhLuan>() {
                    @Override
                    public void onResponse(Call<DongBinhLuan> call, Response<DongBinhLuan> response) {
                        if(response.body() != null) {
                            Toast.makeText(context, "Cập nhật bình luận thành công", Toast.LENGTH_SHORT).show();
                            binhLuan.setNoidung(response.body().getNoidung());
                            notifyDataSetChanged();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DongBinhLuan> call, Throwable t) {
                        Toast.makeText(context, t+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void deleteXoabinhluan(String baseurl,int madbl,DongBinhLuan binhLuan) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiService.deleteDongbinhluans(madbl);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1 && response.body() != null){
                    Toast.makeText(context, "Xoá bài viết thành công", Toast.LENGTH_SHORT).show();
                    binhLuanList.remove(binhLuan);
                    notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
}
