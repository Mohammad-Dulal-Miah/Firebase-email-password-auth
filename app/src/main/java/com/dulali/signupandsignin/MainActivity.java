package com.dulali.signupandsignin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText , passwordEditText;
    Button signUp;
    TextView goSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUp = findViewById(R.id.signUp);
        goSignIn = findViewById(R.id.goSignIn);
        mAuth = FirebaseAuth.getInstance();

        goSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SignInActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    int len = password.length();
                    boolean con = email.matches("(\\S.*\\S)(@)(\\S.*\\S)(.\\S[a-z]{2,3})");

                    if(len>6 && con){
                       boolean res =  registerUser(email , password);
                       if(res){
                           startActivity(new Intent(MainActivity.this , SignInActivity.class));
                       }
                       else{
                           Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                       }
                    }

                    else{
                        Toast.makeText(MainActivity.this, "Enter valid email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean registerUser(String email , String password){

        final boolean[] con = {false};
     mAuth.createUserWithEmailAndPassword(email,password)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(MainActivity.this, "Registration successfully", Toast.LENGTH_SHORT).show();
                         con[0] =  true;

                     }
                     else{
                         Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                     }
                 }

             });
      return con[0];
    }
}