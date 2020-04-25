package com.workshop.studentform;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_page extends AppCompatActivity {
    EditText txt_username, txt_email, txt_password, txt_conformpassword;

    Button btn_register;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_signup_page);
      //  getSupportActionBar().setTitle("Signup_page");


        txt_username = (EditText) findViewById(R.id.username);
        txt_email = (EditText) findViewById(R.id.email);
        txt_password = (EditText) findViewById(R.id.password);
        txt_conformpassword = (EditText) findViewById(R.id.confirmpassword);
        btn_register = (Button) findViewById(R.id.register);

        databaseReference = FirebaseDatabase.getInstance().getReference("Registration");
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please wait.........");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = txt_username.getText().toString();
                final String email = txt_email.getText().toString();
                final String password = txt_password.getText().toString();
                final String conformpassword = txt_conformpassword.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Signup_page.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Signup_page.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(conformpassword)) {
                    Toast.makeText(Signup_page.this, "Please Enter conformpassword", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Signup_page.this, "Please Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (password.length() < 6) {
//                    Toast.makeText(Signup_page.this, "Password too Short", Toast.LENGTH_SHORT).show();
//                }
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Signup_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Registration information = new Registration(

                                            username,
                                            email,
                                            password,
                                            conformpassword
                                    );


                                    FirebaseDatabase.getInstance().getReference("Registration")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Signup_page.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();
                                            progressDialog.dismiss();
                                        }
                                    });

                                }
                            }
                        });
            }

        });
    }
}



