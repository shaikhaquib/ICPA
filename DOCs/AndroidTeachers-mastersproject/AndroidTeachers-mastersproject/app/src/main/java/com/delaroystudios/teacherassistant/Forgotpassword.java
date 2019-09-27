package com.delaroystudios.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Forgotpassword extends AppCompatActivity {

    EditText usernamee,password;
    Button submit;
    DatabaseHelperClass db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        usernamee= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.newpassword);

        submit= (Button) findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username=usernamee.getText().toString();
                String Password=password.getText().toString();

                db = new DatabaseHelperClass(Forgotpassword.this);

                Loginretrieve data=db.getContact(Username);

                String abc=  data.getUsername();
                db.updateContact(abc, Password);
                Intent i = new Intent(Forgotpassword.this, UserLogin.class);
                startActivity(i);
                finish();


            }
        });



    }
}
