package com.digi.digiteacher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digi.digiteacher.databinding.ActivityChaptersBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChaptersActivity extends AppCompatActivity {

    private ActivityChaptersBinding binding;
    private final String BASE_URL = "https://login.digiteacher.in/public";
    String su_id, su_name, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChaptersBinding.inflate(getLayoutInflater());
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.chaptersBackground));
        setContentView(binding.getRoot());

        su_id = getIntent().getStringExtra("sbId");
        su_name = getIntent().getStringExtra("sbName");
        token = getIntent().getStringExtra("token");

        binding.subjectName.setText(su_name);

        binding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        fetchChapters(su_id);
    }

    private void fetchChapters(String subjectId) {
        OkHttpClient client = new OkHttpClient();

        String urlWithParams = BASE_URL + "/api/auth/view_chapter?fk_subject_id=" + subjectId;

        Request request = new Request.Builder()
                .url(urlWithParams)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChaptersActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("ChapterResponse", responseData);
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
                    JsonArray dataArray = jsonObject.getAsJsonArray("data");

                    ChapterModel[] chapterModel = gson.fromJson(dataArray, ChapterModel[].class);

                    processChapters(chapterModel);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChaptersActivity.this, "Failed to load chapters", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void processChapters(ChapterModel[] chapterModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = findViewById(R.id.recyclerViewChapters);
                ChapterAdapter adapter = new ChapterAdapter(Arrays.asList(chapterModel), ChaptersActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChaptersActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
