package com.androidfung.httpbin.http;

import com.androidfung.httpbin.http.model.GetResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by funglam on 6/13/17.
 */

public interface HttpBinService {

    @GET("get")
    Call<GetResponse> get();
}
