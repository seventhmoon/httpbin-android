package com.androidfung.httpbin;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.androidfung.httpbin.databinding.ActivityScrollingBinding;
import com.androidfung.httpbin.http.HttpBinService;
import com.androidfung.httpbin.http.ServicesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new GsonBuilder().setPrettyPrinting().create();
        mOkhttpClient = new OkHttpClient();

        ActivityScrollingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling);
        binding.setBindingData(mResultMap);

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
                Log.d(TAG, t.toString());
            }

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
                Log.d(TAG, t.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
