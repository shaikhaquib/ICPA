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

public class Change_Region extends AppCompatActivity {

    RadioGroup rgRegion,oldrgRegion;
    EditText name, reason;
    String strregion = ",",oldstrregion = ",";
    Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__region);

        name = findViewById(R.id.name);
        reason= findViewById(R.id.reason);
        rgRegion= findViewById(R.id.Radio_region);
        oldrgRegion= findViewById(R.id.oldRadio_region);
        btnRequest= findViewById(R.id.btnRequest);

        rgRegion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strregion = radioButton.getText().toString();
            }
        });
        oldrgRegion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                oldstrregion = radioButton.getText().toString();
            }
        });

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
                    name.setError("Please enter your email");
                }
                else
                if (HelperUtilities.isEmptyOrNull(reason.getText().toString())) {
                    reason.setError("Please mention your reason");
                }else {
                    changeRegion();
                }
            }
        });

    }

    private void changeRegion() {

        final ViewDialog progressDialog  = new ViewDialog(Change_Region.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.change_region, new Response.Listener<String>() {
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
                            builder = new AlertDialog.Builder(Change_Region.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Change_Region.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully registered new request")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(Change_Region.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(Change_Region.this,"Error" , jsonObject.getString("error"));
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
                param.put("customer_id",Global.customer_id);
                param.put("region",strregion);
                param.put("name",name.getText().toString());
                param.put("old_region",oldstrregion);
                param.put("reason",reason.getText().toString());
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
