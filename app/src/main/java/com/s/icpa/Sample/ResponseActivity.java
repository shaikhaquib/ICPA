package com.s.icpa.Sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.s.icpa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseActivity extends Activity {
    TextView responseid, orderid, responsecode, message, paymentmode, transactiontype;
    ImageView imgPaymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responseactivity);

        String response = getIntent().getExtras().getString("response");
        try {
            JSONObject objresonse = new JSONObject(response);
            String paymentResponse = objresonse.getString("ResponseCode");
            if (paymentResponse.equals("200") || paymentResponse.equals("100")) {
                ((ImageView) findViewById(com.example.macbook.qpaysdk.R.id.PaymentStatus)).setImageResource(com.example.macbook.qpaysdk.R.drawable.success);
            } else {
                ((ImageView) findViewById(com.example.macbook.qpaysdk.R.id.PaymentStatus)).setImageResource(com.example.macbook.qpaysdk.R.drawable.failed);
            }
            ((TextView) findViewById(com.example.macbook.qpaysdk.R.id.txtMSPReferenceID)).setText(objresonse.getString("MSPReferenceID"));
            ((TextView) findViewById(com.example.macbook.qpaysdk.R.id.txtMerchantOrderID)).setText(objresonse.getString("MerchantOrderID"));
            ((TextView) findViewById(com.example.macbook.qpaysdk.R.id.txtResponseCode)).setText(objresonse.getString("ResponseCode"));
            ((TextView) findViewById(com.example.macbook.qpaysdk.R.id.txtMessage)).setText(objresonse.getString("Message"));
            ((TextView) findViewById(com.example.macbook.qpaysdk.R.id.txtPaymentMode)).setText(objresonse.getString("PaymentMode"));
            ((TextView) findViewById(com.example.macbook.qpaysdk.R.id.txtTransactionType)).setText(objresonse.getString("TransactionType"));


            // Write your logic here.

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Sample_MainActivity.class);
        startActivity(i);
        finish();

    }
}
