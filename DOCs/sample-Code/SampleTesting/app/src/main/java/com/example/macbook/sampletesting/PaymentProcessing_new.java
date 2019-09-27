package com.example.macbook.sampletesting;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.macbook.qpaysdk.OrderInfo;
import com.example.macbook.qpaysdk.PaymentActivity;
import com.example.macbook.qpaysdk.PaymentResponse;
import com.example.macbook.qpaysdk.R.drawable;
import com.example.macbook.qpaysdk.R.id;
import com.example.macbook.qpaysdk.R.layout;

import org.json.JSONObject;

public class PaymentProcessing_new extends Activity {
    WebView browser;
    ProgressBar progressBar;
    protected String responseActivity;

    public PaymentProcessing_new() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.paymentprocessing);
        this.progressBar = (ProgressBar) this.findViewById(id.progressBar);
        Intent i = this.getIntent();
        OrderInfo_new objOrderInfo = (OrderInfo_new) i.getSerializableExtra("OrderInfo");
        this.processPayment(objOrderInfo);
    }

    public void processPayment(OrderInfo_new obj) {
        try {
            this.browser = (WebView) this.findViewById(id.webview);
            this.browser.addJavascriptInterface(new WebAppInterface(this), "Android");
            MyBrowser client = new MyBrowser();
            this.browser.setWebViewClient(client);
            this.browser.getSettings().setJavaScriptEnabled(true);
            this.browser.requestFocusFromTouch();
            this.browser.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            this.browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.browser.getSettings().setAppCacheEnabled(true);
            this.browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
            WebSettings webSettings = this.browser.getSettings();
            webSettings.setDomStorageEnabled(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setUseWideViewPort(true);
            webSettings.setSavePassword(true);
            webSettings.setSaveFormData(true);
            webSettings.setEnableSmoothTransition(true);
            String postData = "QPayID=" + obj.getQpayID() + "&QPayPWD=" + obj.getQpayPWD() + "&TransactionType=" + OrderInfo.getTransactionType() + "&OrderID=" + obj.getOrderID() + "&Currency=" + obj.getCurrencyCode() + "&Mode=" + obj.getMode() + "&PaymentPageRequired=" + OrderInfo.getPaymentPageRequired() + "&Paymentoption=" + obj.getPaymentOption() + "&name=" + (obj.getName() == null ? "" : obj.getName()) + "&address=" + (obj.getAddress() == null ? "" : obj.getAddress()) + "&city=" + (obj.getCity() == null ? "" : obj.getCity()) + "&state=" + (obj.getState() == null ? "" : obj.getState()) + "&country=" + (obj.getCountry() == null ? "" : obj.getCountry()) + "&postal_code=" + (obj.getPostal_code() == null ? "" : obj.getPostal_code()) + "&phone=" + (obj.getPhone() == null ? "" : obj.getPhone()) + "&email=" + (obj.getEmail() == null ? "" : obj.getEmail()) + "&ResponseURL=" + OrderInfo.getResponseURL();
            if (Build.VERSION.SDK_INT >= 11) {
                this.browser.setLayerType(1, (Paint) null);
            }

            if (isNetworkStatusAvialable(this.getApplicationContext())) {
                this.browser.postUrl(OrderInfo.getRequestURL(), postData.getBytes());
            } else {
                this.InternetNotAvailable();
            }

            this.responseActivity = obj.getResponseActivity();
            this.progressBar.setVisibility(View.GONE);
        } catch (Exception var5) {
            Toast.makeText(this.getApplicationContext(), var5.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this.getApplicationContext(), PaymentActivity.class);
        this.startActivity(i);
        this.finish();
    }

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null && netInfos.isConnected()) {
                return true;
            }
        }

        return false;
    }

    private void InternetNotAvailable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Internet connection. Make sure Wi-Fi or mobile data is turned on, then try again").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle(" ");
        alert.setIcon(drawable.wifioff);
        alert.show();
    }

    private class MyBrowser extends WebViewClient {
        private MyBrowser() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            this.mContext = c;
        }

        @JavascriptInterface
        public void PaymentResponse(String paymentResponse) {
            try {
                JSONObject response = new JSONObject(paymentResponse);

                Intent intent;
                try {
                    Class<?> c = Class.forName(responseActivity);
                    intent = (new Intent(getApplicationContext(), c)).putExtra("response", String.valueOf(response));
                    startActivity(intent);
                    finish();
                } catch (ClassNotFoundException var5) {
                    intent = (new Intent(getApplicationContext(), PaymentResponse.class)).putExtra("response", String.valueOf(response));
                    startActivity(intent);
                    finish();
                }
            } catch (Exception var6) {
                Toast.makeText(getApplicationContext(), var6.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", var6.getMessage());
            }

        }
    }
}
