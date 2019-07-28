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

public class FlightRequestClander extends AppCompatActivity {

    EditText name,   flightno, dateoftravel, flight_name;
    private RadioGroup  rgRegion;
    String strregion = ",";
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_request_clander);

        name = findViewById(R.id.name);
        flightno = findViewById(R.id.flightnumber);
        flight_name = findViewById(R.id.flightname);
        dateoftravel = findViewById(R.id.dateoftravel);

        btnSubmit = findViewById(R.id.btnSubmit);

        rgRegion = findViewById(R.id.Radio_region);
        rgRegion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strregion = radioButton.getText().toString();
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
        else  if (HelperUtilities.isEmptyOrNull(flightno.getText().toString())) {
            flightno.setError("Please enter your Flight no");
        }else  if (HelperUtilities.isEmptyOrNull(flight_name.getText().toString())) {
            flight_name.setError("Please enter your flight name");
        }else  if (HelperUtilities.isEmptyOrNull(dateoftravel.getText().toString())) {
            flight_name.setError("Please enter your Date of name");
        }else {
            flightcalender();
        }
    }

    private void flightcalender() {

        final ViewDialog progressDialog  = new ViewDialog(FlightRequestClander.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.flight_request, new Response.Listener<String>() {
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
                            builder = new AlertDialog.Builder(FlightRequestClander.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(FlightRequestClander.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully added new Request")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(FlightRequestClander.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(FlightRequestClander.this,"Error" , jsonObject.getString("result"));
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
                param.put("flight_name",flight_name.getText().toString());
                param.put("flight_no",flightno.getText().toString());
                param.put("travel_date",dateoftravel.getText().toString());
                param.put("region",strregion);


                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
