package com.delaroystudios.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {
    EditText username,password;
    Button login,register,forgotpassword;

    DatabaseHelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        db=new DatabaseHelperClass(this);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);

        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.register);
        forgotpassword= (Button) findViewById(R.id.forgotpassword);

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UserLogin.this, Forgotpassword.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userloginname = username.getText().toString();
                String userloginpassword = password.getText().toString();
                if (userloginname.isEmpty() && userloginpassword.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Enter username and password to proceed", Toast.LENGTH_SHORT).show();
                } else if (userloginname.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Enter username ", Toast.LENGTH_SHORT).show();
                } else if (userloginpassword.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Enter password ", Toast.LENGTH_SHORT).show();
                } else {
                    String result = userloginname;

                    Loginretrieve output = db.getContact(result);




                    String getUsername = output.getUsername();

                    String getPassword = output.getPasswword();

                    if(userloginname.equals(getUsername) && userloginpassword.equals(getPassword))
                    {
                        Intent i = new Intent(UserLogin.this, AppBase.class);
                        startActivity(i);
                        finish();
                    }
                    else if(userloginname!=getUsername&& userloginpassword!=getPassword)
                    {
                        Toast.makeText(getBaseContext(), "username or password incorrect ", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserLogin.this,UserRegistration.class);
                startActivity(i);
            }
        });
    }


}

