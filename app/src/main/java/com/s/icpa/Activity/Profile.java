package com.s.icpa.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.s.icpa.R;
import com.s.icpa.SQLiteHandler;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    TextView name ,email,work_email,mobile,region,current_fleet,sap_no,address,member,designation,marital_status,Batch_no,dob;
    HashMap<String, String> user;
    SQLiteHandler db = new SQLiteHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name          = findViewById(R.id.name);
        email         = findViewById(R.id.email);
        work_email    = findViewById(R.id.work_email);
        mobile        = findViewById(R.id.mobile);
        region        = findViewById(R.id.region);
        current_fleet = findViewById(R.id.current_fleet);
        sap_no        = findViewById(R.id.sap_no);
        address       = findViewById(R.id.address);
        member        = findViewById(R.id.member);
        designation   = findViewById(R.id.designation);
        marital_status = findViewById(R.id.marital_status);
        Batch_no = findViewById(R.id.batch_no);
        dob = findViewById(R.id.dob);

        getData();
    }

    private void getData() {
        this.user = this.db.getUserDetails();

        name          .setText("Name :"+user.get("name"));
        Batch_no      .setText("Batch_no :"+user.get("batch_no"));
        dob           .setText("DOB :"+user.get("dob   "));
        email         .setText("Email :"+user.get("email"));
        work_email    .setText("Work Email :"+user.get("work_email"));
        mobile        .setText("Mobile :"+user.get("mobile"));
        region        .setText("Region :"+user.get("region"));
        current_fleet .setText("Current Fleet :"+user.get("current_fleet"));
        sap_no        .setText("Sap no :"+user.get("sap_no"));
        address       .setText("Address :"+user.get("address"));
        designation   .setText("Designation :"+user.get("Designation"));

        if (user.get("marital_status").equals("1")){
            marital_status.setText("Merital Status : Married");
        }else {
            marital_status.setText("Merital Status : Single");
        }


        String strMember = user.get("member");

        if (strMember.equals("1")){
            member.setText("Member Status : ICPA");
        }else {
            member.setText("Member Status : NonICPA");
        }
    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(),Edit_Profile.class));
    }
}
