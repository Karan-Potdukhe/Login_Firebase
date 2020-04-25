package com.workshop.studentform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Password_reset extends AppCompatActivity {
    private EditText passwordEmail;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_password_reset);
        passwordEmail =(EditText)findViewById(R.id.resetemail);
        resetpassword = (Button)findViewById(R.id.resetpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Password Reset");
        progressDialog.setMessage("Please wait.......");


        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getText().toString().trim();

                if(useremail.equals(" ")){
                    Toast.makeText(Password_reset.this,"Please enter your registered email ID",Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               progressDialog.show();
                               Toast.makeText(Password_reset.this, "Password reset link send your email", Toast.LENGTH_SHORT).show();
                               finish();
                               startActivity(new Intent(Password_reset.this,Login_page.class));
                               progressDialog.dismiss();
                           }else {
                               Toast.makeText(Password_reset.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                               progressDialog.dismiss();
                           }
                        }
                    });
                }

            }
        });
    }
}
