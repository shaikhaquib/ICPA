package com.s.icpa.Qpay;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * To check the Transaction Status of a particular order.
 */

public class OrderStatus extends Activity {

    TextView orderstatus;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderstatus);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ((Button) findViewById(R.id.btnSubmit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkStatusAvialable(getApplicationContext())) {
                    getPaymentDetails();
                    progressBar.setVisibility(View.VISIBLE);

                } else {
                    InternetNotAvailable();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }


    public void getPaymentDetails() {
        try {
            /*
             * @param orderNumber your unique order number
             */
            JSONObject productJson = new JSONObject();
            productJson.put("OrderID", ((EditText) findViewById(R.id.orderid)).getText().toString());
            StringEntity requestData = new StringEntity(productJson.toString());

            new AsyncHttpClient().post(getApplicationContext(), OrderInfo.getGetPaymentDetails(), requestData, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                    progressBar.setVisibility(View.GONE);

                    try {
                        String result = "", responseMessage = "", paymentStatus = "",
                                paymentMode = "", orderAmount = "", paymentDate = "",
                                paymentOption = "", MSPReferenceID = "", paymentDescription = "";
                        JSONArray objResponse = new JSONArray(response.getString("d"));
                        JSONObject row = objResponse.getJSONObject(0);

                        result = row.getString("Result");
                        responseMessage = row.getString("Message");
                        progressBar.setVisibility(View.VISIBLE);

                        // if order not found

                        if (result == "0") {
                            progressBar.setVisibility(View.INVISIBLE);
                            ScrollView sc = (ScrollView) findViewById(R.id.orderView);
                            sc.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), responseMessage, Toast.LENGTH_LONG).cancel();
                            ((TextView)findViewById(R.id.noOrder)).setText(responseMessage);
                            ((TextView)findViewById(R.id.noOrder)).setVisibility(TextView.VISIBLE);
                            return;

                        }

                        // if order found

                        else if (result == "1") {
                            ScrollView sc = (ScrollView) findViewById(R.id.orderView);
                            sc.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                            paymentStatus = row.getString("PaymentStatus");
                            paymentMode = row.getString("PaymentMode");
                            orderAmount = row.getString("OrderAmount");
                            paymentDate = row.getString("PaymentDate");
                            paymentOption = row.getString("PaymentOption");
                            MSPReferenceID = row.getString("MSPReferenceID");
                            paymentDescription = row.getString("PaymentDescription");

                            ((TextView) findViewById(R.id.paymentDescription)).setText(paymentDescription);
                            ((TextView) findViewById(R.id.orderAmount)).setText(orderAmount);
                            ((TextView) findViewById(R.id.MSPReferenceID)).setText(MSPReferenceID);
                            ((TextView) findViewById(R.id.paymentStatus)).setText(paymentStatus);


                            if (paymentStatus.equalsIgnoreCase("paid")) {

                                ((TextView) findViewById(R.id.paymentOption)).setText(paymentOption);
                                ((TextView) findViewById(R.id.paymentDate)).setText(paymentDate);
                                ((TextView) findViewById(R.id.paymentMode)).setText(paymentMode);
                                ((TextView) findViewById(R.id.paymentOption)).setVisibility(TextView.VISIBLE);
                                ((TextView) findViewById(R.id.paymentDate)).setVisibility(TextView.VISIBLE);
                                ((TextView) findViewById(R.id.paymentMode)).setVisibility(TextView.VISIBLE);
                                ((TextView)findViewById(R.id.noOrder)).setVisibility(TextView.GONE);


                            } else

                            {
                                ((TextView) findViewById(R.id.paymentOption)).setVisibility(TextView.INVISIBLE);
                                ((TextView) findViewById(R.id.paymentDate)).setVisibility(TextView.INVISIBLE);
                                ((TextView) findViewById(R.id.paymentMode)).setVisibility(TextView.INVISIBLE);
                                ((TextView)findViewById(R.id.noOrder)).setVisibility(TextView.GONE);


                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ActivityNotFoundException a) {
                        a.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
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
