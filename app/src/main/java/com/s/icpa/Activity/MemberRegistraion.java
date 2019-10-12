package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.assist.AssistStructure;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.s.icpa.Qpay.OrderInfo;
import com.s.icpa.Qpay.PaymentProcessing;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class MemberRegistraion extends AppCompatActivity {
    ImageButton birthday, anniversry;
    Calendar myCalendar = Calendar.getInstance();
    EditText edtBirthday, edtanniversry, name, email, oemail, mobile, address, password, cpassword, sapno,batch_no;
    LinearLayout layAnnie;
    private RadioGroup RgMeritalsts, Rgcurrentstats, RgDesignation, rgRegion,RgMemeberstatus,radiotstatus;


    String strMeritalstatus = "0", strcurrentstatus = ",", strDesignation = ",", strregion = ",", strMemberstatus="0" , strradiotstatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_registraion);

        getSupportActionBar().hide();

        edtBirthday = findViewById(R.id.edtBirthday);
        name = findViewById(R.id.Name);
        batch_no = findViewById(R.id.batch_no);
        sapno = findViewById(R.id.sapno);
        email = findViewById(R.id.pemail);
        oemail = findViewById(R.id.oEmail);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        password = findViewById(R.id.txtPassword);
        cpassword = findViewById(R.id.txtConfirmPassword);
        Rgcurrentstats = findViewById(R.id.radioCurrentstatus);
        RgMemeberstatus = findViewById(R.id.radioMember);
        radiotstatus = findViewById(R.id.radiotstatus);
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
                new DatePickerDialog(MemberRegistraion.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        anniversry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MemberRegistraion.this, dateannier, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        RadioButtonHandling();



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

        RgMemeberstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strMemberstatus = radioButton.getTag().toString();
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
        radiotstatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strradiotstatus = radioButton.getText().toString();
            }
        });
    }

    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public void register(View view) {

        int id = view.getId();

        if (id == R.id.linkLogin) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else if (id == R.id.btnRegister) {
            validate();
        }
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
            }
            else
            if (HelperUtilities.isEmptyOrNull(oemail.getText().toString())) {
                oemail.setError("Please enter your email");
            } else if (!HelperUtilities.isValidEmail(oemail.getText().toString())) {
                oemail.setError("Please enter a valid email");
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
            if (HelperUtilities.isEmptyOrNull(password.getText().toString())) {
                password.setError("Please enter your password");
            } else if (HelperUtilities.isShortPassword(password.getText().toString())) {
                password.setError("Your password must have at least 6 characters.");
            }
            else
            if (HelperUtilities.isEmptyOrNull(cpassword.getText().toString())) {
                cpassword.setError("Please confirm password");
            }
            else
            if (!(cpassword.getText().toString().equals(password.getText().toString()))) {

                cpassword.setError("The password doesn't match.");


            }else {
                Register();
            }

    }

    private void Register() {

        final ViewDialog progressDialog  = new ViewDialog(MemberRegistraion.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.signup, new Response.Listener<String>() {
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
                            builder = new AlertDialog.Builder(MemberRegistraion.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(MemberRegistraion.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully register")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        if (!strMemberstatus.equals("no")) {
                                            startActivity(new Intent(MemberRegistraion.this, LoginActivity.class));
                                            finish();
                                        }else {
                                            payment_gateway();
                                        }
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(MemberRegistraion.this,"Registration Error" , jsonObject.getString("error"));
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
                param.put("batch_no",batch_no.getText().toString());
                param.put("dob",edtBirthday.getText().toString());
                param.put("current_fleet",strDesignation  );
                param.put("designation",strDesignation  );
                param.put("region",strregion);
                param.put("member",strMemberstatus);
                param.put("status","0");
                param.put("sap_no",sapno.getText().toString());
                param.put("mobile",mobile.getText().toString());
                param.put("email",email.getText().toString());
                param.put("work_email",oemail.getText().toString());
                param.put("address",address.getText().toString());
                param.put("marital_status",strMeritalstatus);
                param.put("wedding_date",edtanniversry.getText().toString());
                param.put("password",password.getText().toString());
                param.put("emp_status",strradiotstatus);
                return param;
            }
    };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void payment_gateway() {

        final ViewDialog progressDialog  = new ViewDialog(MemberRegistraion.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.payment_gateway, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    Log.d("Responce",response);

                    JSONArray array = new JSONArray(response);
                    if (array.length()>0)
                    {
                        JSONObject object = array.getJSONObject(0);

                        createOrderInfo(object.getString("submerchantid"),
                                object.getString("submerchantname"),
                                object.getString("password"),
                                object.getString("qpay_id"));
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
                param.put("region",strregion);
                return param;
            }
    };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void createOrderInfo(String subId ,String subName ,String password , String mainId) {

/*
*
         * @class OrderInfo - Contains required variables to create a new order object.*/



        OrderInfo objOrderInfo = new OrderInfo();


/**
         * @param setMode
         *  Test – Test mode. QPay will not send transaction to bank gateway.
         *  Live – for testing in live mode. Transaction request will be sent to bank.*/



        objOrderInfo.setMode("test");

/**
         * @param setQpayID - Combination of the Qpay ID issued by QPayIndia and base64 value of order amount separated by ` symbol.
         */


        String orderAmount = "100";

        byte[] byt = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            byt = orderAmount.getBytes(StandardCharsets.US_ASCII);
        }
        String qpayId =  mainId+ "`" + new String(Base64.encodeToString(byt, Base64.DEFAULT));
        objOrderInfo.setQpayID(qpayId);

/**
         * @param setQpayPWD - The Password issued by QPayIndia.*/



        objOrderInfo.setQpayPWD(password);

/**
         * @param setSubmerchantID - Submerchant ID.*/


        objOrderInfo.setSubmerchantID(subId);

/**
         * @param setSubmerchantName - Name of Submerchant.*/



        objOrderInfo.setSubmerchantName(subName);


/**
         * @param name -  Name of the customer.*/



        objOrderInfo.setName(name.getText().toString());

/**
         * @param setAddress - Address of the customer.*/



        objOrderInfo.setAddress(address.getText().toString());

/**
         * @param  setCity - City of the customer.*/



        objOrderInfo.setCity(strregion);

/**
         * @param setState - State of the customer*/



        objOrderInfo.setState(strregion);

/**
         * @param setCountry - Country of the customer.*/



        objOrderInfo.setCountry("India");

/**
         * @param setPostal_code - Postal code of the customer.*/



        objOrderInfo.setPostal_code("000");


/*
         * @param setPhone - Phone of the customer.
*/



        objOrderInfo.setPhone(mobile.getText().toString());


/*
         * @param setEmail - Email ID of the customer.
*/



        objOrderInfo.setEmail(email.getText().toString());

/*
* @param setOrderID - A unique Transaction ID generated on the developers side.
*/



        objOrderInfo.setOrderID(UUID.randomUUID().toString().replace("-", "").substring(0, 16));

/*
* @param setPaymentOption - C / D / N / U (C = Credit Card, D = Debit Card, N = Net banking, U - UPI).
*/



        objOrderInfo.setPaymentOption("C,D,N,U");

/*
* @param setCurrencyCode - ISO Code for the currency of the payment - INR
*/



        objOrderInfo.setCurrencyCode("INR");

/*
* @param setResponseActivity - A Response Class need to be created on the developers side.
*/



        objOrderInfo.setResponseActivity("com.s.icpa.Qpay.PaymentResponse");

        Intent i = new Intent(getApplicationContext(), PaymentProcessing.class);
        i.putExtra("OrderInfo", objOrderInfo);
        startActivity(i);
        finish();

    }

}
