package com.s.icpa.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.s.icpa.Activity.APIs;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.Model.Login_Request;
import com.s.icpa.Model.ModelApplicationform;
import com.s.icpa.R;
import com.s.icpa.SendNotification;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApplicationDetail extends AppCompatActivity {
    
    TextView name, email,  dob, address, designation, batchno, currentfleet,  sapno;
    Button approve, disapprove;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_detail);



        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        designation = findViewById(R.id.designation);
        batchno = findViewById(R.id.batch_no);
        currentfleet = findViewById(R.id.current_fleet);
        sapno = findViewById(R.id.sap_no);
        approve = findViewById(R.id.approve);
        disapprove = findViewById(R.id.disapprove);

        disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve = findViewById(R.id.approve);
                disapprove = findViewById(R.id.disapprove);

                disapprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SendNotification(ApplicationDetail.this).execute(Global.customer_id,name.getText().toString()+"ICPA Member Application","your request for ICPA Member Application has been disapproved.For more information you can contact region admin" );
                    }
                });
            }
        });

        getData();


    }

    private void getData() {

        final ModelApplicationform data = (ModelApplicationform) getIntent().getSerializableExtra("data");

        setTitle(data.getName());

        name.setText(data.getName());
        dob.setText(data.getDob());
        address.setText(data.getAddress());
        designation.setText(data.getDesignation());
        batchno.setText(data.getBatchNo());
        currentfleet.setText(data.getCurrentFleet());
        sapno.setText(data.getSapNo());



        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve_user("YES", data.getCustomerId());
            }
        });

        disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve_user("NO", data.getCustomerId());
            }
        });


    }

    private void approve_user(final String s, final String customerId) {

        final ViewDialog progressDialog = new ViewDialog(this);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.updateApllication, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Responce", response);

                    if (jsonObject.getBoolean("status")) {


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(ApplicationDetail.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(ApplicationDetail.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have updated user status")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        new SendNotification(ApplicationDetail.this).execute(Global.customer_id,name.getText().toString()+"ICPA Member Application","your request for ICPA Member Application has been approved.To see changes logout and re-login into your account");
                                        startActivity(new Intent(ApplicationDetail.this, AdminActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        Global.diloge(ApplicationDetail.this, "Error", jsonObject.getString("result"));
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", customerId);
                param.put("member", s);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}
