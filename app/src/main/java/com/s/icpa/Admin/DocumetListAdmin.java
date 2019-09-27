package com.s.icpa.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.s.icpa.Activity.APIs;
import com.s.icpa.Activity.ClaimfromRWC;
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
import java.net.URI;
import java.util.ArrayList;

public class DocumetListAdmin extends AppCompatActivity {

    TextView uploadDocument ;
    ViewDialog progressDialoge ;
    private ArrayList<Uri> arrayList = new ArrayList<>();
    private static final String TAG = "DocumetListAdmin";
    Button submit;
    RecyclerView rvDocument;

    EditText title,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documet_list_admin);
        uploadDocument = findViewById(R.id.uploadDocument);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        submit = findViewById(R.id.submit);
        progressDialoge = new ViewDialog(DocumetListAdmin.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().isEmpty()){
                    title.setError("this field is required");
                }else if  (message.getText().toString().isEmpty()){
                    message.setError("this field is required");
                }else if (arrayList.size()== 0){
                    Toast.makeText(DocumetListAdmin.this, "Please add document ", Toast.LENGTH_SHORT).show();
                }else {
                    new UploadFileToServer().execute();
                }
            }
        });

        uploadDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
                return new Holder(LayoutInflater.from(DocumetListAdmin.this).inflate(R.layout.doucument_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder = (Holder)viewHolder;

                File file = new File(String.valueOf(arrayList.get(i)));
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

        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getData();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            //    Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);

                                Log.d(TAG, "onActivityResult: Multiple File Selected"+ path);
                                arrayList.add(imageUri);
                                rvDocument.getAdapter().notifyDataSetChanged();


                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);

                            arrayList.add(uri);
                            rvDocument.getAdapter().notifyDataSetChanged();

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
        }
    }

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
                multipart.addFormField("title", title.getText().toString());
                multipart.addFormField("message", message.getText().toString());


                for (int i = 0; i < arrayList.size(); i++) {

                    try {
                      //  String path = FileUtils.getPath(DocumetListAdmin.this, arrayList.get(i));
                      //  if (path!=null) {
                       //     File sourceFile = new File(path);

                        Log.d(TAG, "uploadFile: "+String.valueOf(arrayList.get(i)));

                            multipart.addFilePart("upload[" + i + "]", FileUtils.getFile(DocumetListAdmin.this, arrayList.get(i)));
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
                    Global.diloge(DocumetListAdmin.this,"Success","We have recorded your request");
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
