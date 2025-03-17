package com.digi.digiteacher;

import static com.digi.digiteacher.Constants.PREF_NAME;
import static com.digi.digiteacher.Constants.TOKEN_KEY;
import static com.digi.digiteacher.Constants.USER_EMAIL;
import static com.digi.digiteacher.Constants.USER_NAME;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digi.digiteacher.databinding.ActivityResetPasswordBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;
    private final String BASE_URL = "https://login.digiteacher.in/public";
    private ProgressDialog progressDialog;
    private OkHttpClient client;
    private SharedPreferences sharedPreferences;
    String token, fullName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        token = sharedPreferences.getString(TOKEN_KEY, null);

        fullName = sharedPreferences.getString(USER_NAME, null);
        email = sharedPreferences.getString(USER_EMAIL, null);

        client = new OkHttpClient();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        binding.fullNameLabel.setText(fullName);
        binding.emailLabel.setText(email);

        binding.emailEt.setText(email);

        binding.resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = binding.passwordEt.getText().toString().trim();
                String confirmPassword = binding.cPasswordEt.getText().toString().trim();

                if (!password.isEmpty() && !confirmPassword.isEmpty() && !email.isEmpty() && !token.isEmpty()) {
                    if (password.equals(confirmPassword)) {
                        progressDialog.show();
                        resetPassword(email, password, confirmPassword, token);
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void resetPassword(String email, String password, String confirmPassword, String token) {
        // Set up the JSON request body
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            jsonBody.put("password_confirmation", confirmPassword);
            jsonBody.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonBody.toString()
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/reset")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ResetPasswordActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                Log.d("ChangePasswordResponse", responseData);
                try {
                    JSONObject json = new JSONObject(responseData);
                    final boolean status = json.getBoolean("status");
                    final String message = json.getString("message");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });

                    if (status) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ResetPasswordActivity.this, "Your password has been reset successfully! Please Login again", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("ErrorResponse", responseData);
                            Toast.makeText(ResetPasswordActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
