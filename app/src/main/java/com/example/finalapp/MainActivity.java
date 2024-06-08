package com.example.finalapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private String userName;
    private String userPhone;
    private String service;
    private String date;
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.home) {
                        selectedFragment = new HomeFragment();
                    } else if (item.getItemId() == R.id.profile) {
                        selectedFragment = new ProfileFragment();
                    }
                    else if(item.getItemId() == R.id.about){
                        selectedFragment = new AboutFragment();
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    public void saveUserDetails(String name, String phone) {
        this.userName = name;
        this.userPhone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getDate() {
        return date;
    }
    public String getTime   () {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void clearUserDetails() {
        this.userName = null;
        this.userPhone = null;
    }
}
