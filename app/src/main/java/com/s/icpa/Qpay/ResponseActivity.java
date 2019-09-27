package com.s.icpa.Qpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.s.icpa.R;

public class ResponseActivity extends Activity {
    TextView responseid, orderid, responsecode, message, paymentmode, transactiontype;
    ImageView imgPaymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responseactivity);

        String response = getIntent().getExtras().getString("response");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), Sample_MainActivity.class);
        startActivity(i);
        finish();

    }
}
