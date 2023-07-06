package com.example.mangxahoigump.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mangxahoigump.API.ApiService;
import com.example.mangxahoigump.Controller.DSTinNhanNhom;
import com.example.mangxahoigump.Controller.DangNhap;
import com.example.mangxahoigump.Controller.TinTuc;
import com.example.mangxahoigump.Controller.TrangCaNhan;
import com.example.mangxahoigump.Controller.XemThoiTiet;
import com.example.mangxahoigump.Controller.XemViTri;
import com.example.mangxahoigump.MainActivity;
import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuFragment extends Fragment {
    LinearLayout lntcn;
    CircleImageView imgavatar;
    TextView txtten;
    View view;
    private MainActivity mainActivity;
    CardView cvvitri, cvthoitiet,cvnhom,cvtintuc;
    Button btndangxuat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        init();

        mainActivity = (MainActivity) getActivity();
        if (DangNhap.hinh != null) {
            Picasso.with(getActivity()).load(ApiService.BASE_URL + DangNhap.hinh.substring(23, DangNhap.hinh.length())).into(imgavatar);
        } else {
            imgavatar.setImageResource(R.drawable.ic_avatar_default);
        }
        txtten.setText(DangNhap.tennd);
        lntcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentTcn();
            }
        });
        cvvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentVitri();
            }
        });
        cvthoitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentThoitiet();
            }
        });
        cvnhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentNhom();
            }
        });
        cvtintuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentTintuc();
            }
        });
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentDangxuat();
            }
        });
        return view;
    }

    private void intentTintuc() {
        Intent intent = new Intent(getActivity(), TinTuc.class);
        startActivity(intent);
    }

    private void intentNhom() {
        Intent intent = new Intent(getActivity(), DSTinNhanNhom.class);
        startActivity(intent);
    }

    private void intentThoitiet() {
        Intent intent = new Intent(getActivity(), XemThoiTiet.class);
        startActivity(intent);
    }

    private void intentDangxuat() {
        Intent intent = new Intent(getActivity(),DangNhap.class);
        startActivity(intent);
    }

    private void intentVitri() {
        Intent intent = new Intent(getActivity(), XemViTri.class);
        startActivity(intent);
    }

    private void intentTcn() {
        Intent intent = new Intent(getActivity(), TrangCaNhan.class);
        intent.putExtra("mand",DangNhap.idnd);
        startActivity(intent);
    }

    private void init() {
        lntcn = (LinearLayout) view.findViewById(R.id.ln_xem_trang_ca_nhan);
        imgavatar = view.findViewById(R.id.img_avatar_menu);
        txtten = view.findViewById(R.id.txt_ten_menu);
        cvvitri = view.findViewById(R.id.cv1);
        cvthoitiet = view.findViewById(R.id.cv2);
        cvnhom = view.findViewById(R.id.cv3);
        cvtintuc = view.findViewById(R.id.cv4);
        btndangxuat = view.findViewById(R.id.btn_dang_xuat);
    }
}
