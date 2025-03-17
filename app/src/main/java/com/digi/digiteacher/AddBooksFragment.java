package com.digi.digiteacher;

import static android.content.Context.MODE_PRIVATE;
import static com.digi.digiteacher.Constants.PREF_NAME;
import static com.digi.digiteacher.Constants.TOKEN_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digi.digiteacher.databinding.FragmentAddBooksBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddBooksFragment extends Fragment {
    private final String BASE_URL = "https://login.digiteacher.in/public";
    private Context mContext;
    private FragmentAddBooksBinding binding;

    public AddBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBooksBinding.inflate(LayoutInflater.from(mContext), container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.main));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, null);

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookCode = binding.addBooksEt.getText().toString().trim();
                if (TextUtils.isEmpty(bookCode)) {
                    Toast.makeText(mContext, "Please enter a book code", Toast.LENGTH_SHORT).show();
                } else {
                    addBook(bookCode, token);
                }
            }
        });
    }

    private void addBook(String bookCode, String token) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("regi_code", bookCode)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/add_regicode_user")
                .addHeader("Authorization", "Bearer " + token)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseData = response.body().string();
                Log.d("Response", responseData);
                try {
                    JSONObject json = new JSONObject(responseData);
                    final boolean status = json.getBoolean("status");
                    final String message = json.getString("message");
                    if (status) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
