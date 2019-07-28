package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.HelperUtils.HelperUtilities;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ICPAMemeberApplication extends AppCompatActivity {

    RadioGroup  RgDesignation, rgRegion ,Rgcurrentstats;
    String  strDesignation = ",", strregion = ",",strcurrentstatus = ",";
    EditText employeenumber ,name, email, mobile, address;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icpamemeber_application);


        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        employeenumber = findViewById(R.id.employeeno);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        btnSubmit = findViewById(R.id.btnSubmit);


        rgRegion = findViewById(R.id.Radio_region);
        RgDesignation = findViewById(R.id.radioDesignation);
        Rgcurrentstats = findViewById(R.id.radioCurrentstatus);

        RgDesignation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strDesignation = radioButton.getText().toString();
            }
        });
        rgRegion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strregion = radioButton.getText().toString();
            }
        });

        Rgcurrentstats.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strcurrentstatus = radioButton.getText().toString();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void validate() {
        {
            if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
                name.setError("Please enter your name");
            }
            else
            if (HelperUtilities.isEmptyOrNull(email.getText().toString())) {
                email.setError("Please enter your email");
            } else if (!HelperUtilities.isValidEmail(email.getText().toString())) {
                email.setError("Please enter a valid email");
            }
            else
            if (HelperUtilities.isEmptyOrNull(address.getText().toString())) {
                address.setError("Please enter your Address");
            }
            else if (HelperUtilities.isEmptyOrNull(mobile.getText().toString())) {
                mobile.setError("Please enter Mobile no");
            }
            else
            if (HelperUtilities.isEmptyOrNull(employeenumber.getText().toString())) {
                employeenumber.setError("Please enter batch_no");
            }
           else {
                ICPAMemeberApplicationform();
            }
        }
    }

    private void ICPAMemeberApplicationform() {

        final ViewDialog progressDialog  = new ViewDialog(ICPAMemeberApplication.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.icpa_application_form, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    Log.d("Responce",response);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status"))
                    {


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(ICPAMemeberApplication.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(ICPAMemeberApplication.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully register for ICPA MEMBER")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(ICPAMemeberApplication.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(ICPAMemeberApplication.this,"Error" , jsonObject.getString("error"));
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
                param.put("name",name.getText().toString());
                param.put("designation",strDesignation);
                param.put("mobile",mobile.getText().toString());
                param.put("email",email .getText().toString());
                param.put("address",address.getText().toString());
                param.put("status",strcurrentstatus);
                param.put("region",strregion);
                param.put("customer_id",Global.customer_id);

                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
