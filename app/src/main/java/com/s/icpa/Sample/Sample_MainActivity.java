package com.s.icpa.Sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.macbook.qpaysdk.OrderInfo;
import com.s.icpa.R;
import com.example.macbook.qpaysdk.PaymentProcessing;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Sample_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_main);

        final EditText amountET = (EditText) findViewById(R.id.amount);
        Button pay = (Button) findViewById(R.id.payHere);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                OrderInfo orderInfo = new OrderInfo();
                String orderAmount = "10";

                byte[] data = new byte[0];
                try {
                    data = orderAmount.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String base64 = Base64.encodeToString(data, Base64.NO_WRAP);

                String qpayId = "icpaapiacc"+"`" + base64;

                OrderInfo objOrderInfo = new OrderInfo();

                objOrderInfo.setMode("test");
                objOrderInfo.setQpayID(qpayId.replaceAll("[\\\r\\\n]+",""));
                objOrderInfo.setQpayPWD("hgf!1234");


                String planHash = "5Xu2pMqk7zzf+brn3CjrjvMkmsQ44XqIl2CIFM4Cpi+Ja8umcB7I|https://pg.qpayindia.com/wwws/Payment/PaymentDetails.aspx|"+qpayId+"|hgf!1234|PURCHASE|hsgdgd|INR|test|CACxc|test@hsh.com|012345679";
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-512");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                byte[] digest = md.digest(planHash.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < digest.length; i++) {
                    sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                }
                System.out.println(sb);

                // Enter Your Unique Order ID.
                objOrderInfo.setOrderID(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
                objOrderInfo.setCurrencyCode("INR");
                objOrderInfo.setPaymentOption("C,D,U");
                objOrderInfo.setResponseActivity("com.example.macbook.sampletesting.ResponseActivity");
                objOrderInfo.setName("John doe");
                objOrderInfo.setAddress("6th Cross Street");
                objOrderInfo.setCity("Chennai");
                objOrderInfo.setState("Tamil Nadu");
                objOrderInfo.setCountry("India");
                objOrderInfo.setPostal_code("600020");
                objOrderInfo.setPhone("9940620019");
                objOrderInfo.setEmail("app@qpayindia.com");

                Intent i = new Intent(getApplicationContext(), PaymentProcessing.class);
                i.putExtra("OrderInfo", objOrderInfo);
                startActivity(i);
                finish();
            }
        });


    }
}
