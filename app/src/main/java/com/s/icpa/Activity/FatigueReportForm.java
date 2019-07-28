package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FatigueReportForm extends AppCompatActivity {

    LinearLayout Fr1, Fr2, Fr3, Fr4;
    EditText name, empno, local_report_date, local_report_time, duty_desc,
            fof, fot, hrt, aircraft_type, aircraft_no, location, description, awake, sleep,other_comment,what_could_be_done,what_did_u_do;
    RadioGroup rgfpd, rghotel, rghome,rgdisrupt,rgpersonal;
    String strfpd, strhome, strhotel,strdisrupt,strpersonal;

    ImageButton btn_report_date, btn_report_time;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatigue_report_form);


        //fr1

        name = findViewById(R.id.name);
        empno = findViewById(R.id.empno);


        //fr2

        btn_report_date = findViewById(R.id.btn_report_date);
        btn_report_time = findViewById(R.id.btn_report_time);
        local_report_date = findViewById(R.id.report_date);
        local_report_time = findViewById(R.id.report_time);
        duty_desc = findViewById(R.id.duty_desc);
        fof = findViewById(R.id.fof);
        fot = findViewById(R.id.fot);
        hrt = findViewById(R.id.hrt);
        aircraft_type = findViewById(R.id.aircraft_type);
        aircraft_no = findViewById(R.id.aircraft_no);
        location = findViewById(R.id.location);

        //fr3

        description = findViewById(R.id.description);

        //fr4


        awake = findViewById(R.id.awake);
        sleep = findViewById(R.id.sleep);
        what_did_u_do = findViewById(R.id.what_did_u_do);
        what_could_be_done = findViewById(R.id.what_could_be_done);
        other_comment = findViewById(R.id.other_comment);
        rgfpd = findViewById(R.id.fpd);
        rghome = findViewById(R.id.home);
        rghotel = findViewById(R.id.hotel);
        rgdisrupt = findViewById(R.id.disrupt);
        rgpersonal = findViewById(R.id.personal);

        rgfpd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strfpd = radioButton.getText().toString();
            }
        });
        rgpersonal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strpersonal = radioButton.getText().toString();
            }
        });
        rghotel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strhotel = radioButton.getText().toString();
            }
        });
        rghome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strhome = radioButton.getText().toString();
            }
        });
        rgdisrupt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strdisrupt = radioButton.getText().toString();
            }
        });


        Fr1 = findViewById(R.id.fr1);
        Fr2 = findViewById(R.id.fr2);
        Fr3 = findViewById(R.id.fr3);
        Fr4 = findViewById(R.id.fr4);

        // Date Picker


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(local_report_date);
            }

        };
        btn_report_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FatigueReportForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_report_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FatigueReportForm.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        local_report_time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    private void updateLabel(EditText editText) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public void backbutton(View view) {
        int id = view.getId();

        if (id == R.id.fr2Back) {
            Fr1.setVisibility(View.VISIBLE);
            Fr2.setVisibility(View.GONE);
        } else if (id == R.id.fr3Back) {
            Fr2.setVisibility(View.VISIBLE);
            Fr3.setVisibility(View.GONE);
        } else if (id == R.id.fr4back) {
            Fr3.setVisibility(View.VISIBLE);
            Fr4.setVisibility(View.GONE);
        }
    }

    public void nextbutton(View view) {

        int id = view.getId();

        if (id == R.id.fr1btnNext) {
            validateFR1();
        } else if (id == R.id.fr2Next) {
            validateFR2();
        } else if (id == R.id.fr3Next) {
            validateFR3();
        } else if (id == R.id.submit) {
            validateFR4();
        }

    }

    private void validateFR4() {
        if (sleep.getText().toString().isEmpty()) {
            sleep.setError("Field Required");
        } else if (awake.getText().toString().isEmpty()) {
            awake.setError("Field Required");
        }else if (what_did_u_do.getText().toString().isEmpty()) {
            what_did_u_do.setError("Field Required");
        }else if (what_could_be_done.getText().toString().isEmpty()) {
            what_could_be_done.setError("Field Required");
        }else if (other_comment.getText().toString().isEmpty()) {
            other_comment.setError("Field Required");
        } else {
            submitDATA();
        }
    }


    private void validateFR3() {
        if (description.getText().toString().isEmpty()) {
            description.setError("Field Required");
        } else {
            Fr3.setVisibility(View.GONE);
            Fr4.setVisibility(View.VISIBLE);
        }
    }

    private void validateFR2() {
        if (local_report_time.getText().toString().isEmpty()) {
            local_report_time.setError("Field Required");
        } else if (local_report_date.getText().toString().isEmpty()) {
            local_report_date.setError("Field Required");
        } else if (duty_desc.getText().toString().isEmpty()) {
            duty_desc.setError("Field Required");
        } else if (fof.getText().toString().isEmpty()) {
            fof.setError("Field Required");
        } else if (hrt.getText().toString().isEmpty()) {
            hrt.setError("Field Required");
        } else if (aircraft_type.getText().toString().isEmpty()) {
            aircraft_type.setError("Field Required");
        } else if (aircraft_no.getText().toString().isEmpty()) {
            aircraft_no.setError("Field Required");
        } else if (location.getText().toString().isEmpty()) {
            location.setError("Field Required");
        } else {
            Fr3.setVisibility(View.VISIBLE);
            Fr2.setVisibility(View.GONE);
        }
    }

    private void validateFR1() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Field Required");
        } else if (empno.getText().toString().isEmpty()) {
            empno.setError("Field Required");
        } else {
            Fr2.setVisibility(View.VISIBLE);
            Fr1.setVisibility(View.GONE);
        }

    }


    private void submitDATA() {

        final ViewDialog progressDialog = new ViewDialog(FatigueReportForm.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.frf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    Log.d("Responce", response);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(FatigueReportForm.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(FatigueReportForm.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully added your request")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(FatigueReportForm.this, MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        Global.diloge(FatigueReportForm.this, "Error", jsonObject.getString("error"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Global.diloge(FatigueReportForm.this,"Some thing went wrong!","Please Check weather have field/checked all field");
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customer_id);
                param.put("region", Global.Region);
                param.put("name", name.getText().toString());
                param.put("employee_no", empno.getText().toString());
                param.put("local_report_date", local_report_date.getText().toString());
                param.put("local_report_time", local_report_time.getText().toString());
                param.put("duty_desc", duty_desc.getText().toString());
                param.put("fof", fof.getText().toString());
                param.put("fot", fot.getText().toString());
                param.put("hrt", hrt.getText().toString());
                param.put("disrupt", strdisrupt);
                param.put("aircraft_type", aircraft_type.getText().toString());
                param.put("aircraft_no", aircraft_no.getText().toString());
                param.put("location", location.getText().toString());
                param.put("description", description.getText().toString());
                param.put("fpd", strfpd);
                param.put("hotel", strhotel);
                param.put("home", strhome);
                param.put("awake", awake.getText().toString());
                param.put("sleep", sleep.getText().toString());
                param.put("personal",strpersonal);
                param.put("what_did_you_do", what_did_u_do.getText().toString());
                param.put("what_could_be_done", what_could_be_done.getText().toString());
                param.put("other_comment", other_comment.getText().toString());


                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
