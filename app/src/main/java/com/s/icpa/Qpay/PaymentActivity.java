package com.s.icpa.Qpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.s.icpa.R;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/* PaymentActivity  Lets user to Make an Order
 *
 */

public class PaymentActivity extends Activity {

    Button pay, chkorderstatus;
    Spinner modeSpinner;
    TextInputLayout inputAmountLayout, inputQpayIDLayout, inputQpayPWDLayout, inputSubMerchantIDLayout, inputSubMerchantNameLayout, inputPhoneLayout, inputEmailLayout;
    EditText amount, qpayid, qpaypwd, submerchantid, submerchantname, name, address, city, state, country, postal_code, phone, email;
    private static String environment = null;


    ProgressBar progressBar;


    private static final String[] mode = {"test", "live"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentactivity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        chkorderstatus = (Button) findViewById(R.id.chkOrderStatus);
        chkorderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OrderStatus.class);
                startActivity(i);
                finish();
            }
        });


        inputAmountLayout = (TextInputLayout) findViewById(R.id.amountLayout);
        amount = findViewById(R.id.amount);
        amount.setSelection(amount.getText().toString().trim().length());
        amount.addTextChangedListener(new MyTextWatcher(amount));

        inputQpayIDLayout = (TextInputLayout) findViewById(R.id.qpayidLayout);
        qpayid = findViewById(R.id.qpayid);
        qpayid.setSelection(qpayid.getText().toString().trim().length());
        qpayid.addTextChangedListener(new MyTextWatcher(qpayid));

        inputQpayPWDLayout = (TextInputLayout) findViewById(R.id.qpaypwdLayout);
        qpaypwd = findViewById(R.id.qpaypwd);
        qpaypwd.setSelection(qpaypwd.getText().toString().trim().length());

        inputSubMerchantIDLayout = (TextInputLayout) findViewById(R.id.submerchantIdLayout);
        submerchantid = findViewById(R.id.submerchantId);
        submerchantid.setSelection(submerchantid.getText().toString().trim().length());
        submerchantid.addTextChangedListener(new MyTextWatcher(submerchantid));

        inputSubMerchantNameLayout = (TextInputLayout) findViewById(R.id.submerchantNameLayout);
        submerchantname = findViewById(R.id.submerchantName);
        submerchantname.setSelection(submerchantname.getText().toString().trim().length());

        name = findViewById(R.id.name);
        name.setSelection(name.getText().toString().trim().length());
        name.addTextChangedListener(new MyTextWatcher(name));


        address = findViewById(R.id.address);
        address.setSelection(address.getText().toString().trim().length());
        address.addTextChangedListener(new MyTextWatcher(address));


        city = findViewById(R.id.city);
        city.setSelection(city.getText().toString().trim().length());
        city.addTextChangedListener(new MyTextWatcher(city));


        state = findViewById(R.id.state);
        state.setSelection(state.getText().toString().trim().length());
        state.addTextChangedListener(new MyTextWatcher(state));


        country = findViewById(R.id.country);
        country.setSelection(country.getText().toString().trim().length());
        country.addTextChangedListener(new MyTextWatcher(country));


        postal_code = findViewById(R.id.postal_code);
        postal_code.setSelection(postal_code.getText().toString().trim().length());
        postal_code.addTextChangedListener(new MyTextWatcher(postal_code));


        inputPhoneLayout = (TextInputLayout) findViewById(R.id.phoneLayout);

        phone = findViewById(R.id.phone);
        phone.setSelection(phone.getText().toString().trim().length());
        phone.addTextChangedListener(new MyTextWatcher(phone));

        inputEmailLayout = (TextInputLayout) findViewById(R.id.emaillayout);

        email = findViewById(R.id.email);
        email.setSelection(email.getText().toString().trim().length());
        email.addTextChangedListener(new MyTextWatcher(email));


        modeSpinner = (Spinner) findViewById(R.id.mode_spinner);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                environment = mode[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkStatusAvialable(getApplicationContext())) {
                    createOrderInfo();

                } else {
                    InternetNotAvailable();

                }

            }
        });


    }


    public void createOrderInfo() {


        /**
         * @class OrderInfo - Contains required variables to create a new order object.
         */

        OrderInfo objOrderInfo = new OrderInfo();


        /**
         * @param setMode
         *  Test – Test mode. QPay will not send transaction to bank gateway.
         *  Live – for testing in live mode. Transaction request will be sent to bank.
         */

        objOrderInfo.setMode(environment);

        /**
         * @param setQpayID - Combination of the Qpay ID issued by QPayIndia and base64 value of order amount separated by ` symbol.
         */

        String orderAmount = amount.getText().toString();

        byte[] byt = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            byt = orderAmount.getBytes(StandardCharsets.US_ASCII);
        }
        String qpayId = qpayid.getText().toString() + "`" + new String(Base64.encodeToString(byt, Base64.DEFAULT));
        objOrderInfo.setQpayID(qpayId);

        /**
         * @param setQpayPWD - The Password issued by QPayIndia.
         */

        objOrderInfo.setQpayPWD(qpaypwd.getText().toString());

        /**
         * @param setSubmerchantID - Submerchant ID.
         */
        objOrderInfo.setSubmerchantID(submerchantid.getText().toString());

        /**
         * @param setSubmerchantName - Name of Submerchant.
         */

        objOrderInfo.setSubmerchantName(submerchantname.getText().toString());


        /**
         * @param name -  Name of the customer.
         */

        objOrderInfo.setName(name.getText().toString());

        /**
         * @param setAddress - Address of the customer.
         */

        objOrderInfo.setAddress(address.getText().toString());

        /**
         * @param  setCity - City of the customer.
         */

        objOrderInfo.setCity(city.getText().toString());

        /**
         * @param setState - State of the customer.
         */

        objOrderInfo.setState(state.getText().toString());

        /**
         * @param setCountry - Country of the customer.
         */

        objOrderInfo.setCountry(country.getText().toString());

        /**
         * @param setPostal_code - Postal code of the customer.
         */

        objOrderInfo.setPostal_code(postal_code.getText().toString());

        /**
         * @param setPhone - Phone of the customer.
         */

        objOrderInfo.setPhone(phone.getText().toString());

        /**
         * @param setEmail - Email ID of the customer.
         */

        objOrderInfo.setEmail(email.getText().toString());

        /** @param setOrderID - A unique Transaction ID generated on the developers side.
         */

        objOrderInfo.setOrderID(UUID.randomUUID().toString().replace("-", "").substring(0, 16));

        /** @param setPaymentOption - C / D / N / U (C = Credit Card, D = Debit Card, N = Net banking, U - UPI).
         */

        objOrderInfo.setPaymentOption("C,D,N,U");

        /** @param setCurrencyCode - ISO Code for the currency of the payment - INR
         */

        objOrderInfo.setCurrencyCode("INR");

        /** @param setResponseActivity - A Response Class need to be created on the developers side.
         */

        objOrderInfo.setResponseActivity("com.example.macbook.qpaysdk.PaymentResponse");

        Intent i = new Intent(getApplicationContext(), PaymentProcessing.class);
        i.putExtra("OrderInfo", objOrderInfo);
        startActivity(i);
        finish();

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        public MyTextWatcher(View view) {
            this.view = view;

        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    }

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            @SuppressLint("MissingPermission") NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }

    private void InternetNotAvailable() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("No Internet connection. Make sure Wi-Fi or mobile data is turned on, then try again")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.setTitle(" ");
        alert.setIcon(R.drawable.wifioff);
        alert.show();
    }

}
