package com.example.academiccalander;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Button createPage = (Button) findViewById(R.id.create_account);
        createPage.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CreateAccount.class));
        });
    }

    protected void onStart(){
        super.onStart();
        Button loginButton = (Button) findViewById(R.id.login);
        EditText emailEditText = (EditText) findViewById(R.id.email);
        EditText passwordEditText = (EditText) findViewById(R.id.password);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(getApplicationContext(), MainPage.class));
        } else {
            loginButton.setOnClickListener(view -> {
                String email = (String) emailEditText.getText().toString();
                String password = (String) passwordEditText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"signInWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),MainPage.class));
                        } else {
                            Log.w(TAG,"signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
        }
    }



}