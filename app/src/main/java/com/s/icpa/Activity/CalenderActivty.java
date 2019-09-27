package com.s.icpa.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalenderActivty extends AppCompatActivity {

    long startDate;
    CalenderEvent calenderEvent;
    private static final String TAG = "CalenderActivty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_activty);




        calenderEvent = findViewById(R.id.calender_event);
// to set desire day time milli second in first parameter
//or you set color for each event

/*
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getApplicationContext(), CalenderActivty.class);
                // Save the selected date in for using in the new activity
                intent.putExtra("year", year);
                intent.putExtra("month", month+1);
                intent.putExtra("dayOfMonth", dayOfMonth);

                startActivity(intent);
            }
        });
*/

        updateData();
        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Intent intent = new Intent(getApplicationContext(), FlightRequestClander.class);
                // Save the selected date in for using in the new activity
                intent.putExtra("year", dayContainerModel.getYear());
                intent.putExtra("month",dayContainerModel.getMonthNumber()+1);
                intent.putExtra("dayOfMonth", dayContainerModel.getDay());

                startActivity(intent);            }
        });    }



    private void updateData() {


        final ViewDialog progressDialog = new ViewDialog(CalenderActivty.this);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.All_flight_request_details, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    Object json = new JSONTokener(response).nextValue();
                    if (json instanceof JSONObject) {
                        //you have an object
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("status"));
                        {
                            Log.d(TAG, "onResponse: No DATA");
                        }
                    } else if (json instanceof JSONArray)
                    {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0 ; i < array.length() ; i++){

                            JSONObject object=array.getJSONObject(i);

                            try {
                                String dateString = object.getString("travel_date");
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = sdf.parse(dateString);

                                long time = date.getTime();

                                Event event = new Event(time, "o", Color.RED);
                                calenderEvent.addEvent(event);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
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
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("customer_id", Global.customer_id);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
