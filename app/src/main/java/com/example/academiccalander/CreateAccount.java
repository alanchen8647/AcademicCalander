package com.example.academiccalander;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CreateAccount extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void onStart(){
        super.onStart();
        Button createAccount = (Button) findViewById(R.id.createButton);

        createAccount.setOnClickListener(view -> {
            EditText createEmail = (EditText) findViewById(R.id.createEmail);
            EditText createPassword = (EditText) findViewById(R.id.createPassword);
            EditText confirmPass = (EditText) findViewById(R.id.confirmPassword);

            String email = (String) createEmail.getText().toString();
            String password = (String) createPassword.getText().toString();
            String confirm = (String) confirmPass.getText().toString();

            if(email == null){
                createEmail.setError("Please type in the email");
            }
            if(password == null){
                createPassword.setError("Please put in the password");
            }
            if(!confirm.equals(password)){
                confirmPass.setError("Please re-enter the password");
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "createWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(CreateAccount.this, MainActivity.class));
                    } else {
                        Log.w(TAG, "createWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


    }

}