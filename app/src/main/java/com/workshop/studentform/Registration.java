package com.workshop.studentform;

import android.widget.EditText;

public class Registration {

    public String Username,Email,Password,Conformpassword;

    public Registration (){

    }

    public Registration(String username, String email, String password, String conformpassword) {
      this.Username = username;
      Email = email;
      Password = password;
      Conformpassword = conformpassword;
    }



}
