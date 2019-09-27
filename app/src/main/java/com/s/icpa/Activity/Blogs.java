package com.s.icpa.Activity;

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
import com.s.icpa.Admin.DocumetListAdmin;
import com.s.icpa.AppController;
import com.s.icpa.Global;
import com.s.icpa.Model.ACM_Model;
import com.s.icpa.Model.Documentlist;
import com.s.icpa.Model.ModelApplicationform;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Blogs extends AppCompatActivity {

    RecyclerView rvDocument;
    List<Documentlist> documentlists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        rvDocument=findViewById(R.id.rvDocument);
        rvDocument.setHasFixedSize(true);
        rvDocument.setLayoutManager(new LinearLayoutManager(this));


        rvDocument.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(Blogs.this).inflate(R.layout.blog_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder = (Holder)viewHolder;
                Documentlist documentlist = documentlists.get(i);

                holder.filename.setText(documentlist.getTitle());
                holder.description.setText(documentlist.getMessage());
                holder.links.setText(documentlist.getDocumentUrl().replace(",","\n"));

            }

            @Override
            public int getItemCount() {
                return documentlists.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView filename ,links,description;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    filename =  itemView.findViewById(R.id.filename);
                    links =  itemView.findViewById(R.id.links);
                    description =  itemView.findViewById(R.id.description);
                }
            }
        });

        getData();

    }

    private void getData() {

        final ViewDialog progressDialog = new ViewDialog(Blogs.this);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, APIs.show_admin_notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                Log.d("Responce",response);

                try {

                    if (response != null) {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            Documentlist data = new Gson().fromJson(array.getString(i), Documentlist.class);
                            documentlists.add(data);
                            rvDocument.getAdapter().notifyDataSetChanged();

                        }
                    }
                } catch(JSONException e){
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
