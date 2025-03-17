package com.digi.digiteacher;

import static com.digi.digiteacher.Constants.PREF_NAME;
import static com.digi.digiteacher.Constants.TOKEN_KEY;
import static com.digi.digiteacher.Constants.USER_EMAIL;
import static com.digi.digiteacher.Constants.USER_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.digi.digiteacher.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        String token = sharedPreferences.getString(TOKEN_KEY, null);
        if (token == null) {
            redirectToLogin();
            return;
        }

        String fullName = sharedPreferences.getString(USER_NAME, null);
        String email = sharedPreferences.getString(USER_EMAIL, null);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        HomeFragment homeFragment = (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (homeFragment != null) {
            homeFragment.displayFullName(fullName);
            homeFragment.displayToken(token);
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView navHeaderName = headerView.findViewById(R.id.nav_header_name);
        TextView navHeaderEmail = headerView.findViewById(R.id.nav_header_email);

        navHeaderName.setText(fullName);
        navHeaderEmail.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_logout) {
//            logout();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    private void logout() {
//        // Clear the token from SharedPreferences
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove(TOKEN_KEY);
//        editor.apply();
//
//        // Redirect to the LoginActivity
//        redirectToLogin();
//    }
//

    private void redirectToLogin() {
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
