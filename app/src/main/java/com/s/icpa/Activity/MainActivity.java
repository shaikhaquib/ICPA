package com.s.icpa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;
import com.s.icpa.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HashMap<String, String> user;
    SQLiteHandler db = new SQLiteHandler(this);
    SessionManager sessionManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edt_Profile) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),Profile.class));
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
            startActivity(new Intent(getApplicationContext(),FlightRequestClander.class));
        }

    }
}
