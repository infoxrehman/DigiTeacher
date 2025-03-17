package com.digi.digiteacher;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.digi.digiteacher.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private final String BASE_URL = "https://login.digiteacher.in/public";
    private Context mContext;
    private FragmentHomeBinding binding;
    private CourseAdapter adapter;
    String name,tok;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(mContext), container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.main));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerViewCourses.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CourseAdapter();
        binding.recyclerViewCourses.setAdapter(adapter);

    }

    private void fetchCourses() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/get_subject")
                .addHeader("Authorization", "Bearer " + tok)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("SubjectResponse", responseData);

                    Gson gson = new Gson();
                    Type courseListType = new TypeToken<List<CourseModel>>() {}.getType();
                    List<CourseModel> courses = gson.fromJson(responseData, courseListType);

                    if (courses != null && !courses.isEmpty()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setCourses(mContext, courses);
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "No courses found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String errorBody = response.body().string();
                                Toast.makeText(mContext, errorBody, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }


        });
    }

    public void displayFullName(String fullName) {
        binding.fullName.setText(fullName);
        name = fullName;
    }

    public void displayToken(String token) {
        tok = token;
        fetchCourses();
    }

    @Override
    public void onStart() {
        super.onStart();
        displayFullName(name);
        displayToken(tok);
        adapter.token = tok;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayFullName(name);
        displayToken(tok);
    }

}