package com.s.icpa.Admin;

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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.s.icpa.Activity.APIs;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.Model.EmailRequest;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeRegion_List extends AppCompatActivity {
    ViewDialog progressDialog = new ViewDialog(ChangeRegion_List.this);
    List<EmailRequest> list = new ArrayList<>();

    RecyclerView rvregionrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_region_list);

        setTitle("Region change request");

        rvregionrequest = findViewById(R.id.rvregionrequest);
        rvregionrequest.setLayoutManager(new LinearLayoutManager(this));
        rvregionrequest.setHasFixedSize(true);
        rvregionrequest.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(ChangeRegion_List.this).inflate(R.layout.sample_list, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Holder holder = (Holder) viewHolder;
                final  EmailRequest request = list.get(i);
                holder.name.setText(request.getName());
                holder.id.setText(request.getNewRegion());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Update_Region.class);
                        intent.putExtra("data", request);
                        startActivity(intent);

// To retrieve object in second Activity
                    }
                });


            }

            @Override
            public int getItemCount() {
                return list.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView name,id;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.name);
                    id = itemView.findViewById(R.id.email);
                }
            }
        });
        getData();
    }

    private void getData() {

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.Region_Request, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Responce",response);

                try {

                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        EmailRequest data = new Gson().fromJson(array.getString(i), EmailRequest.class);
                        list.add(data);
                    }
                    rvregionrequest.getAdapter().notifyDataSetChanged();

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
                param.put("region", Global.Region);
                return param;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }


}
