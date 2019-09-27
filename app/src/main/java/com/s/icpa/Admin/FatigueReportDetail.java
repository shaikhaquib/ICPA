package com.s.icpa.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.s.icpa.Model.ACM_Model;
import com.s.icpa.Model.FrfModel;
import com.s.icpa.R;
import com.s.icpa.ViewDialog;

public class FatigueReportDetail extends AppCompatActivity {

    ViewDialog progressDialog = new ViewDialog(this);

    EditText name, empno, local_report_date, local_report_time, duty_desc, edtnap,
            fof, fot, hrt, aircraft_type, aircraft_no, location, description, awake, sleep, other_comment, what_could_be_done, what_did_u_do;
    EditText fpd, hotel, home, disrupt, personal;
    SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatigue_report_detail);


        name = findViewById(R.id.name);
        empno = findViewById(R.id.empno);
        edtnap = findViewById(R.id.edtnap);
        seekbar = findViewById(R.id.seekbar);

        local_report_date = findViewById(R.id.report_date);
        local_report_time = findViewById(R.id.report_time);
        duty_desc = findViewById(R.id.duty_desc);
        fof = findViewById(R.id.fof);
        fot = findViewById(R.id.fot);
        hrt = findViewById(R.id.hrt);
        aircraft_type = findViewById(R.id.aircraft_type);
        aircraft_no = findViewById(R.id.aircraft_no);
        location = findViewById(R.id.location);

        //fr3

        description = findViewById(R.id.description);

        //fr4


        awake = findViewById(R.id.awake);
        sleep = findViewById(R.id.sleep);
        what_did_u_do = findViewById(R.id.what_did_u_do);
        what_could_be_done = findViewById(R.id.what_could_be_done);
        other_comment = findViewById(R.id.other_comment);
        fpd = findViewById(R.id.fpd);
        home = findViewById(R.id.home);
        hotel = findViewById(R.id.hotel);
        disrupt = findViewById(R.id.disrupt);
        personal = findViewById(R.id.personal);

        getData();

    }

    private void getData() {
        final FrfModel data = (FrfModel) getIntent().getSerializableExtra("data");
        setTitle(data.getName());
        setData(data);
    }

    private void setData(FrfModel data) {

        name.setText(data.getName());
        empno.setText(data.getEmployeeNo());
        local_report_date.setText(data.getLocalReportDate());
        local_report_time.setText(data.getLocalReportTime());
        duty_desc.setText(data.getDutyDesc());
        edtnap.setText(data.getFlightdecknap());
        fof.setText(data.getFof());
        fot.setText(data.getFot());
        hrt.setText(data.getHrt());
        aircraft_type.setText(data.getAircraftType());
        aircraft_no.setText(data.getAircraftNo());
        location.setText(data.getLocation());
        description.setText(data.getDescription());
        awake.setText(data.getAwake());
        sleep.setText(data.getSleep());
        other_comment.setText(data.getOtherComment());
        what_could_be_done.setText(data.getWhatCouldBeDone());
        what_did_u_do.setText(data.getWhatDidYouDo());
        fpd.setText(data.getFpd());
        hotel.setText(data.getHotel());
        home.setText(data.getHome());
        disrupt.setText(data.getDisrupt());
        personal.setText(data.getPersonal());

        if (data.getPersonal()!=null)
        seekbar.setProgress(Integer.parseInt(data.getHowYouFelt()));

    }

}
