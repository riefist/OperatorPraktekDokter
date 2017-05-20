package com.muhamadarief.operatorpraktek.Utils;

import com.muhamadarief.operatorpraktek.API.ApiService;

/**
 * Created by Muhamad Arief on 26/04/2017.
 */

public class ApiUtils {

    public ApiUtils(){

    }

    public static final String BASE_URL = "http://192.168.142.1/Skripsi_PraktekDokter/";

    public static ApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
