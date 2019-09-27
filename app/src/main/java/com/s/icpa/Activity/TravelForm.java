package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TravelForm extends AppCompatActivity {

    EditText  name, email, mobile, staffno, flightno, dateoftravel, Sector,leavesanctionedfrom,leavesanctionedto ,edtWeekOff;
    private RadioGroup  Rgcurrentstats ,flighttype , rgWeekoff;

    String  strcurrentstatus = "," ,strflighttype = "," , strWeekoff = "No";

    Button btnSubmit ;
    ImageButton btndateoftravel ,btndate;
    Calendar myCalendar = Calendar.getInstance();
    LinearLayout layAnnie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_form);
        layAnnie = findViewById(R.id.layAnnie);

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
        btndateoftravel = findViewById(R.id.btndateoftravel);
        btndate = findViewById(R.id.date);
        Sector = findViewById(R.id.Sector);
        edtWeekOff = findViewById(R.id.edtWeekoffdate);
        rgWeekoff = findViewById(R.id.RgWeekOff);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateoftravel.setText(sdf.format(myCalendar.getTime()));

            }

        };
        final DatePickerDialog.OnDateSetListener weekofdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                edtWeekOff.setText(sdf.format(myCalendar.getTime()));
                strWeekoff = sdf.format(myCalendar.getTime());
            }

        };

        btndateoftravel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TravelForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btndate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TravelForm.this, weekofdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        rgWeekoff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Married) {
                    layAnnie.setVisibility(View.GONE);
                } else {
                    layAnnie.setVisibility(View.VISIBLE);
                }
            }
        });



        Rgcurrentstats = findViewById(R.id.radioCurrentstatus);
        Rgcurrentstats.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strcurrentstatus = radioButton.getText().toString();
            }
        });
        flighttype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strflighttype = radioButton.getText().toString();
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
        }else  if (HelperUtilities.isEmptyOrNull(Sector.getText().toString())) {
            Sector.setError("Please enter your Flight no");
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
                param.put("flight_type",strflighttype);
                param.put("flight_no",flightno.getText().toString());
                param.put("travel_date",dateoftravel.getText().toString());
                param.put("staff_no",staffno.getText().toString());
                param.put("email",email.getText().toString());
                param.put("mobile",mobile.getText().toString());
                param.put("leave_from",leavesanctionedfrom.getText().toString());
                param.put("leave_to",leavesanctionedto.getText().toString());
                param.put("current_fleet",strcurrentstatus);
                param.put("region",Global.Region);
                param.put("sector",Sector.getText().toString());
                param.put("weekly_off",strWeekoff);

                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
