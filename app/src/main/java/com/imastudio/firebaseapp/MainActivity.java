package com.imastudio.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnSignIn, btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Fill required!", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("signup", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String dataEmail = user.getEmail();

                                        Toast.makeText(MainActivity.this, "Sign Up Success: "+ dataEmail, Toast.LENGTH_SHORT).show();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("signup", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Fill required!", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("signin", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String dataEmail = user.getEmail();

                                        Toast.makeText(MainActivity.this, "Welcome: "+ dataEmail, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, RealtimeDatabaseActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("signin", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }
}