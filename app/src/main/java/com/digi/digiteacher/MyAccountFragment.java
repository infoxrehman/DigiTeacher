package com.digi.digiteacher;

import static com.digi.digiteacher.Constants.PREF_NAME;
import static com.digi.digiteacher.Constants.TOKEN_KEY;
import static com.digi.digiteacher.Constants.USER_EMAIL;
import static com.digi.digiteacher.Constants.USER_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.digi.digiteacher.databinding.FragmentMyAccountBinding;

public class MyAccountFragment extends Fragment {

    private FragmentMyAccountBinding binding;
    private Context mContext;
    private SharedPreferences sharedPreferences;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(LayoutInflater.from(mContext), container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.main));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        binding.resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ResetPasswordActivity.class));
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.remove(USER_EMAIL);
        editor.remove(USER_NAME);
        editor.apply();

        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}