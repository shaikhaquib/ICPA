package com.delaroystudios.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener{

    EditText namee,username,password,confirmpassword,phonenumber;

    DatabaseHelperClass db;

    Button regiter;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        db=new DatabaseHelperClass(this);

        namee= (EditText) findViewById(R.id.name);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        confirmpassword= (EditText) findViewById(R.id.confirmpassword);
        phonenumber= (EditText) findViewById(R.id.phonenumber);

        regiter= (Button) findViewById(R.id.register);
        login= (Button) findViewById(R.id.login);
        regiter.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==regiter)
        {
            String names=namee.getText().toString();
            String usernames=username.getText().toString();
            String pass=password.getText().toString();
            String confirmpass=confirmpassword.getText().toString();
            String phone=phonenumber.getText().toString();
            if(names.isEmpty() && usernames.isEmpty()&&pass.isEmpty())
            {
                Toast.makeText(getBaseContext(),"please fill all the details",Toast.LENGTH_SHORT).show();
            }
            else if(phone.length()<10)
            {
                Toast.makeText(getBaseContext(),"phone no must be 10 digits",Toast.LENGTH_SHORT).show();
            }
            else if(phone.length()>10)
            {
                Toast.makeText(getBaseContext(),"phone no must be 10 digits",Toast.LENGTH_SHORT).show();
            }
            else if(pass.equals(confirmpass) )
            {
                adddata(names,usernames,pass,confirmpass,phone);
            }
            else if(pass!=confirmpass)
            {
                Toast.makeText(getBaseContext(),"password do not match",Toast.LENGTH_SHORT).show();
            }



        }
        else if(v==login)
        {
            Intent i=new Intent(this,UserLogin.class);
            startActivity(i);

        }

    }

    public void adddata(String names, String usernames, String pass,String confirmpass,String phone) {
        boolean inserData=db.addData(names,usernames,pass,confirmpass,phone);

        if(inserData==true)
        {
            Toast.makeText(getBaseContext(),"Successfully Registered!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),"Oops! something went wrong",Toast.LENGTH_SHORT).show();
        }
        Intent i=new Intent(this,UserLogin.class);
        startActivity(i);

        finish();
    }

    }

