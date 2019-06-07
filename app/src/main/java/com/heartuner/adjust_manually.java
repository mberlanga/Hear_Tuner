package com.heartuner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class adjust_manually extends AppCompatActivity {
    ArrayList<TextView> centerFrequencyList;
    ArrayList<TextView> minRangeList;
    ArrayList<TextView> maxRangeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_manually);

        ArrayList<TextView> centerFrequencyList = new ArrayList<TextView>();
        ArrayList<TextView> minRangeList = new ArrayList<TextView>();
        ArrayList<TextView> maxRangeList = new ArrayList<TextView>();


        centerFrequencyList.add((TextView)findViewById(R.id.band0_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band1_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band2_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band3_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band4_center_frequency));

        minRangeList.add((TextView)findViewById(R.id.band0_min_range));
        minRangeList.add((TextView)findViewById(R.id.band1_min_range));
        minRangeList.add((TextView)findViewById(R.id.band2_min_range));
        minRangeList.add((TextView)findViewById(R.id.band3_min_range));
        minRangeList.add((TextView)findViewById(R.id.band4_min_range));

        maxRangeList.add((TextView)findViewById(R.id.band0_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band1_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band2_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band3_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band4_max_range));

        short [] ranges = AppConfiguration.mEqualizer.getBandLevelRange();
        String temp;
        for(short i=0;i<AppConfiguration.mEqualizer.getNumberOfBands();i++){
            temp = "" + AppConfiguration.mEqualizer.getCenterFreq(i)/1000 + "Hz";
            centerFrequencyList.get(i).setText(temp);

            temp = "" + ranges[0] + "mB";
            minRangeList.get(i).setText(temp);

            temp = "" + ranges[1] + "mB";
            maxRangeList.get(i).setText(temp);
        }
    }
}
