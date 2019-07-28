package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.HelperUtils.HelperUtilities;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Change_Password extends AppCompatActivity {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private boolean isValid;
    private boolean isValidUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        oldPassword = (EditText) findViewById(R.id.txtOldPassword);
        newPassword = (EditText) findViewById(R.id.txtNewPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        Button btnChangePassword = (Button) findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidInput())
                changePassword();
            }
        });

    }

    private void changePassword() {

        final ViewDialog progressDialog  = new ViewDialog(Change_Password.this);

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.change_password, new Response.Listener<String>() {
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
                            builder = new AlertDialog.Builder(Change_Password.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Change_Password.this);
                        }
                        builder.setCancelable(false);
                        builder.setTitle("Success")
                                .setMessage("You have successfully changed your password")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startActivity(new Intent(Change_Password.this,MainActivity.class));
                                        finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }
                    else
                    {
                        Global.diloge(Change_Password.this,"Error" , jsonObject.getString("error"));
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
                param.put("customer_id",Global.customer_id);
                param.put("new_password",newPassword.getText().toString());
                param.put("old_password",oldPassword.getText().toString());


                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    public boolean isValidInput() {
        if (HelperUtilities.isEmptyOrNull(oldPassword.getText().toString())) {
            oldPassword.setError("Please enter your password");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(newPassword.getText().toString())) {
            newPassword.setError("Please enter your new password");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(confirmPassword.getText().toString())) {
            confirmPassword.setError("Please confirm your new password");
            return false;
        } else if (HelperUtilities.isShortPassword(newPassword.getText().toString())) {
            newPassword.setError("Your password must have at least 6 characters.");
            return false;
        } else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            newPassword.setError("The password doesn't match");
            return false;
        }
        return true;
    }

}
