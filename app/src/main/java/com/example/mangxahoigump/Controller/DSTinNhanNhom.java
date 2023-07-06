package com.example.mangxahoigump.Controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.API.RetrofitClient;
import com.example.mangxahoigump.Adapter.DSTinNhanAdapter;
import com.example.mangxahoigump.Adapter.DSTinNhanNhomAdapter;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.R;
import com.example.mangxahoigump.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DSTinNhanNhom extends AppCompatActivity {
    RecyclerView rcv;
    DSTinNhanNhomAdapter adapter;
    ImageView imgback, imgtaonhom, imganh;
    private int MY_REQUEST_CODE = 3;
    private Uri mUri;
    EditText edttimkiem;
    ArrayList<Nhom> nhomList = new ArrayList<>();
    ProgressDialog progressDialog;
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
        setContentView(R.layout.layout_ds_tin_nhan_nhom);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        getNhomnd(ApiService.BASE_URL);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv.addItemDecoration(itemDecoration);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DSTinNhanNhom.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imgtaonhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTaonhom();
            }
        });
        edttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<Nhom> filteredList = new ArrayList<>();
        for (Nhom n : nhomList) {
            if (n.getTennhom().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(n);
            }
        }
        adapter.filterList(filteredList);
    }


    private void dialogTaonhom() {
        Dialog dialog = new Dialog(DSTinNhanNhom.this);
        dialog.setContentView(R.layout.dialog_tao_nhom);
        EditText edtten = dialog.findViewById(R.id.edt_ten_tao_nhom);
        EditText edtmota = dialog.findViewById(R.id.edt_mo_ta_tao_nhom);
        Button btntao = dialog.findViewById(R.id.btntaonhom);
        Button btnhuy = dialog.findViewById(R.id.btnhuytaonhom);
        imganh = dialog.findViewById(R.id.img_dialog_tao_nhom);
        dialog.show();
        imganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentThemanh();
            }
        });
        btntao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String tentn = edtten.getText().toString().trim();
                String motatn = edtmota.getText().toString().trim();
                if (tentn.equals("") || motatn.equals("")) {
                    Toast.makeText(DSTinNhanNhom.this, "Hãy nhập thông tin cho nhóm!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    taoNhom(ApiService.BASE_URL, tentn, motatn, dialog);
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void taoNhom(String baseurl, String tentn, String motatn, Dialog dialog) {
        String realpath = "";
        if (mUri == null) {
            realpath = "";
        } else {
            realpath = RealPathUtil.getRealPath(this, mUri);
        }
        File file = new File(realpath);
        RequestBody tennhom = RequestBody.create(MediaType.parse("multipart/form-data"), tentn);
        RequestBody mota = RequestBody.create(MediaType.parse("multipart/form-data"), motatn);
        RequestBody hinh = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part mparthinh = MultipartBody.Part.createFormData("", file.getName(), hinh);
        if (realpath.equals("")) {
            Toast.makeText(this, "Hãy thêm hình cho nhóm!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
            Call<Nhom> call = apiService.postNhom(tennhom, mota, mparthinh);
            call.enqueue(new Callback<Nhom>() {
                @Override
                public void onResponse(Call<Nhom> call, Response<Nhom> response) {
                    if (response.body() != null) {
                        int manhom = response.body().getManhom();
                        Toast.makeText(DSTinNhanNhom.this, "Tạo nhóm thành công", Toast.LENGTH_SHORT).show();
                        postNhomnd(ApiService.BASE_URL, DangNhap.idnd, manhom);
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Nhom> call, Throwable t) {

                }
            });
        }
    }

    private void postNhomnd(String baseurl, int idnd, int manhom) {
        ApiService apiService = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiService.postNhomnd(manhom, idnd);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                    getNhomnd(ApiService.BASE_URL);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

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
        rcv = findViewById(R.id.rcv_ds_tin_nhan_nhom);
        imgback = findViewById(R.id.img_quay_lai_dstn_nhom);
        imgtaonhom = findViewById(R.id.img_tao_nhom);
        edttimkiem = findViewById(R.id.edt_tk_nhom);

    }

    private void getNhomnd(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<Nhom>> call = apiservice.getNhombymand(DangNhap.idnd);
        call.enqueue(new Callback<ArrayList<Nhom>>() {
            @Override
            public void onResponse(Call<ArrayList<Nhom>> call, Response<ArrayList<Nhom>> response) {
                if (response.body() != null) {
                    nhomList = response.body();
                    adapter = new DSTinNhanNhomAdapter(DSTinNhanNhom.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DSTinNhanNhom.this, RecyclerView.VERTICAL, false);
                    rcv.setLayoutManager(linearLayoutManager);
                    adapter.setData(nhomList);
                    rcv.setAdapter(adapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Nhom>> call, Throwable t) {

            }
        });
    }
}
