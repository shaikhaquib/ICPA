package com.s.icpa.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.s.icpa.Model.PMU_Model;
import com.s.icpa.R;

public class RWC_Detail extends AppCompatActivity {

    TextView name, email,  mobile, designation, dateofpmu,sapno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rwc_detail);
        name = findViewById(R.id.name);
        designation = findViewById(R.id.designation);
        dateofpmu = findViewById(R.id.dateofpmu);
        sapno = findViewById(R.id.sapno);


        getData();

    }

    private void getData() {

        final PMU_Model data = (PMU_Model) getIntent().getSerializableExtra("data");

        setTitle(data.getName());

        name.setText(data.getName());

        sapno.setText(data.getSapNo());
        designation.setText(data.getDesignation());
        dateofpmu.setText(data.getDate());

    }
}
