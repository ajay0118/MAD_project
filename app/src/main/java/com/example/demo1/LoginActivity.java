package com.example.demo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private CheckBox rememberMeCheckbox;
    private Button loginButton, signupButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize UI elements
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEditText = findViewById(R.id.email_id_ET);
        passwordEditText = findViewById(R.id.password_ET);
        rememberMeCheckbox = findViewById(R.id.remember_me_checkbox); // Correct findViewById for CheckBox
        loginButton = findViewById(R.id.login_btn);
        signupButton = findViewById(R.id.signup_btn);

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve email and password from EditText fields
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Perform basic validation
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user !=null){
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        String username = user.getEmail() !=null ? user.getEmail().split("@")[0] : "User";
                                        //Create an Intent to start DashboardActivity and pass the username
                                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                        intent.putExtra("username",username); //passing the username to DashboardActivity
                                        startActivity(intent);

//                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this,"User Information not Available",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Set click listener for SignupActivity button
        signupButton.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                // Handle SignupActivity button click (navigate to SignupActivity activity or perform desired action)
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

                Toast.makeText(LoginActivity.this, "Sign Up button clicked", Toast.LENGTH_SHORT).show();
            return true;
            }
        });
    }
}
