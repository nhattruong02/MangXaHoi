package com.example.mangxahoigump.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Controller.BinhLuan;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.TrangCaNhan;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.Likes;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiVietAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<BaiViet> baiVietList;
    private PopupMenu popupMenu;
    ProgressDialog progressDialog;
    ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);

    public BaiVietAdapter(Context context, int layout, List<BaiViet> baiVietList) {
        this.context = context;
        this.layout = layout;
        this.baiVietList = baiVietList;
    }

    @Override
    public int getCount() {
        return baiVietList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHoder {
        ImageView imghinh, imgmenu, imgbl, imglike;
        TextView txtten, txttg, txtsllike, txtslbl, txtnoidung;
        CircleImageView imgavatar;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaiVietAdapter.ViewHoder hoder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            hoder = new BaiVietAdapter.ViewHoder();
            hoder.imgavatar = view.findViewById(R.id.img_avatar_dong_bv);
            hoder.imghinh = view.findViewById(R.id.img_anh_dong_bv);
            hoder.txtten = view.findViewById(R.id.txt_ten_dong_bv);
            hoder.txttg = view.findViewById(R.id.txt_tg_bv);
            hoder.txtsllike = view.findViewById(R.id.txt_sllike);
            hoder.txtslbl = view.findViewById(R.id.txt_slbl);
            hoder.txtnoidung = view.findViewById(R.id.txt_nd_dong_bv);
            hoder.imgmenu = view.findViewById(R.id.menu_bv);
            hoder.imgbl = view.findViewById(R.id.img_binh_luan);
            hoder.imglike = view.findViewById(R.id.img_like);
            view.setTag(hoder);
        } else {
            hoder = (BaiVietAdapter.ViewHoder) view.getTag();
        }
        BaiViet baiViet = baiVietList.get(i);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait ...");
        hoder.imgavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TrangCaNhan.class);
                intent.putExtra("mand", baiViet.getMand());
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(ApiService.BASE_URL + baiViet.getHinhanh().substring(23, baiViet.getHinhanh().length())).into(hoder.imghinh);
        if (baiViet.getHinhanh().trim().equals("")) {
            hoder.imghinh.setVisibility(View.GONE);
        }
        int mand = baiViet.getMand();
        Call<NguoiDung> call = apiService.getNguoidungsbl(mand);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.body() != null) {
                    hoder.txtten.setText(response.body().getHoten().toString().trim());
                    if (response.body().getHinhanh() == null) {
                        hoder.imgavatar.setImageResource(R.drawable.spcr);
                    } else {
                        Picasso.with(context).load(ApiService.BASE_URL + response.body().getHinhanh().substring(23, response.body().getHinhanh().length())).into(hoder.imgavatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
        getLike(hoder.imglike,baiViet.getMabv());
        hoder.txtsllike.setText(baiViet.getSoluonglike() + "");
        hoder.txtslbl.setText(baiViet.getSoluongbl() + "");
        hoder.imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Call<ArrayList<Likes>> call = apiService.getTrangthai(baiViet.getMabv(),DangNhap.idnd);
                call.enqueue(new Callback<ArrayList<Likes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Likes>> call, Response<ArrayList<Likes>> response) {
                            if(response.body() != null && response.body().size() != 0){
                                int mabv = baiViet.getMabv();
                                Call<Integer> call1 = apiService.deleteLike(DangNhap.idnd, mabv);
                                call1.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (response.body() == 1) {
                                            getLike(hoder.imglike,baiViet.getMabv());
                                            hoder.txtsllike.setText(baiViet.getSoluonglike()+"");
                                            progressDialog.dismiss();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                    }
                                });
                            }else{
                                int mabv1 = baiViet.getMabv();
                                Call<Likes> call2 = apiService.postLike(DangNhap.idnd, mabv1);
                                call2.enqueue(new Callback<Likes>() {
                                    @Override
                                    public void onResponse(Call<Likes> call, Response<Likes> response) {
                                        if(response.body().getMabv() == baiViet.getMabv()) {
                                            getLike(hoder.imglike,baiViet.getMabv());
                                            hoder.txtsllike.setText(baiViet.getSoluonglike()+1+"");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Likes> call, Throwable t) {
                                    }
                                });
                            }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Likes>> call, Throwable t) {
                    }
                });
                }

            });

        hoder.txtten.setText(baiViet.getTennd());
        hoder.txtnoidung.setText(baiViet.getNoidung());
        long tgiantv = 0;
        long tghientai = 0;
        long tgianbaiviet = 0;
        String tgian = "";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            tghientai = System.currentTimeMillis();
            tgian = format.format(baiViet.getThoigian());
            tgianbaiviet = format.parse(tgian.trim()).getTime();
        } catch (ParseException e) {
        }
        tgiantv = tghientai - tgianbaiviet;
        if (TimeUnit.MILLISECONDS.toMinutes(tgiantv) <= 60) {
            hoder.txttg.setText(TimeUnit.MILLISECONDS.toMinutes(tgiantv) + " phút trước");
        } else if (TimeUnit.MILLISECONDS.toHours(tgiantv) <= 24) {
            hoder.txttg.setText(TimeUnit.MILLISECONDS.toHours(tgiantv) + " giờ trước");
        } else if (TimeUnit.MILLISECONDS.toDays(tgiantv) >= 1) {
            hoder.txttg.setText(format.format(baiViet.getThoigian()).substring(0, 10));
        }
        if (baiViet.getMand() == DangNhap.idnd) {
            hoder.imgmenu.setVisibility(View.VISIBLE);
            hoder.imgmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupMenu = new PopupMenu(context, view);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.menu_bai_viet, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_sua_bv:
                                    int mabvsua = baiViet.getMabv();
                                    suaBaiviet( mabvsua, baiViet);
                                    break;
                                case R.id.menu_xoa_bv:
                                    progressDialog.show();
                                    int mabv = baiViet.getMabv();
                                    deleteBaiviet( mabv, baiViet);
                                    break;
                            }
                            return false;
                        }
                    });
                }
            });
        } else {
            hoder.imgmenu.setVisibility(View.INVISIBLE);
        }
        hoder.imgbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewGroup.getContext(), BinhLuan.class);
                intent.putExtra("mabv", baiViet.getMabv());
                viewGroup.getContext().startActivity(intent);

            }
        });
        return view;
    }

    private void getLike(ImageView imglike, int mabv) {
        imglike.setImageResource(R.drawable.ic_like);
        Call<ArrayList<Likes>> call1 = apiService.getLike(DangNhap.idnd);
        call1.enqueue(new Callback<ArrayList<Likes>>() {
            @Override
            public void onResponse(Call<ArrayList<Likes>> call, Response<ArrayList<Likes>> response) {
                List<Likes> likes = response.body();
                if (likes != null) {
                    for (Likes likes1 : likes) {
                        if (likes1.isTrangthai() == true && likes1.getMabv() == mabv) {
                            imglike.setImageResource(R.drawable.ic_liked);
                            progressDialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Likes>> call, Throwable t) {

            }
        });
    }


    private void suaBaiviet( int mabv, BaiViet baiViet) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sua_bai_viet);
        Button btnsua = dialog.findViewById(R.id.btnsuabv);
        Button btnhuy = dialog.findViewById(R.id.btnhuysuabv);
        EditText edtnd = dialog.findViewById(R.id.edt_noi_dung_suabv);
        dialog.show();
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String nd = edtnd.getText().toString().trim();
                Call<BaiViet> call = apiService.putBaiviets(mabv, nd);
                call.enqueue(new Callback<BaiViet>() {
                    @Override
                    public void onResponse(Call<BaiViet> call, Response<BaiViet> response) {
                        if (response.body() != null) {
                            Toast.makeText(context, "Cập nhật bài viết thành công", Toast.LENGTH_SHORT).show();
                            baiViet.setNoidung(response.body().getNoidung());
                            notifyDataSetChanged();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaiViet> call, Throwable t) {

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

    private void deleteBaiviet( int mabv, BaiViet baiViet) {
        Call<Integer> call = apiService.deleteBaiviet(mabv);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() == 1 && response.body() != null) {
                    Toast.makeText(context, "Xoá bài viết thành công", Toast.LENGTH_SHORT).show();
                    baiVietList.remove(baiViet);
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
