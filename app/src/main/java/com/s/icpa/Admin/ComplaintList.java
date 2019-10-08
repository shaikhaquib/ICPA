package com.s.icpa.Admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.s.icpa.Activity.APIs;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.Model.ComplaintModel;
import com.s.icpa.Model.EmailRequest;
import com.s.icpa.R;
import com.s.icpa.SendNotification;
import com.s.icpa.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintList extends AppCompatActivity {

    RecyclerView rvComplaint;
    List<ComplaintModel> list = new ArrayList<>();
    ViewDialog progressDialog = new ViewDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        rvComplaint = findViewById(R.id.rvComplaint);
        rvComplaint.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvComplaint.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(ComplaintList.this).inflate(R.layout.listitem_with_button, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Holder holder = (Holder) viewHolder;
                final ComplaintModel request = list.get(i);
                holder.name.setText(request.getName());
                holder.Designation.setText(request.getDesignation());
                holder.id.setText(request.getComplaint());

                holder.reply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        successDilogue(i);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView name,id,Designation,reply;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.name);
                    id = itemView.findViewById(R.id.email);
                    Designation = itemView.findViewById(R.id.designation);
                    reply = itemView.findViewById(R.id.reply);
                }
            }
        });

        getData();
    }

    private void getData() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.ComplaintList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Responce",response);

                try {

                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        ComplaintModel data = new Gson().fromJson(array.getString(i), ComplaintModel.class);
                        list.add(data);
                    }
                    rvComplaint.getAdapter().notifyDataSetChanged();

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
                param.put("region","SR");
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


    public void successDilogue(int i) {
        final Dialog dialog = new Dialog(ComplaintList.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.write_solution);




        Button submit = dialog.findViewById(R.id.submit);
        TextView name = dialog.findViewById(R.id.name);
        TextView complaint = dialog.findViewById(R.id.complaint);
        EditText solution = dialog.findViewById(R.id.message);


        name.setText(list.get(i).getName());
        complaint.setText("Complaint: "+list.get(i).getComplaint());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!solution.getText().toString().equals("")) {
                    new SendNotification(ComplaintList.this).execute(list.get(i).getCustomerId(), list.get(i).getName() + " Response on your complaint ", solution.getText().toString());
                    sendSolution();
                }
                else
                    solution.setError("Field required");
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void sendSolution() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.writeSolution, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Responce",response);
                try {
                    JSONObject jsonObject =  new JSONObject(response);

                    if (jsonObject.getBoolean("status")){
                        Global.diloge(ComplaintList.this,"Success","Solution send successfully");
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
                param.put("region","SR");
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}
