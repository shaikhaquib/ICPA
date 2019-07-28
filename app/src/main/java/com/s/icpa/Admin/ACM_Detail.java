package com.s.icpa.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.s.icpa.Model.ACM_Model;
import com.s.icpa.Model.Login_Request;
import com.s.icpa.R;

public class ACM_Detail extends AppCompatActivity {

    TextView name, email,  mobile, currentfleet, staffno,flightno,flighttype,dateoftravel,leavesanctioned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acm__detail);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        currentfleet = findViewById(R.id.current_fleet);
        staffno = findViewById(R.id.staffno);
        flightno = findViewById(R.id.flightnumber);
        flighttype = findViewById(R.id.flighttype);
        dateoftravel = findViewById(R.id.dateoftravel);
        leavesanctioned = findViewById(R.id.leavesanctioned);

        getData();

    }

    private void getData() {

        final ACM_Model data = (ACM_Model) getIntent().getSerializableExtra("data");

        setTitle(data.getName());

        name.setText(data.getName());
        email.setText(data.getEmail());
        mobile.setText(data.getMobile());
        currentfleet.setText(data.getCurrentFleet());
        staffno.setText(data.getStaffNo());
        flightno.setText(data.getFlightNo());
        flighttype.setText(data.getFlightType());
        dateoftravel.setText(data.getTravelDate());
        leavesanctioned.setText(data.getLeaveFrom()+" to "+data.getLeaveTo());


    }

}
