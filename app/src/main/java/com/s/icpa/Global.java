package com.s.icpa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



/**
 * Created by Avinash on 13-Oct-17.
 */

public class Global {
    public static int remaining;
    public static int capacity = 0;
    public static int sending_rate = 0;
    public static int sent = 0;
    public static int current_count = 0;
    public static String customer_id;
    public static String Region;
    public static String name;
    public static String token ;
    public static String empStatus;
    public static String empDesig;
    public static String empemail;
    public static String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    public  static List<String> eList =new ArrayList<>();
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;


    public static final String KEY_SESSION = "session";
    public static final String PREF_FILE_NAME = ".my_pref_file";

    public static final String PREF_TOKEN = "Token";
    public static final String PREF_UID = ".Uid";

    public static String error_code;
    public static String Email;
    public static String mobile;


    public static void diloge(final Context context ,String Title ,String Message){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setCancelable(false);
        builder.setTitle(Title)
                .setMessage(Message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static String Dateformate(String str) throws ParseException {
        DateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        String inputDateStr=str;
        Date date = inputFormat.parse(inputDateStr);
        String outputDateStr = outputFormat.format(date);
        Log.d("Date",outputDateStr);
        return outputDateStr;
    }
}
