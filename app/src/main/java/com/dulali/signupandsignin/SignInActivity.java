package com.dulali.signupandsignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText emailEditText , passwordEditText;
    Button signIn;
    TextView goSignUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signIn = findViewById(R.id.signIn);
        goSignUp = findViewById(R.id.goSignUp);
        mAuth = FirebaseAuth.getInstance();


        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this , MainActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignInActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    int len = password.length();
                    boolean con = email.matches("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})");

                    if(len>6 && con){
                        loginUser(email , password);
                    }

                    else{
                        Toast.makeText(SignInActivity.this, "Enter valid email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email , password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this , DashActivity.class));
                    }
                });
    }
}