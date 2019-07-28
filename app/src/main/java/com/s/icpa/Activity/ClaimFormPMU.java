package com.s.icpa.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.s.icpa.AndroidMultiPartEntity;
import com.s.icpa.FileUtils;
import com.s.icpa.Global;
import com.s.icpa.HelperUtils.HelperUtilities;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ClaimFormPMU extends AppCompatActivity {

    ViewDialog progressDialoge = new ViewDialog(this);
    ImageButton button ;
    private String filePath;
    private static final String TAG = "ClaimFormPMU";

    EditText name,designation,sapno,dopmu;
    Button submitpmu;
    RadioGroup   Rgregion;
    String strregion = ",";

    ImageButton date_pmu;
    Calendar myCalendar = Calendar.getInstance();
    private ArrayList<Uri> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_form_pmu);
        arrayList = new ArrayList<>();

         button = findViewById(R.id.btn_picker);
         designation = findViewById(R.id.Designation);
         name = findViewById(R.id.name);
         sapno = findViewById(R.id.sapno);
         dopmu = findViewById(R.id.dopmu);
         submitpmu = findViewById(R.id.submitpmu);
         date_pmu = findViewById(R.id.btn_date_pmu);
        Rgregion = findViewById(R.id.Radio_region);

        Rgregion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                strregion = radioButton.getText().toString();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });



        // Date Picker


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dopmu.setText(sdf.format(myCalendar.getTime()));
            }

        };
        date_pmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ClaimFormPMU.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        submitpmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HelperUtilities.isEmptyOrNull(name.getText().toString())) {
                    name.setError("Please enter your name");
                }
                else
                if (HelperUtilities.isEmptyOrNull(designation.getText().toString())) {
                    name.setError("Please enter your designation");
                }
                else if (HelperUtilities.isEmptyOrNull(dopmu.getText().toString())) {
                    name.setError("Please enter your Date of PMU");
                }
                else if (HelperUtilities.isEmptyOrNull(sapno.getText().toString())) {
                    name.setError("Please enter your Sap no");
                }
                else if (arrayList.size()==0) {
                   Global.diloge(ClaimFormPMU.this,"Error","Please select valid document");
                }
                else{
                    new UploadFileToServer().execute();

                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            //    Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);

                                Log.d(TAG, "onActivityResult: Multiple File Selected"+ path);
                                arrayList.add(imageUri);


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
            progressDialoge.show();
           super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(APIs.PMU);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                }
                        });
                //Log.d("file path", filePath);

                for (int i = 0; i < arrayList.size(); i++) {

                    try {
                        String path = FileUtils.getPath(ClaimFormPMU.this, arrayList.get(i));
                        File sourceFile = new File(path);
                        entity.addPart("document[" + i + "]", new FileBody(sourceFile));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                entity.addPart("customer_id", new StringBody(Global.customer_id));
                entity.addPart("designation", new StringBody(designation.getText().toString()));
                entity.addPart("name", new StringBody(name.getText().toString()));
                entity.addPart("region", new StringBody(strregion));
                entity.addPart("sap_no", new StringBody(sapno.getText().toString()));
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

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
                    Global.diloge(ClaimFormPMU.this,"Success","We have recorded your request");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
