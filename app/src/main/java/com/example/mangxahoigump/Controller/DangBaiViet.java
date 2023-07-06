package com.example.mangxahoigump.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.ThongBao;
import com.example.mangxahoigump.R;
import com.example.mangxahoigump.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangBaiViet extends AppCompatActivity {
    Button btndang;
    private int MY_REQUEST_CODE = 2;
    ImageView imgquaylai, imgthemanh, imganh;
    EditText edtdangbv;
    private Uri mUri;
    private ProgressDialog progressDialog;
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
                            imganh.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_bai_viet);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ....");
        imgquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentQuaylai();
            }
        });
        btndang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentDang(ApiService.BASE_URL);
            }
        });
        imgthemanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentThemanh();
            }
        });
    }

    private void intentThemanh() {
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intentActivityResultLauncher.launch(Intent.createChooser(intent, "SELECT PICTURE"));
    }

    private void intentDang(String baseurl) {
        int mand1 = DangNhap.idnd;
        String nd = edtdangbv.getText().toString().trim();
        String tennd1 = DangNhap.tennd.toString().trim();
        DateFormat format =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String tgianht = format.format(Calendar.getInstance().getTime());
        String chedo1= "1";
        String realpath="";
        if(mUri == null){
            realpath="";
        }else{
            realpath = RealPathUtil.getRealPath(this, mUri);
        }
        File file = new File(realpath);
        RequestBody mand = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mand1));
        RequestBody tennd = RequestBody.create(MediaType.parse("multipart/form-data"), tennd1);
        RequestBody hinh = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody noidung = RequestBody.create(MediaType.parse("multipart/form-data"), nd);
        RequestBody thoigian = RequestBody.create(MediaType.parse("multipart/form-data"), tgianht);
        RequestBody chedo = RequestBody.create(MediaType.parse("multipart/form-data"), chedo1);
        MultipartBody.Part mparthinh = MultipartBody.Part.createFormData("", file.getName(), hinh);
        if(nd.equals("") ){
            Toast.makeText(this, "Hãy thêm nội dung cho bài viết!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!realpath.equals("")) {
                progressDialog.show();
                ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
                Call<Integer> call = apiservice.postBaiViet(mand, tennd, mparthinh, noidung, thoigian, chedo);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.body() == 1) {
                            progressDialog.dismiss();
                            Toast.makeText(DangBaiViet.this, "Đăng bài viết thành công!", Toast.LENGTH_SHORT).show();
                            postThongbao(ApiService.BASE_URL, nd, tgianht);
                            intentMain();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }else{
                progressDialog.show();
                ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
                Call<Integer> call = apiservice.postBaiViet1(mand,tennd,noidung,thoigian, chedo);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.body() == 1) {
                            progressDialog.dismiss();
                            Toast.makeText(DangBaiViet.this, "Đăng bài viết thành công!", Toast.LENGTH_SHORT).show();
                            postThongbao(ApiService.BASE_URL, nd, tgianht);
                            intentMain();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    private void postThongbao(String baseurl,String noidung,String tgianht) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ThongBao> call = apiservice.postthongbao(DangNhap.idnd,noidung,tgianht);
        call.enqueue(new Callback<ThongBao>() {
            @Override
            public void onResponse(Call<ThongBao> call, Response<ThongBao> response) {
                if(response.body() != null){
                }
            }

            @Override
            public void onFailure(Call<ThongBao> call, Throwable t) {
            }
        });
    }
    private void intentMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void intentQuaylai() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

    private void init() {
        edtdangbv = findViewById(R.id.edt_dang_bv);
        imgquaylai = findViewById(R.id.img_quay_lai_bv);
        imgthemanh = findViewById(R.id.img_them_anh_bv);
        imganh = findViewById(R.id.img_anh_bv);
        btndang = findViewById(R.id.btn_dang_bv);
    }
}
