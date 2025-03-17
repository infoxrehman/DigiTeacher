package com.digi.digiteacher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ChapterVideoActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://login.digiteacher.in/public";

    String subjectId, chapterId, subjectName, chapterName;

    TextView subjectTv, chapterTv;

    ImageView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.chapterVideosBackground));
        setContentView(R.layout.activity_chapter_video);

        subjectId = getIntent().getStringExtra("sbId");
        chapterId = getIntent().getStringExtra("ch_id");
        subjectName = getIntent().getStringExtra("sbName");
        chapterName = getIntent().getStringExtra("chName");

        subjectTv = findViewById(R.id.videoSubject);
        chapterTv = findViewById(R.id.videoChapter);
        closeBtn = findViewById(R.id.closeBtn);

        subjectTv.setText(subjectName);
        chapterTv.setText(chapterName);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

//        String chapterId = "13";

         fetchChapterVideos(subjectId, chapterId);
    }

    private void fetchChapterVideos(String subjectId, String chapterId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/view_audiovideo?fk_subject_id=" + subjectId + "&fk_chapter_id=" + chapterId)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("VideoResponse", responseData);
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);
                    JsonArray dataArray = jsonObject.getAsJsonArray("data");

                    ChapterVideoModel[] chapterVideos = gson.fromJson(dataArray, ChapterVideoModel[].class);

                    processChapterVideos(chapterVideos);
                } else {
                }
            }
        });
    }

    private void processChapterVideos(ChapterVideoModel[] chapterVideos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                ChapterVideoAdapter adapter = new ChapterVideoAdapter(Arrays.asList(chapterVideos), ChapterVideoActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChapterVideoActivity.this));
                recyclerView.setAdapter(adapter);
            }
        });
    }

}