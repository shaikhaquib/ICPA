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
import android.widget.LinearLayout;
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

public class TravelForm extends AppCompatActivity {

    EditText  name, email, mobile, staffno, flightno, dateoftravel, leavesanctionedfrom,leavesanctionedto,flighttype;
    private RadioGroup  Rgcurrentstats;

    String  strcurrentstatus = ",";

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_form);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        flightno = findViewById(R.id.flightnumber);
        flighttype = findViewById(R.id.flighttype);
        staffno = findViewById(R.id.staffnumber);
        dateoftravel = findViewById(R.id.dateoftravel);
        leavesanctionedfrom = findViewById(R.id.leavesanctionedfrom);
        leavesanctionedto = findViewById(R.id.leavesanctionedto);
        btnSubmit = findViewById(R.id.btnSubmit);



        Rgcurrentstats = findViewById(R.id.radioCurrentstatus);
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
        if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
            name.setError("Please enter your name");
        }
        else
        if (HelperUtilities.isEmptyOrNull(email.getText().toString())) {
            email.setError("Please enter your email");
        } else if (!HelperUtilities.isValidEmail(email.getText().toString())) {
            email.setError("Please enter a valid email");
        }else  if (HelperUtilities.isEmptyOrNull(flightno.getText().toString())) {
            flightno.setError("Please enter your Flight no");
        }else  if (HelperUtilities.isEmptyOrNull(flighttype.getText().toString())) {
            flightno.setError("Please enter your flight type");
        }else if (HelperUtilities.isEmptyOrNull(staffno.getText().toString())) {
            staffno.setError("Please enter your Staff no");
        }else if (HelperUtilities.isEmptyOrNull(mobile.getText().toString())) {
            mobile.setError("Please enter your mobile no");
        }else  if (HelperUtilities.isEmptyOrNull(dateoftravel.getText().toString())) {
            dateoftravel.setError("Please enter your Date of travel");
        }else  if (HelperUtilities.isEmptyOrNull(leavesanctionedfrom.getText().toString())) {
            leavesanctionedfrom.setError("Please enter your Date of travel");
        }else  if (HelperUtilities.isEmptyOrNull(leavesanctionedto.getText().toString())) {
            leavesanctionedto.setError("Please enter your Date of travel");
        }else {
            ACM();
        }
    }

    private void ACM() {

        final ViewDialog progressDialog  = new ViewDialog(TravelForm.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.ACM, new Response.Listener<String>() {
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
                            builder = new AlertDialog.Builder(TravelForm.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(TravelForm.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully added new Request")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(TravelForm.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(TravelForm.this,"Error" , jsonObject.getString("error"));
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
                param.put("flight_type",flighttype.getText().toString());
                param.put("flight_no",flightno.getText().toString());
                param.put("travel_date",dateoftravel.getText().toString());
                param.put("staff_no",staffno.getText().toString());
                param.put("email",email.getText().toString());
                param.put("mobile",mobile.getText().toString());
                param.put("leave_from",leavesanctionedfrom.getText().toString());
                param.put("leave_to",leavesanctionedto.getText().toString());
                param.put("current_fleet",strcurrentstatus);
                param.put("region",Global.Region);

                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
