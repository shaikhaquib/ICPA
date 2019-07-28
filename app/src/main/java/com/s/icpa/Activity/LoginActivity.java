package com.s.icpa.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.s.icpa.Admin.AdminActivity;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.Model.LoginModel;
import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;
import com.s.icpa.SessionManager;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    ViewDialog progressDialog;
    SQLiteHandler db;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        db = new SQLiteHandler(this);
        session= new SessionManager(LoginActivity.this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressDialog= new ViewDialog(this);

        if (session.isLoggedIn()){
            if (!session.isAdmin()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }else {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }
        }
    }

    public void login(View view) {


        int id = view.getId();

        if (id==R.id.btnLogin) {

            if (email.getText().toString().isEmpty()) {
                email.setError("Field require");
            } else if (password.getText().toString().isEmpty()) {
                email.setError("Field require");
            } else {
                Aunthanticate(email.getText().toString(),password.getText().toString());
            }

        }else if (id==R.id.linkRegister){
            startActivity(new Intent(getApplicationContext(), MemberRegistraion.class));
        }
    }


    private void Aunthanticate(final String email,final String password) {


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.LoginAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Responce",response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status"))
                    {
                        session.setLogin(true);
                        LoginModel data = new Gson().fromJson(response, LoginModel.class);
                        db.addUser(
                                data.getCustomerId(),
                                data.getBatchNo(),
                                data.getEmail(),
                                data.getMobile(),
                                data.getName(),
                                " ",
                                data.getDob(),
                                data.getCurrentFleet(),
                                data.getSapNo(),
                                data.getWorkEmail(),
                                data.getAddress(),
                                data.getMaritalStatus(),
                                data.getWeddingDate(),
                                data.getMember(),
                                data.getDesignation(),
                                data.getRegion()
                                );
                        if(!data.getVerifyStatus().equals("10"))
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        else {
                            session.setAdmin(true);
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        }
                        finish();
                    }
                    else
                    {
                        Global.diloge(LoginActivity.this,"Login Error" , jsonObject.getString("error"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> param = new HashMap<String,String>();
                param.put("email",email);
                param.put("password",password);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}
