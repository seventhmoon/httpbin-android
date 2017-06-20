package com.androidfung.httpbin.http;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by funglam on 6/13/17.
 */

public interface HttpBinService {

    @GET("get")
    Call<Map<String, Object>> get();

    @POST("post")
    Call<Map<String, Object>> post();

    @GET("ip")
    Call<Map<String, Object>> ip();
}
