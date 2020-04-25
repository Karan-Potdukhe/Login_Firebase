package com.workshop.studentform;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_page extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    Button buttona;
    String email;
    String password;
    TextView textViewa;
    CheckBox showpassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        checkConnection();
        final EditText emails = (EditText) findViewById(R.id.emailid);
        final EditText passwords = (EditText) findViewById(R.id.passwordid);
        buttona = (Button) findViewById(R.id.signup);
        Button buttonlogin = (Button) findViewById(R.id.login);
        textViewa = (TextView) findViewById(R.id.forgotpassword);
        showpassword = (CheckBox)findViewById(R.id.showpassword);
        sharedPreferences = getSharedPreferences( "Login_page",MODE_PRIVATE );
        editor=sharedPreferences.edit();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            startActivity( new Intent(Login_page.this,MainActivity.class  ) );
            finish();
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait.........");

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    passwords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    passwords.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        textViewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_page.this,Password_reset.class));
            }
        });




        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emails.getText().toString().trim();
                password = passwords.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login_page.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_page.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (password.length() < 6) {
//                    Toast.makeText(Login_page.this, "Password to short", Toast.LENGTH_SHORT).show();
//
//                }
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login_page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {


                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                    progressDialog.dismiss();
                                } else {

                                    Toast.makeText(Login_page.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
//            public  void  signup(View view){
//                startActivity(new Intent(getApplicationContext(),Signup_page.class));
//            }
        });
    }


    public void signup(View view) {

        startActivity(new Intent(getApplicationContext(), Signup_page.class));

    }

    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
//                Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_SHORT).show();
//            }
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                Toast.makeText(this, "Data Network Enabled", Toast.LENGTH_SHORT).show();
//            }
        }
        else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }






