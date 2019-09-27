package com.s.icpa.Qpay;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response Model for Payment submission Call.
 */

public class PaymentResponse extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentresponse);
        String response = getIntent().getExtras().getString("response");
        if (isNetworkStatusAvialable(getApplicationContext())) {
            try {
                JSONObject objresonse = new JSONObject(response);
                String paymentResponse = objresonse.getString("ResponseCode");
                if (paymentResponse.equals("200") || paymentResponse.equals("100")) {
                    ((ImageView) findViewById(R.id.PaymentStatus)).setImageResource(R.drawable.success);
                } else {
                    ((ImageView) findViewById(R.id.PaymentStatus)).setImageResource(R.drawable.failed);
                }
                ((TextView) findViewById(R.id.txtMSPReferenceID)).setText(objresonse.getString("MSPReferenceID"));
                ((TextView) findViewById(R.id.txtMerchantOrderID)).setText(objresonse.getString("MerchantOrderID"));
                ((TextView) findViewById(R.id.txtResponseCode)).setText(objresonse.getString("ResponseCode"));
                ((TextView) findViewById(R.id.txtMessage)).setText(objresonse.getString("Message"));
                ((TextView) findViewById(R.id.txtPaymentMode)).setText(objresonse.getString("PaymentMode"));
                ((TextView) findViewById(R.id.txtTransactionType)).setText(objresonse.getString("TransactionType"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            InternetNotAvailable();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
        startActivity(i);
        finish();

    }

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
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
