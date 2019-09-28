package com.s.icpa.Qpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.s.icpa.R;

import org.json.JSONObject;

public class PaymentProcessing extends Activity {
    WebView browser;
    ProgressBar progressBar;
    protected String responseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentprocessing);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Intent i = getIntent();
        OrderInfo objOrderInfo = (OrderInfo) i.getSerializableExtra("OrderInfo");
        processPayment(objOrderInfo);
    }


    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */

        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * using java script interface capturing payment response from responseURL( response.aspx)
         */

        @JavascriptInterface
        public void PaymentResponse(String paymentResponse) {
            try {

                JSONObject response = new JSONObject(paymentResponse);

                try {

                    //Sending the JSON Obj to responseActivity

                    Class<?> c = Class.forName(responseActivity);
                    Intent intent = new Intent(getApplicationContext(), c).putExtra("response", String.valueOf(response));
                    startActivity(intent);
                    finish();

                } catch (ClassNotFoundException ignored) {

                    //Sending the responseActivity to defaultActivity

                    Intent intent = new Intent(getApplicationContext(), PaymentResponse.class).putExtra("response", String.valueOf(response));
                    startActivity(intent);
                    finish();
                }

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", ex.getMessage());
            }
        }
    }

    public void processPayment(OrderInfo obj) {

        try {
            browser = (WebView) findViewById(R.id.webview);
            browser.addJavascriptInterface(new WebAppInterface(this), "Android");
            MyBrowser client = new MyBrowser();
            browser.setWebViewClient(client);
            browser.getSettings().setJavaScriptEnabled(true);
            browser.requestFocusFromTouch();
            browser.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            browser.getSettings().setAppCacheEnabled(true);
            browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            WebSettings webSettings = browser.getSettings();
            webSettings.setDomStorageEnabled(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setSavePassword(true);
            webSettings.setSaveFormData(true);
            webSettings.setEnableSmoothTransition(true);


            String postData = "QPayID=" + (obj.getQpayID()) +
                    "&QPayPWD=" + (obj.getQpayPWD()) +
                    "&Submerchantid=" + (obj.getSubmerchantID()) +
                    "&Submerchantname=" + (obj.getSubmerchantName()) +
                    "&TransactionType=" + (obj.getTransactionType()) +
                    "&OrderID=" + (obj.getOrderID()) +
                    "&Currency=" + (obj.getCurrencyCode()) +
                    "&Mode=" + (obj.getMode()) +
                    "&PaymentPageRequired=" + (obj.getPaymentPageRequired()) +
                    "&Paymentoption=" + (obj.getPaymentOption()) +
                    "&name=" + (obj.getName() == null ? "" : obj.getName()) +
                    "&address=" + (obj.getAddress() == null ? "" : obj.getAddress()) +
                    "&city=" + (obj.getCity() == null ? "" : obj.getCity()) +
                    "&state=" + (obj.getState() == null ? "" : obj.getState()) +
                    "&country=" + (obj.getCountry() == null ? "" : obj.getCountry()) +
                    "&postal_code=" + (obj.getPostal_code() == null ? "" : obj.getPostal_code()) +
                    "&phone=" + (obj.getPhone() == null ? "" : obj.getPhone()) +
                    "&email=" + (obj.getEmail() == null ? "" : obj.getEmail()) +
                    "&ResponseURL=" + (obj.getResponseURL());


            if (Build.VERSION.SDK_INT >= 11) {
                browser.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }


            if (isNetworkStatusAvialable(getApplicationContext())) {
                browser.postUrl(obj.getRequestURL(), postData.getBytes());
            } else {
                InternetNotAvailable();

            }
            responseActivity = obj.getResponseActivity();
            progressBar.setVisibility(View.GONE);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
