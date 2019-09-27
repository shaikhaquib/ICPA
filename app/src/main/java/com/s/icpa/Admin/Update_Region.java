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
import com.s.icpa.Model.EmailRequest;
import com.s.icpa.R;
import com.s.icpa.SendNotification;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Update_Region extends AppCompatActivity {

    TextView name, email, workemail, mobile, designation, newEmail, reason;
    Button approve, disapprove;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__region);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        workemail = findViewById(R.id.work_email);
        reason = findViewById(R.id.reason);
        newEmail = findViewById(R.id.newemail);
        designation = findViewById(R.id.designation);
        approve = findViewById(R.id.approve);
        disapprove = findViewById(R.id.disapprove);

        disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendNotification(Update_Region.this).execute(Global.customer_id,name.getText().toString()+"Region change request","your request for Region change has been disapproved.For more information you can contact region admin" );

            }
        });

        getData();

    }

    private void getData() {

        final EmailRequest data = (EmailRequest) getIntent().getSerializableExtra("data");

        setTitle(data.getName());

        name.setText(data.getName());
        reason.setText(data.getReason());
        newEmail.setText(data.getNewRegion());
        designation.setText(data.getDesignation());
        workemail.setText(data.getBatchNo());
        email.setText(data.getCurrentFleet());

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approve_user(data.getRegion(),data.getCustomerId());
            }
        });


    }


    private void approve_user(final String s, final String customerId) {

        final ViewDialog progressDialog = new ViewDialog(this);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.approve_email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Responce", response);

                    if (jsonObject.getBoolean("status")) {


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(Update_Region.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Update_Region.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have updated user status")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        new SendNotification(Update_Region.this).execute(Global.customer_id,name.getText().toString()+"Region change request","your request for Region change has been approved.To apply setting you have to logout and re-login into your account" );
                                        startActivity(new Intent(Update_Region.this, AdminActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        Global.diloge(Update_Region.this, "Error", jsonObject.getString("result"));
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
                param.put("new_region", s);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}
