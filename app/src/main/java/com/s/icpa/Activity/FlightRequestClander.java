package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.s.icpa.Admin.FatigueReportDetail;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.HelperUtils.HelperUtilities;
import com.s.icpa.Model.FlightRequestModel;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FlightRequestClander extends AppCompatActivity {

    EditText name, flightno, flight_name;
    private RadioGroup rgRegion;
    String strregion = ",";
    Button btnSubmit;
    EditText  txtname, txtflightno, txtflightname, txtflightdate;
    String Date;
    TextView dateoftravel;
    RelativeLayout relForm;
    LinearLayout detail, ltEdt;
    Button edit, delete, update;
    String id;
    ViewDialog progressDialog = new ViewDialog(FlightRequestClander.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_request_clander);


        if (getIntent() != null) {
            // You can check for null to make sure
            int year = getIntent().getIntExtra("year", 2017);
            int month = getIntent().getIntExtra("month", 1);
            int dayOfMonth = getIntent().getIntExtra("dayOfMonth", 1);

            Date = dayOfMonth + "/" + month + "/" + year;
            // Use these to put them inside a TextView...
        }

        name = findViewById(R.id.name);
        flightno = findViewById(R.id.flightnumber);
        flight_name = findViewById(R.id.flightname);
        dateoftravel = findViewById(R.id.dateoftravel);
        txtname = findViewById(R.id.txtname);
        txtflightno = findViewById(R.id.txtflightnumber);
        txtflightname = findViewById(R.id.txtflightname);
        txtflightdate = findViewById(R.id.txttraveldate);
        relForm = findViewById(R.id.form);
        detail = findViewById(R.id.detail);
        dateoftravel.setText(Date);
        btnSubmit = findViewById(R.id.btnSubmit);
        edit = findViewById(R.id.btnEdit);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        ltEdt = findViewById(R.id.ld);

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


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ltEdt.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
                txtname.setEnabled(true);
                txtflightno.setEnabled(true);
                txtflightname.setEnabled(true);
                txtflightdate.setEnabled(true);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(FlightRequestClander.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(FlightRequestClander.this);
                }
                builder.setCancelable(false);
                builder.setTitle("Confirmation")
                        .setMessage("Are you sure! Do you want to delete it.")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               Datedelete();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtname.getText().toString().isEmpty()){
                    name.setError("Field required");
                }else if (txtflightname.getText().toString().isEmpty()){
                    txtflightname.setError("Field required");
                }else if (txtflightno.getText().toString().isEmpty()){
                    txtflightno.setError("Field required");
                }else if (txtflightdate.getText().toString().isEmpty()){
                    txtflightdate.setError("Field required");
                }else {
                    updateData(txtname.getText().toString(),txtflightname.getText().toString(),txtflightdate.getText().toString(),txtflightno.getText().toString());
                }
            }
        });


        getData();
    }

    private void updateData(final String name, final String fname, final String date, final String fno) {


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.update_request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    Log.d("Responce", response);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(FlightRequestClander.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(FlightRequestClander.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully updated your calender")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(FlightRequestClander.this, MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        Global.diloge(FlightRequestClander.this, "Error", jsonObject.getString("result"));
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
                param.put("customer_id", Global.customer_id);
                param.put("flight_name", fname);
                param.put("flight_no", fno);
                param.put("date", date);
                param.put("id", id);


                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void Datedelete() {


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.delete_request, new Response.Listener<String>() {
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
                                .setMessage("You have successfully Deleted")
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
                        Global.diloge(FlightRequestClander.this,"Error" , jsonObject.getString("error"));
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
                param.put("customer_id", Global.customer_id);
                param.put("id",id );
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void getData() {


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.flight_request_detail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                if (String.valueOf(response.charAt(0)).equalsIgnoreCase("["))
                    try {
                        Log.d("Responce", response);

                        JSONArray array = new JSONArray(response);


                        FlightRequestModel data = new Gson().fromJson(array.getString(0), FlightRequestModel.class);
                        relForm.setVisibility(View.GONE);
                        detail.setVisibility(View.VISIBLE);

                        txtflightdate.setText(data.getTravelDate());
                        txtflightname.setText(data.getFlightName());
                        txtflightno.setText(data.getFlightNo());
                        txtname.setText(Global.name);
                        id =  data.getId();

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
                param.put("customer_id", Global.customer_id);
                param.put("date", dateoftravel.getText().toString());
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void validate() {
        if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
            name.setError("Please enter your name");
        } else if (HelperUtilities.isEmptyOrNull(flightno.getText().toString())) {
            flightno.setError("Please enter your Flight no");
        } else if (HelperUtilities.isEmptyOrNull(flight_name.getText().toString())) {
            flight_name.setError("Please enter your flight name");
        } else if (HelperUtilities.isEmptyOrNull(dateoftravel.getText().toString())) {
            flight_name.setError("Please enter your Date of name");
        } else {
            flightcalender();
        }
    }

    private void flightcalender() {


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.flight_request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    Log.d("Responce", response);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {


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
                                        startActivity(new Intent(FlightRequestClander.this, MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        Global.diloge(FlightRequestClander.this, "Error", jsonObject.getString("result"));
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
                param.put("customer_id", Global.customer_id);
                param.put("flight_name", flight_name.getText().toString());
                param.put("flight_no", flightno.getText().toString());
                param.put("travel_date", dateoftravel.getText().toString());
                param.put("region", strregion);


                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
