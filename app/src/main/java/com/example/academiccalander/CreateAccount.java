package com.example.academiccalander;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CreateAccount extends AppCompatActivity {

    FirebaseAuth mAuth;

    ActionCodeSettings actionCodeSettings =
            ActionCodeSettings.newBuilder().setUrl("heeps://www.example.com/fnishSignUp?cartId=1234").setHandleCodeInApp(true).setIOSBundleId("come.example.ios").setAndroidPackageName(
                    "come.example.android",
                    true,"12"
            ).build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void onStart(){
        super.onStart();
        Button createAccount = (Button) findViewById(R.id.createButton);
        EditText createEmail = (EditText) findViewById(R.id.createEmail);
        EditText createPassword = (EditText) findViewById(R.id.createPassword);
        EditText confirmPass = (EditText) findViewById(R.id.confirmPassword);
        EditText createUsername = (EditText) findViewById(R.id.username);

        createAccount.setOnClickListener(view -> {
            String email = (String) createEmail.getText().toString();
            String password = (String) createPassword.getText().toString();
            String confirm = (String) confirmPass.getText().toString();
            String userName = (String) createUsername.getText().toString();
            CharSequence checkEmail = (String) email;
            if(email == null){
                createEmail.setError("Please type in the email");
            } else if(password == null){
                createPassword.setError("Please put in the password");
            } else if(!confirm.equals(password)){
                confirmPass.setError("Please re-enter the password");
            } else {
                if(!Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches()){
                    createEmail.setError("invalid Email");
                    createEmail.requestFocus();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(CreateAccount.this, MainActivity.class));
                            } else {
                                Log.w(TAG, "createWithEmail:failure", task.getException());
                                Toast.makeText(CreateAccount.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }



}