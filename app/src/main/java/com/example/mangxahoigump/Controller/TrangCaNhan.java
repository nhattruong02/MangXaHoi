package com.example.mangxahoigump.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.BaiVietAdapter;
import com.example.mangxahoigump.CheckInternet;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.R;
import com.example.mangxahoigump.RealPathUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangCaNhan extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 2;
    CircleImageView avatar;
    private Uri mUri;
    ImageView imgcn,imgquaylai,imgxemtt;
    private ProgressDialog progressDialog;
    Dialog dialog;
    ArrayList<BaiViet> listbvtcanhan;
    BaiVietAdapter adapter;
    ListView lvbvtcanhan;
    TextView txttentcn, txtmotatcn;
    NguoiDung nguoiDung;
    DatePickerDialog.OnDateSetListener dateOnClickListener;
    private ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            avatar.setImageBitmap(bitmap);
                            xacnhancn();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trang_ca_nhan);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        Intent intent = getIntent();
        int mand = intent.getIntExtra("mand",0);
        if (mand == DangNhap.idnd) {
            getNguoidung(ApiService.BASE_URL, DangNhap.idnd);
            getBvtcanhan(ApiService.BASE_URL, DangNhap.idnd);
            imgcn.setVisibility(View.VISIBLE);
        }else{
            getBvtcanhan(ApiService.BASE_URL, mand);
            getNguoidung(ApiService.BASE_URL, mand);
            imgcn.setVisibility(View.INVISIBLE);
        }
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        imgcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnThongtin(ApiService.BASE_URL);
            }
        });
        imgquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentQuaylai();
            }
        });
        imgxemtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xemThongtin();
            }
        });
    }

    private void xemThongtin() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_xem_thong_tin);
        TextView tvgtcn = dialog.findViewById(R.id.edt_gtcn);
        TextView tvnscn = dialog.findViewById(R.id.edt_nscn);
        TextView tvsdtcn = dialog.findViewById(R.id.edt_sdtcn);
        String gt = nguoiDung.getGioitinh().trim();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String ns = format.format(nguoiDung.getNamsinh());
        String sdt = nguoiDung.getSdt();
        tvgtcn.setText(gt);
        tvnscn.setText(ns);
        tvsdtcn.setText(sdt);
        dialog.show();
    }

    private void intentQuaylai() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getNguoidung(String baseurl, int mand) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<NguoiDung> call = apiService.getNguoidungsbl(mand);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                nguoiDung = response.body();
                if (nguoiDung.getHinhanh() == null) {
                    avatar.setImageResource(R.drawable.spcr);
                } else {
                    Picasso.with(TrangCaNhan.this).load(ApiService.BASE_URL + nguoiDung.getHinhanh().substring(23, nguoiDung.getHinhanh().length())).into(avatar);
                }
                txttentcn.setText(nguoiDung.getHoten().trim());
                if(nguoiDung.getMota() == null){
                    txtmotatcn.setText("");
                }
                else{
                    txtmotatcn.setText(nguoiDung.getMota().trim());
                }
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {

            }
        });
    }

    private void cnThongtin(String url) {
        dialog = new Dialog(TrangCaNhan.this);
        dialog.setContentView(R.layout.dialog_cap_nhat_thong_tin_cn);
        Button btncn = dialog.findViewById(R.id.btncapnhat);
        Button btnhuycn = dialog.findViewById(R.id.btnhuycapnhat);
        EditText edthoten = dialog.findViewById(R.id.edt_ht_cntt);
        EditText edtgt = dialog.findViewById(R.id.edt_gt_cntt);
        EditText edtsdt = dialog.findViewById(R.id.edt_sdt_cntt);
        EditText edtmota = dialog.findViewById(R.id.edt_mo_ta_cntt);
        EditText edtns = dialog.findViewById(R.id.edt_ns_cntt);
        String gt = nguoiDung.getGioitinh();
        String sdt = nguoiDung.getSdt();
        edtgt.setText(gt);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String ns = format.format(nguoiDung.getNamsinh());
        edtns.setText(ns);
        edtsdt.setText(sdt);
        edthoten.setText(DangNhap.tennd);
        edtmota.setText(txtmotatcn.getText().toString().trim());
        edtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int nam = cal.get(Calendar.YEAR);
                int thang = cal.get(Calendar.MONTH);
                int ngay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        TrangCaNhan.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, dateOnClickListener, nam, thang, ngay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateOnClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                thang += 1;
                String date = ngay + "/" + thang + "/" + nam;
                edtns.setText(date);
            }
        };
        dialog.show();
        btncn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String hoten = edthoten.getText().toString().trim();
                String gt = edtgt.getText().toString().trim();
                String sdt = edtsdt.getText().toString().trim();
                String mota = edtmota.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("dd/m/yyyy");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
                String ns = "";
                try {
                    ns = format1.format(format.parse(edtns.getText().toString().trim()))+"";
                } catch (ParseException e) {
                }
                if (hoten.equals("") || gt.equals("") || sdt.equals("") || mota.equals("")) {
                    Toast.makeText(TrangCaNhan.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    ApiService apiService = RetrofitClient.getClient(url).create(ApiService.class);
                    Call<NguoiDung> call = apiService.putThongtincn(DangNhap.idnd, hoten, gt, sdt, mota, ns);
                    call.enqueue(new Callback<NguoiDung>() {
                        @Override
                        public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                            NguoiDung nguoiDung = response.body();
                            if (nguoiDung != null) {
                                Log.e("111123",nguoiDung+"");
                                txttentcn.setText(nguoiDung.getHoten());
                                txtmotatcn.setText(nguoiDung.getMota());
                                Toast.makeText(TrangCaNhan.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<NguoiDung> call, Throwable t) {
                            Log.e("111122",t+"");
                        }
                    });
                }
            }
        });
        btnhuycn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void getBvtcanhan(String baseurl, int mand) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<BaiViet>> call = apiservice.getBaivietsbymand(mand);
        call.enqueue(new Callback<ArrayList<BaiViet>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiViet>> call, Response<ArrayList<BaiViet>> response) {
                listbvtcanhan = response.body();
                if (listbvtcanhan != null) {
                    adapter = new BaiVietAdapter(TrangCaNhan.this, R.layout.dong_bai_viet, listbvtcanhan);
                    lvbvtcanhan.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BaiViet>> call, Throwable t) {
            }
        });
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intentActivityResultLauncher.launch(Intent.createChooser(intent, "SELECT PICTURE"));
    }

    private void xacnhancn() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cap_nhat_avatar);
        Button btncapnhat = dialog.findViewById(R.id.btn_dong_y_avatar);
        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri != null) {
                    if (CheckInternet.checkI(TrangCaNhan.this)) {
                        callApiuploadavatar(ApiService.BASE_URL);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(TrangCaNhan.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();

    }

    private void callApiuploadavatar(String baseurl) {
        progressDialog.show();
        int mand = DangNhap.idnd;
        String realpath = RealPathUtil.getRealPath(this, mUri);
        File file = new File(realpath);
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mand));
        RequestBody requestBodyavatar = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part mpartavatar = MultipartBody.Part.createFormData("", file.getName(), requestBodyavatar);
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<NguoiDung> call = apiservice.updateAvatar(id, mpartavatar);
        call.enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void init() {
        imgquaylai = findViewById(R.id.img_quay_lai_tcn);
        imgxemtt = findViewById(R.id.img_xem_thong_tin);
        txttentcn = findViewById(R.id.txtten_tcn);
        txtmotatcn = findViewById(R.id.txtmota_tcn);
        avatar = findViewById(R.id.civ_avatar);
        lvbvtcanhan = findViewById(R.id.lv_trang_ca_nhan);
        imgcn = findViewById(R.id.img_cn_thong_tin);
    }
}
