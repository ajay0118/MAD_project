package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private Fragment selectedFragment = null;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Initialize welcomeTest
        welcomeText = findViewById(R.id.welcomeText);

        //retrieve the username from the Intent

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        if(email != null){
            String username = getUsernameFromEmail(email);
            welcomeText.setText("Welcome," + username);
        }
        else
        {
            welcomeText.setText("Welcome");
        }
//        if (username !=null) {
//            welcomeText.setText("Welcome," + username);
//        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Set the initial fragment to be displayed
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new CategoryFragment()).commit();
    }

    private String getUsernameFromEmail(String email) {
        String[] parts = email.split("@");

        if (parts.length > 0){
            return parts[0];
        }
        else {
            return "";
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.nav_home) {
                        selectedFragment = new CategoryFragment();
                    }
                    // Add more conditions for other tabs if needed

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    return true;
                }
            };
}
