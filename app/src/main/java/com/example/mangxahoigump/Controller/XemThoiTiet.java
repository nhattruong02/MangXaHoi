package com.example.mangxahoigump.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mangxahoigump.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class XemThoiTiet extends AppCompatActivity {
    String theurl = "https://api.openweathermap.org/data/2.5/forecast?q=hung%20yen&appid=66701fb179c870fca12c545b30118b5a";
    TextView  txtdoam, txtmay, txtgio, txtnhietdo, txttt;
    EditText edttk;
    ImageView imghinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xem_thoi_tiet);
        edttk = findViewById(R.id.edttimkiem);
        txtnhietdo = findViewById(R.id.txt_nhiet_do);
        txtdoam = findViewById(R.id.txt_do_am);
        txtgio = findViewById(R.id.txt_gio);
        txtmay = findViewById(R.id.txt_may);
        txttt = findViewById(R.id.txt_trang_thai);
        imghinh = findViewById(R.id.img_hinh);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute(theurl);
            }
        });

    }

    private void ParseContent(String content) {
        String s = "";
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            JSONObject jsonObject2 = jsonObject1.getJSONObject("main");
            String temp = jsonObject2.getString("temp");
            int doam = jsonObject2.getInt("humidity");
            JSONObject jsonObject3 = jsonObject1.getJSONObject("clouds");
            String may = jsonObject3.getString("all");
            JSONObject jsonObject4 = jsonObject1.getJSONObject("wind");
            String gio = jsonObject4.getString("speed");
            JSONArray jsonArray1 = jsonObject1.getJSONArray("weather");
            JSONObject jsonObject6 = jsonArray1.getJSONObject(0);
            String mota = jsonObject6.getString("description");
            String hinh = jsonObject6.getString("icon");
            txtnhietdo.setText("Nhiệt độ:" + temp.substring(0,2) +"°C");
            txtmay.setText(may +"%");
            txtdoam.setText(doam + "%");
            txtgio.setText(gio+"m/s");
            txttt.setText(mota);
            Picasso.with(XemThoiTiet.this).load("https://openweathermap.org/img/wn/"+hinh+".png").into(imghinh);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class ReadData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            ParseContent(s);
            super.onPostExecute(s);
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            // đạo tối tượng URL
            URL url = new URL(theUrl);
            // Tạo 1 kết nối
            URLConnection urlConnection = url.openConnection();

            // đưa url vừa nhận vào 1 bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // đọc nội dung trong đường dẫn url nhận được
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

