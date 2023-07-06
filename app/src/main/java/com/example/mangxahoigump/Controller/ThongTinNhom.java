package com.example.mangxahoigump.Controller;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.DSTinNhanNhomAdapter;
import com.example.mangxahoigump.Adapter.ThanhVienNhomAdapter;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinNhom extends AppCompatActivity {
    ImageView imgavatar,imgback,imgthemtv,imgcnnhom;
    TextView txtten,txtmota,txtsl;
    ListView lvtv;
    Button btnroinhom;
    ThanhVienNhomAdapter adapter;
    ProgressDialog progressDialog;
    List<NguoiDung> nguoiDungList = new ArrayList<>();
    ApiService apiService = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thong_tin_nhom);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        Nhom nhom = DSTinNhanNhomAdapter.nhom1;
        txtten.setText(nhom.getTennhom().trim());
        txtmota.setText(nhom.getMota().trim());
        getTvnhom(nhom.getManhom());
        
        if(nhom.getHinhanh().equals("")){
            imgavatar.setImageResource(R.drawable.spcr);
        }else{
            Picasso.with(this).load(ApiService.BASE_URL+nhom.getHinhanh().substring(23,nhom.getHinhanh().length())).into(imgavatar);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentQuaylai();
            }
        });
        imgthemtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTinNhom.this,ThemTVNhom.class);
                startActivity(intent);
            }
        });
        btnroinhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaTVnhom();
            }
        });
        lvtv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int mand = nguoiDungList.get(i).getMand();
                Intent intent = new Intent(ThongTinNhom.this,TrangCaNhan.class);
                intent.putExtra("mand",mand);
                startActivity(intent);
            }
        });
        imgcnnhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaNhom();
            }
        });
    }

    private void suaNhom() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cap_nhat_nhom);
        EditText edtten = dialog.findViewById(R.id.edt_sua_ten_nhom);
        EditText edtmota = dialog.findViewById(R.id.edt_sua_mt_nhom);
        Button btnsua = dialog.findViewById(R.id.btnsuanhom);
        Button btnhuy = dialog.findViewById(R.id.btnhuysuanhom);
        dialog.show();
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                int manhom = DSTinNhanNhomAdapter.nhom1.getManhom();
                String ten = edtten.getText().toString().trim();
                String mota = edtmota.getText().toString().trim();
                Call<Nhom> call = apiService.putNhom(manhom,ten,mota);
                call.enqueue(new Callback<Nhom>() {
                    @Override
                    public void onResponse(Call<Nhom> call, Response<Nhom> response) {
                        if(response.body() != null){
                            txtten.setText(ten);
                            txtmota.setText(mota);
                            Toast.makeText(ThongTinNhom.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Nhom> call, Throwable t) {

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

    private void xoaTVnhom() {
        int manhom = DSTinNhanNhomAdapter.nhom1.getManhom();
        Call<Integer> call = apiService.deleteTvnhom(manhom,DangNhap.idnd);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                    Toast.makeText(ThongTinNhom.this, "Rời nhóm thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThongTinNhom.this,DSTinNhanNhom.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void getTvnhom(int manhom) {
        Call<ArrayList<NguoiDung>> call = apiService.getTvnhom(manhom);
        call.enqueue(new Callback<ArrayList<NguoiDung>>() {
            @Override
            public void onResponse(Call<ArrayList<NguoiDung>> call, Response<ArrayList<NguoiDung>> response) {
                nguoiDungList = response.body();
                if(nguoiDungList != null){
                    adapter = new ThanhVienNhomAdapter(ThongTinNhom.this,R.layout.dong_thanh_vien_nhom,nguoiDungList);
                    lvtv.setAdapter(adapter);
                }
                txtsl.setText(nguoiDungList.size()+"");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<NguoiDung>> call, Throwable t) {

            }
        });
    }

    private void intentQuaylai() {
        Intent intent = new Intent(ThongTinNhom.this,NhanTinNhom.class);
        startActivity(intent);
    }
    
    private void init(){
        imgavatar = findViewById(R.id.img_avatar_nhom);
        imgback = findViewById(R.id.img_quay_lai_ttnhom);
        imgthemtv = findViewById(R.id.them_thanh_vien);
        txtten = findViewById(R.id.txt_ten_nhom);
        txtmota = findViewById(R.id.txt_mota_nhom);
        txtsl = findViewById(R.id.txt_sl);
        lvtv = findViewById(R.id.lv_thanh_vien);
        btnroinhom = findViewById(R.id.btn_roi_nhom);
        imgcnnhom = findViewById(R.id.img_cn_nhom);
    }
}
