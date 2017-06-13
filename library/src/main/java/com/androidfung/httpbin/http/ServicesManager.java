package com.androidfung.httpbin.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by funglam on 6/13/17.
 */

@SuppressWarnings("unused")
public class ServicesManager {

    private static final String BASE_URL = "https://httpbin.org/";

    public static HttpBinService getHttpBinService(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HttpBinService.class);
    }
}