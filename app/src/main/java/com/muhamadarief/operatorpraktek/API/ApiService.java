package com.muhamadarief.operatorpraktek.API;

import com.muhamadarief.operatorpraktek.Model.BaseResponse;
import com.muhamadarief.operatorpraktek.Model.Pendaftaran;
import com.muhamadarief.operatorpraktek.Model.PraktekDokter;
import com.muhamadarief.operatorpraktek.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Muhamad Arief on 26/04/2017.
 */

public interface ApiService {

    @GET("daftarpasien.php")
    Call<List<Pendaftaran>> getDaftarPasien(@Query("id_praktek") String id_praktek, @Query("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_status.php")
    Call<BaseResponse> updateStatus(
            @Field("id_praktek") String id_praktek,
            @Field("status") boolean status
    );

    @GET("dataoperator.php")
    Call<User> getDataOperator(
            @Query("id_praktek") String id_praktek
    );

    @FormUrlEncoded
    @POST("updatedataoperator.php")
    Call<BaseResponse> updateDataOperator(
            @Field("id_praktek") String id_praktek,
            @Field("email") String email,
            @Field("nohp") String nohp,
            @Field("jenis_kelamin") String jenis_kelamin
    );

}
