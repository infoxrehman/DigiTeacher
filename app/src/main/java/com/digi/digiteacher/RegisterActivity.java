package com.digi.digiteacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.digi.digiteacher.databinding.ActivityRegisterBinding;

import android.util.Log;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private final String BASE_URL = "https://login.digiteacher.in/public";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.nameEt.getText().toString().trim();
                String mobile = binding.mobileEt.getText().toString().trim();
                String email = binding.emailEt.getText().toString().trim();
                String school = binding.schoolEt.getText().toString().trim();
                String clas = binding.clasEt.getText().toString().trim();
                String code = binding.codeEt.getText().toString().trim();
                String password = binding.passwordEt.getText().toString().trim();
                String confirmPassword = binding.cPasswordEt.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    signUp(name, mobile, email, school, clas, code, password);
                } else {
                    Toast.makeText(RegisterActivity.this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

        binding.haveAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void signUp(String name, String mobile, String email, String school, String clas, String code, String password) {

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("full_name", name)
                .add("email", email)
                .add("password", password)
                .add("fk_role_id", "3")
                .add("regi_code", code)
                .add("phone_number", mobile)
                .add("country_type", "+91")
                .add("school", school)
                .add("class", clas)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/auth/signup_user")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();
                Log.d("Response", responseData);
                try {
                    JSONObject json = new JSONObject(responseData);
                    final boolean status = json.getBoolean("status");
                    final String message = json.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status) {
                                Toast.makeText(RegisterActivity.this, "Register successfully! Please login to continue", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
