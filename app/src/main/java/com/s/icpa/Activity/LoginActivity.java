package com.s.icpa.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.s.icpa.Admin.ACM_List;
import com.s.icpa.Admin.AdminActivity;
import com.s.icpa.Admin.AdminBlogs;
import com.s.icpa.Admin.ApplicationsForm;
import com.s.icpa.Admin.FatigueReportList;
import com.s.icpa.Admin.LoginApprove;
import com.s.icpa.Admin.PMU;
import com.s.icpa.Admin.RWC;
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
                        LoginModel data = new Gson().fromJson(response, LoginModel.class);
                        db.deleteUsers();
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
                        if(data.getVerifyStatus().equals("10")){
                            session.setLogin(true);
                            session.setAdmin(true);
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            finish();
                        }else if(data.getVerifyStatus().equals("20")){
                            startActivity(new Intent(LoginActivity.this, LoginApprove.class));
                            finish();
                        }else if(data.getVerifyStatus().equals("22")){
                            startActivity(new Intent(LoginActivity.this, AdminBlogs.class));
                            finish();
                        }else if(data.getVerifyStatus().equals("24")){
                            /*startActivity(new Intent(LoginActivity.this, Com.class));
                            finish();*/
                        }else if(data.getVerifyStatus().equals("26")){
                            startActivity(new Intent(LoginActivity.this, ACM_List.class));
                            finish();
                        }else if(data.getVerifyStatus().equals("28")){
                            startActivity(new Intent(LoginActivity.this, PMU.class));
                            finish();
                        }else if(data.getVerifyStatus().equals("30")){
                            startActivity(new Intent(LoginActivity.this, RWC.class));
                            finish();
                        }else if(data.getVerifyStatus().equals("32")){
                            startActivity(new Intent(LoginActivity.this, FatigueReportList.class));
                            finish();
                        }
                        else if (data.getVerifyStatus().equals("3")){
                            Global.diloge(LoginActivity.this,"Login Error","Your login request has been unapproved \n Please contact region admin for more detail. ");
                        } else if (data.getVerifyStatus().equals("0")){
                            Global.diloge(LoginActivity.this,"Login Request","Your login request is under process \n Please contact region admin for more detail. ");
                        }
                        else {
                            if (data.getMember().equalsIgnoreCase("yes")){
                            session.setLogin(true);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();}else {
                                Toast.makeText(LoginActivity.this, "You are not ICPA member please become member", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, ICPAMemeberApplication.class));
                            }
                        }
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
