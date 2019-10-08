package com.s.icpa.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.s.icpa.Activity.APIs;
import com.s.icpa.AndroidMultiPartEntity;
import com.s.icpa.FileUtils;
import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UploadDirecter extends AppCompatActivity {

    ViewDialog progressDialoge ;
    private ArrayList<Uri> arrayList = new ArrayList<>();
    private static final String TAG = "UploadDirecter";
    Button submit,attachfile;
    RecyclerView rvDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_directer);
        attachfile = findViewById(R.id.Attahfile);
        submit = findViewById(R.id.upload);
        progressDialoge = new ViewDialog(UploadDirecter.this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size()== 0){
                    Toast.makeText(UploadDirecter.this, "Please add document ", Toast.LENGTH_SHORT).show();
                }else {
                    new UploadFileToServer().execute();
                }

            }
        });


        attachfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });

        rvDocument = findViewById(R.id.rvDocument);
        rvDocument.setHasFixedSize(true);
        rvDocument.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        rvDocument.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(UploadDirecter.this).inflate(R.layout.doucument_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder = (Holder)viewHolder;

                File file = FileUtils.getFile(getApplicationContext(),arrayList.get(i));
                Log.d(TAG, "onBindViewHolder: "+String.valueOf(arrayList.get(i)));
                holder.filename.setText(file.getName().replaceAll("[^a-zA-Z0-9\\.\\-]", "_"));

            }

            @Override
            public int getItemCount() {
                return arrayList.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView filename ;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    filename =  itemView.findViewById(R.id.filename);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // TODO Auto-generated method stub

        if(requestCode == 7) {
            if(null != data) { // checking empty selection
                if(null != data.getClipData()) { // checking multiple selection or not
                    for(int i = 0; i < data.getClipData().getItemCount(); i++) {
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        arrayList.add(uri);
                        rvDocument.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    Uri uri = data.getData();
                    arrayList.add(uri);
                    rvDocument.getAdapter().notifyDataSetChanged();
                }
            }
        }    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            super.onPreExecute();

            progressDialoge.show();

        }


        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;




            try {
                //Log.d("file path", filePath);

                String charset = "UTF-8";
                String requestURL = APIs.documentupload;

                AndroidMultiPartEntity multipart = new AndroidMultiPartEntity(requestURL, charset);
                multipart.addFormField("type ", getIntent().getStringExtra("type"));


                for (int i = 0; i < arrayList.size(); i++) {

                    try {
                        //  String path = FileUtils.getPath(UploadDirecter.this, arrayList.get(i));
                        //  if (path!=null) {
                        //     File sourceFile = new File(path);

                        Log.d(TAG, "uploadFile: "+String.valueOf(arrayList.get(i)));

                        multipart.addFilePart("upload[]", FileUtils.getFile(UploadDirecter.this, arrayList.get(i)));
                        //  }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                responseString = multipart.finish(); // response from server.


            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e( "Response from server: " , result);
            progressDialoge.dismiss();
            //super.onPostExecute(result);

            try {
                JSONObject jsonObject =  new JSONObject(result);

                if (jsonObject.getBoolean("status")){
                    Global.diloge(UploadDirecter.this,"Success","We have recorded your request");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

}
