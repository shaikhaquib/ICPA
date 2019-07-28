package com.s.icpa.Admin;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.s.icpa.Activity.Change_email;
import com.s.icpa.Global;
import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;
import com.s.icpa.SessionManager;

import java.util.HashMap;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {


    HashMap<String, String> user;
    SQLiteHandler db = new SQLiteHandler(this);
    SessionManager sessionManager ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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
            }



    }
}
