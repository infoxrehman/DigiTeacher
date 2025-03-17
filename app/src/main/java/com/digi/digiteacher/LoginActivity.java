package com.digi.digiteacher;

import static com.digi.digiteacher.Constants.PREF_NAME;
import static com.digi.digiteacher.Constants.TOKEN_KEY;
import static com.digi.digiteacher.Constants.USER_EMAIL;
import static com.digi.digiteacher.Constants.USER_NAME;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digi.digiteacher.databinding.ActivityLoginBinding;

import org.json.JSONArray;
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

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private final String BASE_URL = "https://login.digiteacher.in/public";
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEt.getText().toString().trim();
                String password = binding.passwordEt.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    progressDialog.show();
                    signIn(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.noAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void signIn(String email, String password) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                Log.d("LoginResponse", responseData);
                try {
                    JSONObject json = new JSONObject(responseData);
                    final boolean status = json.getBoolean("status");
                    final String message = json.getString("message");
                    if (status) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String token = json.getString("token");
                                    JSONArray allDataArray = json.getJSONArray("alldata");
                                    JSONObject userData = allDataArray.getJSONObject(0);
                                    String fullName = userData.getString("full_name");

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(TOKEN_KEY, token);
                                    editor.putString(USER_EMAIL, email);
                                    editor.putString(USER_NAME, fullName);
                                    editor.apply();

                                    navigateToDashboard(fullName, token, email);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("ErrorResponse", responseData);
                                    Toast.makeText(LoginActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                }
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ErrorResponse", responseData);
                            Toast.makeText(LoginActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    private void navigateToDashboard(String fullName, String token, String email) {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("email", email);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
