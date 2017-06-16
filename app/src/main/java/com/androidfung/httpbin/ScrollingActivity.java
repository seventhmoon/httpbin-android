package com.androidfung.httpbin;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {

    private static final String TAG = ScrollingActivity.class.getSimpleName();
    private BindingData mBindingData;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new GsonBuilder().setPrettyPrinting().create();
        ActivityScrollingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling);
        mBindingData = new BindingData();

        binding.setBindingData(mBindingData);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> testService());

        testService();
    }

    private void testService(){
        HttpBinService httpBinService = ServicesManager.getHttpBinService();

        httpBinService.get().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                String prettyString = mGson.toJson(response.body());
                Log.d(TAG, prettyString);
                mBindingData.setResponseGet(prettyString);

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
                mBindingData.setResponsePost(prettyString);
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

    public static class BindingData extends BaseObservable{

        private String mResponseGet = "Loading...";
        private String mResponsePost = "Loading...";

        @Bindable
        public String getResponseGet() {
            return mResponseGet;
        }

        void setResponseGet(String responseGet) {
            this.mResponseGet = responseGet;
            notifyPropertyChanged(BR.responseGet);
        }

        @Bindable
        public String getResponsePost() {
            return mResponsePost;

        }

        void setResponsePost(String responsePost) {
            this.mResponsePost = responsePost;
            notifyPropertyChanged(BR.responsePost);
        }
    }
}
