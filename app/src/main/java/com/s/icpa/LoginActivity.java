package com.s.icpa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }

    public void login(View view) {


        int id = view.getId();

        if (id==R.id.btnLogin) {

            if (email.getText().toString().isEmpty()) {
                email.setError("Field require");
            } else if (password.getText().toString().isEmpty()) {
                email.setError("Field require");
            } else if (email.getText().toString().equals("john.doe@gmail.com") && password.getText().toString().equals("123456")) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("You have entered an invalid username or password");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        }else if (id==R.id.linkRegister){
            startActivity(new Intent(getApplicationContext(), MemberRegistraion.class));
        }
    }
}
