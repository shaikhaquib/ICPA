package com.s.icpa.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.s.icpa.Global;
import com.s.icpa.Model.ACM_Model;
import com.s.icpa.Model.PMU_Model;
import com.s.icpa.R;
import com.s.icpa.SendNotification;

public class PMU_Detail extends AppCompatActivity {

    TextView name, email,  link, designation, dateofpmu,sapno;
    Button approve, disapprove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmu__detail);

        name = findViewById(R.id.name);
       designation = findViewById(R.id.designation);
        dateofpmu = findViewById(R.id.dateofpmu);
        sapno = findViewById(R.id.sapno);
        link = findViewById(R.id.link);

        approve = findViewById(R.id.approve);
        disapprove = findViewById(R.id.disapprove);


        disapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendNotification(PMU_Detail.this).execute(Global.customer_id,name.getText().toString()+"PMU CLAIM","your request for PMU CLAIM has been disapproved.For more information you can contact region admin" );
            }
        });

        getData();

    }

    private void getData() {

        final PMU_Model data = (PMU_Model) getIntent().getSerializableExtra("data");

        setTitle(data.getName());

        name.setText(data.getName());

        sapno.setText(data.getSapNo());
        designation.setText(data.getDesignation());
        dateofpmu.setText(data.getDate());
        if (data.getDocumentUrl()!=null)
        link.setText(data.getDocumentUrl().replace(",","\n"));

    }
}
