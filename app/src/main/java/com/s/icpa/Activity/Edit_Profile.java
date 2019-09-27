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
import com.google.gson.Gson;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.HelperUtils.HelperUtilities;
import com.s.icpa.Model.LoginModel;
import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Edit_Profile extends AppCompatActivity {

    ImageButton birthday, anniversry;
    Calendar myCalendar = Calendar.getInstance();
    EditText edtBirthday, edtanniversry, name, email, oemail, mobile, address, password, cpassword, sapno, batch_no;
    LinearLayout layAnnie;
    RadioGroup RgMeritalsts, Rgcurrentstats, RgDesignation, rgRegion;

    RadioButton Single,Married,officer,captain,commander,B_777,B_787,B_747,A_320,NR,SR,WR,ER;

    String strMeritalstatus = "0", strcurrentstatus = ",", strDesignation = ",", strregion = ",";
    HashMap<String, String> user;
    SQLiteHandler db = new SQLiteHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edtBirthday = findViewById(R.id.edtBirthday);
        name = findViewById(R.id.Name);
        Single = findViewById(R.id.Single);
        Married = findViewById(R.id.Married);
        officer = findViewById(R.id.officer);
        captain = findViewById(R.id.captain);
        commander = findViewById(R.id.commander);
        commander = findViewById(R.id.commander);
        batch_no = findViewById(R.id.batch_no);
        B_777 = findViewById(R.id.B_777);
        B_787 = findViewById(R.id.B_787);
        B_747 = findViewById(R.id.B_747);
        A_320 = findViewById(R.id.A_320);
        NR = findViewById(R.id.nr);
        SR = findViewById(R.id.sr);
        WR = findViewById(R.id.wr);
        ER = findViewById(R.id.er);
        sapno = findViewById(R.id.sapno);
        email = findViewById(R.id.pemail);
        oemail = findViewById(R.id.oEmail);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        password = findViewById(R.id.txtPassword);
        cpassword = findViewById(R.id.txtConfirmPassword);
        Rgcurrentstats = findViewById(R.id.radioCurrentstatus);
        rgRegion = findViewById(R.id.Radio_region);
        RgDesignation = findViewById(R.id.radioDesignation);
        layAnnie = findViewById(R.id.layAnnie);
        RgMeritalsts = findViewById(R.id.RgMeritalsts);
        birthday = findViewById(R.id.Birthday);
        edtanniversry = findViewById(R.id.edtanniversseryday);
        anniversry = findViewById(R.id.anniversery);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtBirthday);
            }

        };
        final DatePickerDialog.OnDateSetListener dateannier = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(edtanniversry);
            }

        };
        edtanniversry.setText("..");

        birthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Edit_Profile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        anniversry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Edit_Profile.this, dateannier, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        RadioButtonHandling();
        getData();
    }

    private void RadioButtonHandling() {

        RgMeritalsts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Married) {
                    layAnnie.setVisibility(View.VISIBLE);
                    strMeritalstatus = "1";
                } else {
                    layAnnie.setVisibility(View.GONE);
                    strMeritalstatus = "0";
                    edtanniversry.setText("..");
                }
            }
        });

        Rgcurrentstats.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strcurrentstatus = radioButton.getText().toString();
            }
        });
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
    }

    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }


    private void getData() {
        this.user = this.db.getUserDetails();

        name.setText( user.get("name"));
        batch_no.setText( user.get("batch_no"));
        edtBirthday.setText(user.get("dob"));
        edtanniversry.setText( user.get("wedding_date"));
        email.setText( user.get("email"));
        oemail.setText( user.get("work_email"));
        mobile.setText( user.get("mobile"));

        sapno.setText(user.get("sap_no"));
        address.setText( user.get("address"));

        if (user.get("marital_status").equals("1")) {
            Married.setChecked(true);
        } else {
            Single.setChecked(true);
        }

        strcurrentstatus=user.get("current_fleet");
        strregion=user.get("region");
        strDesignation=user.get("Designation");


        String strRegion = user.get("region");

        assert strRegion != null;
        switch (strRegion) {
            case "NR":
                NR.setChecked(true);
                break;
            case "WR":
                WR.setChecked(true);
                break;
            case "SR":
                SR.setChecked(true);
                break;
            case "ER":
                ER.setChecked(true);
                break;
        }

        String strDesignation = user.get("Designation");

        assert strDesignation != null;
        switch (strDesignation) {
            case "First Officer":
                officer .setChecked(true);
                break;
            case "Captain":
                captain.setChecked(true);
                break;
            case "Commander":
                commander.setChecked(true);
                break;

        }


        String strFleet = user.get("current_fleet");


        assert strFleet != null;
        switch (strFleet) {
            case "B-747":
                B_747.setChecked(true);
                break;
            case "B-777":
                B_777.setChecked(true);
                break;
            case "B-787":
                B_787.setChecked(true);
                break;
            case "A-320":
                A_320.setChecked(true);
                break;
        }


    }

    public void editprofile(View view) {
        int id = view.getId();

        if (id == R.id.btnUpdate){
            validate();
        }
    }

    private void validate() {

        if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
            name.setError("Please enter your name");
        }
        else if (HelperUtilities.isEmptyOrNull(sapno.getText().toString())) {
            sapno.setError("Please enter sap no");
        }
        else
        if (HelperUtilities.isEmptyOrNull(batch_no.getText().toString())) {
            batch_no.setError("Please enter batch_no");
        }
        else
        if (HelperUtilities.isEmptyOrNull(edtBirthday.getText().toString())) {
            batch_no.setError("Please enter Birth Day");
        }
        else
       {
            Register();
        }

    }

    private void Register() {

        final ViewDialog progressDialog  = new ViewDialog(Edit_Profile.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.update_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    Log.d("Responce",response);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status"))
                    {
                        db.deleteUsers();
                        LoginModel data = new Gson().fromJson(response, LoginModel.class);
                        db.addUser(
                                data.getCustomerId(),
                                data.getBatchNo(),
                                data.getEmail(),
                                data.getMobile(),
                                data.getName(),
                                " ",
                                data.getDob(),
                                data.getCurrentFleet(),
                                data.getSapNo(),
                                data.getWorkEmail(),
                                data.getAddress(),
                                data.getMaritalStatus(),
                                data.getWeddingDate(),
                                "0",
                                strDesignation,
                                data.getRegion()
                        );
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(Edit_Profile.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Edit_Profile.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully Update your profile")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(Edit_Profile.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(Edit_Profile.this,"Error" , jsonObject.getString("error"));
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
                param.put("name",name.getText().toString());
                param.put("batch_no",batch_no.getText().toString());
                param.put("dob",edtBirthday.getText().toString());
                param.put("current_fleet",strcurrentstatus);
                param.put("designation",strDesignation);
                param.put("region",strregion);
                param.put("sap_no",sapno.getText().toString());
                param.put("mobile",mobile.getText().toString());
                param.put("email",email.getText().toString());
                param.put("work_email",oemail.getText().toString());
                param.put("address",address.getText().toString());
                param.put("marital_status",strMeritalstatus);
                param.put("wedding_date",edtanniversry.getText().toString());
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
