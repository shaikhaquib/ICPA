package com.s.icpa.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.s.icpa.Admin.ChangeRegion_List;
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
    RecyclerView rvDocument;

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
        rvDocument = findViewById(R.id.rvDocument);
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



        rvDocument.setHasFixedSize(true);
        rvDocument.setLayoutManager(new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false));

        // Date Picker


        rvDocument.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(ClaimFormPMU.this).inflate(R.layout.doucument_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Holder holder = (Holder)viewHolder;

                File file = FileUtils.getFile(getApplicationContext(),arrayList.get(i));
                holder.filename.setText(file.getName());

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


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
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

            String charset = "UTF-8";
            String requestURL = APIs.PMU;


            try {
                //Log.d("file path", filePath);





                AndroidMultiPartEntity multipart = new AndroidMultiPartEntity(requestURL, charset);
                multipart.addFormField("customer_id", Global.customer_id);
                multipart.addFormField("designation", designation.getText().toString());
                multipart.addFormField("name"       , name.getText().toString());
                multipart.addFormField("region"     , strregion);
                multipart.addFormField("sap_no"     , sapno.getText().toString());


                for (int i = 0; i < arrayList.size(); i++) {

                    try {
                        String path = FileUtils.getPath(ClaimFormPMU.this, arrayList.get(i));
                        File sourceFile = new File(path);
                        multipart.addFilePart("upload[" + i + "]", FileUtils.getFile(ClaimFormPMU.this, arrayList.get(i)));
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
                    Global.diloge(ClaimFormPMU.this,"Success","We have recorded your request");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
