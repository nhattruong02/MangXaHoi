package com.example.mangxahoigump.Controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.Adapter.TinNhanAdapter;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.TinNhan;
import com.example.mangxahoigump.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanTin extends AppCompatActivity {
    ImageView imgguitin,imgback,imgmenu;
    CircleImageView imghinh;
    TextView txtten;
    EditText edtsoantin;
    RecyclerView rcv;
    TinNhanAdapter adapter;
    int mannhan;
    ArrayList<TinNhan> tinNhanlist = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tin_nhan);
        init();
        Intent intent = getIntent();
        int kt =intent.getIntExtra("tinnhan",0);
        if(kt == 1){
            imgmenu.setVisibility(View.INVISIBLE);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        Bundle bundle = getIntent().getExtras();
        NguoiDung nguoiDung = (NguoiDung) bundle.get("nd");
        mannhan = nguoiDung.getMand();
        txtten.setText(nguoiDung.getHoten());
        if(nguoiDung.getHinhanh().equals("")){
            imghinh.setImageResource(R.drawable.spcr);
        }else{
            Picasso.with(this).load(ApiService.BASE_URL+nguoiDung.getHinhanh().substring(23,nguoiDung.getHinhanh().length())).into(imghinh);
        }
        gettinnhan(mannhan);
        imgguitin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtsoantin.getText().toString().trim().equals("")){
                    Toast.makeText(NhanTin.this, "Hãy nhập nội dung tin nhắn", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    posttinnhan(mannhan);
                }
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhanTin.this,DSTinNhan.class);
                startActivity(intent);
            }
        });
    }
    private void gettinnhan(int mannhan) {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("tinnhan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tinNhanlist.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren() ){
                    TinNhan tinNhan = snapshot1.getValue(TinNhan.class);
                    if(tinNhan.getMand() == DangNhap.idnd && tinNhan.getMannhan() == mannhan
                        || tinNhan.getMand() == mannhan && tinNhan.getMannhan() == DangNhap.idnd){
                        tinNhanlist.add(tinNhan);
                    }
                    adapter = new TinNhanAdapter(NhanTin.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NhanTin.this, RecyclerView.VERTICAL, false);
                    rcv.setLayoutManager(linearLayoutManager);
                    adapter.setData(tinNhanlist);
                    rcv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void posttinnhan(int mannhan){
        String noidung = edtsoantin.getText().toString().trim();
        int thoigian = (int) Calendar.getInstance().getTimeInMillis();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("mand",DangNhap.idnd);
        hashMap.put("mannhan",mannhan);
        hashMap.put("noidung",noidung);
        hashMap.put("thoigian",thoigian);
        database.child("tinnhan").push().setValue(hashMap);
        edtsoantin.setText("");
        progressDialog.dismiss();
    }
    private void init(){
        imghinh = findViewById(R.id.img_tin_nhan);
        imgguitin = findViewById(R.id.img_gui_tin_nhan);
        imgback = findViewById(R.id.img_quay_lai_tn);
        txtten = findViewById(R.id.txt_ten_tn);
        edtsoantin = findViewById(R.id.edt_soan_tin_nhan);
        rcv = findViewById(R.id.rcv_ds_tin_nhan);
        imgmenu = findViewById(R.id.img_menu_nhom);;
    }
}
