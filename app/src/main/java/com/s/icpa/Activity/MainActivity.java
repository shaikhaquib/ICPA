package com.s.icpa.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.s.icpa.Admin.AdminBlogs;
import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;
import com.s.icpa.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_REQUEST_CODE = 3993;
    HashMap<String, String> user;
    SQLiteHandler db = new SQLiteHandler(this);
    SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navUserName);
        TextView NavEmail = headerView.findViewById(R.id.navEmail);
        NavEmail.setText(Global.Email);
        navUsername.setText(Global.name);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edt_Profile) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }else if (id == R.id.nav_notification) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),Notification.class));
        } else if (id == R.id.nav_update_email) {
            startActivity(new Intent(getApplicationContext(),Change_email.class));
        } else if (id == R.id.nav_change_pass) {
            startActivity(new Intent(getApplicationContext(),Change_Password.class));
        } else if (id == R.id.nav_become_member) {
            startActivity(new Intent(getApplicationContext(),ICPAMemeberApplication.class));
        } else if (id == R.id.nav_update_region) {
           startActivity(new Intent(getApplicationContext(), Change_Region.class));

        } else if (id == R.id.nav_conatact) {
            startActivity(new Intent(getApplicationContext(), Contact.class));

        } else if (id == R.id.logout) {
            db.deleteUsers();
            sessionManager.setLogin(false);
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void main(View view) {
        int id = view.getId();


        if (id == R.id.fatiguereport) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),FatigueReportForm.class));
        }else if (id == R.id.ptf) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),TravelForm.class));
        }else  if (id == R.id.claimformpmu) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),ClaimFormPMU.class));
        }else  if (id == R.id.claimformws) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),ClaimfromRWC.class));
        }else  if (id == R.id.complaint) {
            // Handle the camera action
           startActivity(new Intent(getApplicationContext(),Complaint_form.class));
        }else  if (id == R.id.userAccount) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),Profile.class));
        }else  if (id == R.id.flightcalendar) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),CalenderActivty.class));
        }else  if (id == R.id.blogs) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),Blogs.class));
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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
