package com.example.mangxahoigump.API;

import com.example.mangxahoigump.Model.BaiViet;
import com.example.mangxahoigump.Model.DongBinhLuan;
import com.example.mangxahoigump.Model.Likes;
import com.example.mangxahoigump.Model.NguoiDung;
import com.example.mangxahoigump.Model.Nhom;
import com.example.mangxahoigump.Model.ThongBao;
import com.example.mangxahoigump.Model.TinNhan;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    public static final String BASE_URL = "https://funmintpage79.conveyor.cloud/";
    @PUT("api/PUTMATKHAU/NguoiDungs")
    Call<NguoiDung> putMatkhau(@Query("tk") String tk,
                               @Query("mk") String mk);

    @GET("api/KIEMTRATK/NguoiDungs/{taikhoan}")
    Call<NguoiDung> kiemTratk(@Path("taikhoan") String taikhoan);

    @GET("api/GetNguoiDungs/NguoiDungs")
    Call<ArrayList<NguoiDung>> getNguoidungs();

    @GET("api/GetNguoiDung/NguoiDungs/{id}")
    Call<NguoiDung> getNguoidungsbl(@Path("id") int maInt);

    @POST("api/POSTNGUOIDUNG1/NguoiDungs")
    Call<NguoiDung> postNguoidung(@Query("tk") String tk,
                                  @Query("mk") String mk,
                                  @Query("hoten") String hoten,
                                  @Query("gt") String gt,
                                  @Query("ns") String ns,
                                  @Query("sdt") String sdt);

    @Multipart
    @PUT("api/PUTUploadFile/NguoiDungs")
    Call<NguoiDung> updateAvatar(@Part("mand") RequestBody mand,
                                 @Part("") MultipartBody.Part hinhanh);

    @Multipart
    @POST("api/POSTUploadFile/BaiViets")
    Call<Integer> postBaiViet(
            @Part("mand") RequestBody mand,
            @Part("tennd") RequestBody tennd,
            @Part("") MultipartBody.Part hinhanh,
            @Part("noidung") RequestBody noidung,
            @Part("thoigian") RequestBody thoigian,
            @Part("chedo") RequestBody chedo);

    @Multipart
    @POST("api/POSTUploadFile/BaiViets")
    Call<Integer> postBaiViet1(
            @Part("mand") RequestBody mand,
            @Part("tennd") RequestBody tennd,
            @Part("noidung") RequestBody noidung,
            @Part("thoigian") RequestBody thoigian,
            @Part("chedo") RequestBody chedo);

    @GET("api/GETALLBaiViets")
    Call<ArrayList<BaiViet>> getBaiviets();

    @PUT("api/PUTBAIVIET/BaiViets")
    Call<BaiViet> putBaiviets(@Query("mabv") int mabv,
                              @Query("nd") String nd);

    @GET("api/GETBaiViets/{mand}")
    Call<ArrayList<BaiViet>> getBaivietsbymand(@Path("mand") int mand);

    @GET("api/GETDSBANBE/NguoiDungs/{mand}")
    Call<ArrayList<NguoiDung>> getDSbanbe(@Path("mand") int mand);

    @GET("api/GETDSBANBECHOXN1/NguoiDungs/{mand}")
    Call<ArrayList<NguoiDung>> getDSbanbechoxn1(@Path("mand") int mand);

    @GET("api/GETDSBANBEDAKB/NguoiDungs/{mand}")
    Call<ArrayList<NguoiDung>> getDSbanbedakb(@Path("mand") int mand);

    @POST("api/POSTBANBECHOXN/BanBes")
    Call<Integer> postBanbechoxn(@Query("mand") int mand, @Query("mandbb") int mandbb);

    @DELETE("api/DELETEBANBECHOXN/BanBes")
    Call<Integer> deleteBanbechoxn(@Query("mand") int mand, @Query("mandbb") int mandbb);

    @PUT("api/PUTBANBECHOXN/BanBes")
    Call<Integer> putBanbechoxn(@Query("mand") int mand, @Query("mandbb") int mandbb);


    @PUT("api/PUTTHONGTINND/NguoiDungs")
    Call<NguoiDung> putThongtincn(@Query("mand") int mand,
                                  @Query("hoten") String hoten,
                                  @Query("gioitinh") String gioitinh,
                                  @Query("sdt") String sdt,
                                  @Query("mota") String mota,
                                  @Query("ns") String ns);


    @GET("api/GETDONGBLBYMABV/DongBinhLuans/{mabv}")
    Call<ArrayList<DongBinhLuan>> getDongbl(@Path("mabv") int mabv);

    @DELETE("api/DELETEBAIVIET/BaiViets/{mabv}")
    Call<Integer> deleteBaiviet(@Path("mabv") int mabv);

    @POST("api/POSTDONGBL/DongBinhLuans")
    Call<DongBinhLuan> postDongbl(@Query("mabv") int mabv,
                                  @Query("mand") int mand,
                                  @Query("noidung") String noidung,
                                  @Query("tg") String tg,
                                  @Query("hinh") String hinh);

    @Multipart
    @POST("api/POSTUploadFileAsync/Nhoms")
    Call<Nhom> postNhom(@Part("tennhom") RequestBody tennhom,
                        @Part("mota") RequestBody mota,
                        @Part("") MultipartBody.Part hinhanh);

    @POST("api/POSTNHOMND/NhomNguoiDungs")
    Call<Integer> postNhomnd(@Query("manhom") int manhom,
                             @Query("mand") int mand);

    @GET("api/GETNHOMBYMAND/Nhoms/{mand}")
    Call<ArrayList<Nhom>> getNhombymand(@Path("mand") int mand);

    @GET("api/GETThongbaosBYMAND")
    Call<ArrayList<ThongBao>> getthongbao(@Query("mand") int mand);

    @POST("api/POSTThongbaos")
    Call<ThongBao> postthongbao(@Query("mand") int mand,
                                @Query("noidung") String noidung,
                                @Query("thoigian") String thoigian);

    @PUT("api/PUTDONGBL/DongBinhLuans")
    Call<DongBinhLuan> putDongbinhluans(@Query("madbl") int madbl,
                                        @Query("nd") String nd);

    @DELETE("api/DELETEDONGBL/DongBinhLuans")
    Call<Integer> deleteDongbinhluans(@Query("madbl") int mabv);

    @GET("api/GETTHANHVIENNHOM/NguoiDungs")
    Call<ArrayList<NguoiDung>> getTvnhom(@Query("manhom") int manhom);

    @GET("api/TIMKIEMBANBE/NguoiDungs")
    Call<ArrayList<NguoiDung>> getDStkbanbe(@Query("mand") int mand,
                                            @Query("tttk") String tttk);

    @GET("api/THEMNDNHOM/NguoiDungs")
    Call<ArrayList<NguoiDung>> getDSthemndnhom(@Query("mand") int mand,
                                               @Query("manhom") int manhom);

    @POST("api/POSTTVNHOMND/NhomNguoiDungs")
    Call<Integer> postTvnhom(@Query("manhom") int manhom,
                             @Query("mand") int mand);

    @DELETE("api/DELETETVNHOM/NhomNguoiDungs")
    Call<Integer> deleteTvnhom(@Query("manhom") int manhom,
                               @Query("mand") int mand);

    @GET("api/GETLIKEBYMAND/Likes")
    Call<ArrayList<Likes>> getLike(@Query("mand") int mand);

    @POST("api/POSTLIKE1/Likes")
    Call<Likes> postLike(@Query("mand") int mand,
                         @Query("mabv") int mabv);

    @DELETE("api/DELETELIKED/Likes")
    Call<Integer> deleteLike(@Query("mand") int mand,
                             @Query("mabv") int mabv);

    @GET("api/GETTTBYMABV/Likes")
    Call<ArrayList<Likes>> getTrangthai(@Query("mabv") int mabv,
                             @Query("mand") int mand);

    @PUT("api/PUTHOM/Nhoms")
    Call<Nhom> putNhom(@Query("manhom") int manhom,
                                  @Query("ten") String ten,
                                  @Query("mota") String mota);
}
