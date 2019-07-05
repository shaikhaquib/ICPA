package com.s.icpa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class FatigueReportForm extends AppCompatActivity {

    LinearLayout Fr1,Fr2,Fr3,Fr4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fatigue_report_form);

        Fr1= findViewById(R.id.fr1);
        Fr2= findViewById(R.id.fr2);
        Fr3= findViewById(R.id.fr3);
        Fr4= findViewById(R.id.fr4);
    }

    public void backbutton(View view) {
        int id = view.getId();

        if (id ==  R.id.fr2Back){
            Fr1.setVisibility(View.VISIBLE);
            Fr2.setVisibility(View.GONE);
        }else if (id ==  R.id.fr3Back){
            Fr2.setVisibility(View.VISIBLE);
            Fr3.setVisibility(View.GONE);
        }else if (id ==  R.id.fr4back){
            Fr3.setVisibility(View.VISIBLE);
            Fr4.setVisibility(View.GONE);
        }
    }

    public void nextbutton(View view) {

        int id = view.getId();

        if (id ==  R.id.fr1btnNext){
            Fr2.setVisibility(View.VISIBLE);
            Fr1.setVisibility(View.GONE);
        }else if (id ==  R.id.fr2Next){
            Fr3.setVisibility(View.VISIBLE);
            Fr2.setVisibility(View.GONE);
        }else if (id ==  R.id.fr3Next){
            Fr4.setVisibility(View.VISIBLE);
            Fr3.setVisibility(View.GONE);
        }

    }
}
