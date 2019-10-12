package com.s.icpa.Admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.s.icpa.Activity.Blogs;
import com.s.icpa.Activity.Change_email;
import com.s.icpa.Activity.LoginActivity;
import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;
import com.s.icpa.SessionManager;

import java.util.HashMap;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 3993;
    HashMap<String, String> user;
    SQLiteHandler db = new SQLiteHandler(this);
    SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }
        }


        sessionManager = new SessionManager(this);
        getUserDATA();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Admin panel");
        getSupportActionBar().setSubtitle("ADMIN "+Global.name);
    }
    private void getUserDATA() {

        this.user = this.db.getUserDetails();
        String str = (String) this.user.get("name");
        Global.Email = (String) this.user.get(NotificationCompat.CATEGORY_EMAIL);
        Global.mobile = (String) this.user.get("mobile");
        Global.customer_id = (String) this.user.get("customer_id");
        Global.Region = (String) this.user.get("region");
        Global.name = str;
        setTitle(str);

    }

    public void onclickadmin(View view) {

        int id = view.getId();


            if(id==R.id.new_login_request) {
                startActivity(new Intent(getApplicationContext(),NewLoginRequest.class));
            } else if(id==R.id.ptf) {
                startActivity(new Intent(getApplicationContext(),ACM_List.class));
            } else if(id==R.id.claimformpmu) {
                startActivity(new Intent(getApplicationContext(),PMU.class));
            } else if(id==R.id.claimformws) {
                startActivity(new Intent(getApplicationContext(),RWC.class));
            } else if(id==R.id.change_email) {
                startActivity(new Intent(getApplicationContext(), EmailRequestList.class));
            } else if(id==R.id.change_region) {
                startActivity(new Intent(getApplicationContext(),ChangeRegion_List.class));
            }else if(id==R.id.application_form) {
                startActivity(new Intent(getApplicationContext(),ApplicationsForm.class));
            } else if(id==R.id.fatiguereport) {
                startActivity(new Intent(getApplicationContext(),FatigueReportList.class));
            } else if(id==R.id.blogs) {
                startActivity(new Intent(getApplicationContext(), AdminBlogs.class));
            }else if(id==R.id.showDirector) {
                startActivity(new Intent(getApplicationContext(), DirectorAdminlist.class).putExtra("type","1"));
            }else if (id==R.id.showcircular){
                startActivity(new Intent(getApplicationContext(), DirectorAdminlist.class).putExtra("type","2"));
            }else if (id==R.id.complaint){
                startActivity(new Intent(getApplicationContext(), ComplaintList.class));
            }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            sessionManager.setLogin(false);
            sessionManager.setAdmin(false);
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AdminActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(AdminActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(AdminActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            Toast.makeText(AdminActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AdminActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}
