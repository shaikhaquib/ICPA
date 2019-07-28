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
import android.widget.ImageButton;
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

public class Complaint_form extends AppCompatActivity {

    ViewDialog progressDialoge = new ViewDialog(this);
    EditText name,designation,sapno,complaint;
    Button btncomplaint;
    RadioGroup Rgregion;
    String strregion = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_form);


        designation = findViewById(R.id.Designation);
        name = findViewById(R.id.name);
        sapno = findViewById(R.id.sapno);
        complaint = findViewById(R.id.complaint);
        btncomplaint = findViewById(R.id.btncomplaint);
        Rgregion = findViewById(R.id.Radio_region);

        Rgregion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strregion = radioButton.getText().toString();
            }
        });

        btncomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });


        btncomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
                    name.setError("Please enter your name");
                }
                else
                if (HelperUtilities.isEmptyOrNull(designation.getText().toString())) {
                    name.setError("Please enter your designation");
                }
                else if (HelperUtilities.isEmptyOrNull(complaint.getText().toString())) {
                    name.setError("Please enter your Date of complaint");
                }
                else{
                    SubmitComplaint();

                }
            }
        });

    }

    private void SubmitComplaint() {

        final ViewDialog progressDialog  = new ViewDialog(Complaint_form.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.complaint, new Response.Listener<String>() {
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
                            builder = new AlertDialog.Builder(Complaint_form.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Complaint_form.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully register your complaint")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(Complaint_form.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(Complaint_form.this," Error" , jsonObject.getString("error"));
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
                param.put("designation",designation.getText().toString());
                param.put("complaint",complaint.getText().toString());
                param.put("region",strregion);
                param.put("customer_id",Global.customer_id);

                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
