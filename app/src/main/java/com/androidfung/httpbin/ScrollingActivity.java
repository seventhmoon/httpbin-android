package com.androidfung.httpbin;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidfung.httpbin.http.HttpBinService;
import com.androidfung.httpbin.http.ServicesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import hugo.weaving.DebugLog;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = ScrollingActivity.class.getSimpleName();
    private ObservableArrayMap<String, Object> mResultMap = new ObservableArrayMap<>();
    private OkHttpClient mOkhttpClient;
    private Gson mGson;

    @Override
    @DebugLog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new GsonBuilder().setPrettyPrinting().create();
        mOkhttpClient = new OkHttpClient();

        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling);
        binding.setVariable(com.androidfung.httpbin.BR.bindingData, mResultMap);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> testService());

        testService();
    }

    private void testService(){
        HttpBinService httpBinService = ServicesManager.getHttpBinService(mOkhttpClient);

        httpBinService.get().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                String prettyString = mGson.toJson(response.body());
                Log.d(TAG, prettyString);
                mResultMap.put("/get", prettyString);
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                 printError(t);
            }
        });

        httpBinService.ip().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                String prettyString = mGson.toJson(response.body());
                Log.d(TAG, prettyString);
                mResultMap.put("/ip", prettyString);
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                printError(t);            }
        });

        httpBinService.post().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                String prettyString = mGson.toJson(response.body());
                Log.d(TAG, prettyString);
                mResultMap.put("/post", prettyString);
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                printError(t);
            }
        });

    }

    private void printError(Throwable throwable){
        Log.d(TAG, throwable.toString());
        Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show();
    }


}
