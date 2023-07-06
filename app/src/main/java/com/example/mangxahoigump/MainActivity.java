package com.example.mangxahoigump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mangxahoigump.Controller.BinhLuan;
import com.example.mangxahoigump.Controller.DSBanBe;
import com.example.mangxahoigump.Controller.DSTinNhan;
import com.example.mangxahoigump.Controller.NhanTin;
import com.example.mangxahoigump.Controller.TimKiemBanBe;
import com.example.mangxahoigump.Fragment.HomeFragment;
import com.example.mangxahoigump.Fragment.MenuFragment;
import com.example.mangxahoigump.Fragment.PagerViewAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment = new HomeFragment();
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    EditText edttimkiem;
    private PagerViewAdapter pagerViewAdapter;
    private String mand,tennd,hinh;
    private ImageView imgtinhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, homeFragment).commit();
        init();
        imgtinhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentTinnhan();
            }
        });
        edttimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimKiemBanBe.class);
                startActivity(intent);
            }
        });
        guiDlfragmentHome();
        viewPager.setSaveEnabled(false);
        pagerViewAdapter = new PagerViewAdapter(this);
        viewPager.setAdapter(pagerViewAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_home);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_friend);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_notification);
                    break;
                case 3:
                    tab.setIcon(R.drawable.ic_menu);
                    break;
            }
        }).attach();
    }

    private void intentTinnhan() {
        Intent intent = new Intent(MainActivity.this, DSTinNhan.class);
        startActivity(intent);
    }


    private void guiDlfragmentHome() {
        Intent intent = getIntent();
        mand = intent.getStringExtra("mand");
        tennd = intent.getStringExtra("tennd");
        hinh = intent.getStringExtra("hinh");
    }

    private void init() {
        edttimkiem = findViewById(R.id.edttimkiem);
        imgtinhan = findViewById(R.id.img_tin_nhan);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
    }

    public String getMand() {
        return mand;
    }

    public void setMand(String mand) {
        this.mand = mand;
    }

    public String getTennd() {
        return tennd;
    }

    public void setTennd(String tennd) {
        this.tennd = tennd;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}