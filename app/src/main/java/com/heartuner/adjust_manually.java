package com.heartuner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class adjust_manually extends AppCompatActivity {
    //Declare variables to be used for manual profile setting
    ArrayList<TextView> centerFrequencyList;
    ArrayList<TextView> minRangeList;
    ArrayList<TextView> maxRangeList;
    ArrayList<SeekBar> adjusterBarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_manually);

        //Initialize variables to be used for manual profile setting
        centerFrequencyList = new ArrayList<TextView>();
        minRangeList = new ArrayList<TextView>();
        maxRangeList = new ArrayList<TextView>();
        adjusterBarList = new ArrayList<SeekBar>();

        //Add all TextViews associated with the center frequency titles to its respective list
        centerFrequencyList.add((TextView)findViewById(R.id.band0_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band1_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band2_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band3_center_frequency));
        centerFrequencyList.add((TextView)findViewById(R.id.band4_center_frequency));

        //Add all TextViews associated with the minimum range label to its respective list
        minRangeList.add((TextView)findViewById(R.id.band0_min_range));
        minRangeList.add((TextView)findViewById(R.id.band1_min_range));
        minRangeList.add((TextView)findViewById(R.id.band2_min_range));
        minRangeList.add((TextView)findViewById(R.id.band3_min_range));
        minRangeList.add((TextView)findViewById(R.id.band4_min_range));

        //Add all TextViews associated with the maximum range label to its respective list
        maxRangeList.add((TextView)findViewById(R.id.band0_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band1_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band2_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band3_max_range));
        maxRangeList.add((TextView)findViewById(R.id.band4_max_range));

        //Add all SeekBars associated with the center frequency magnitude to its respective list
        adjusterBarList.add((SeekBar)findViewById(R.id.fBandAdjuster0));
        adjusterBarList.add((SeekBar)findViewById(R.id.fBandAdjuster1));
        adjusterBarList.add((SeekBar)findViewById(R.id.fBandAdjuster2));
        adjusterBarList.add((SeekBar)findViewById(R.id.fBandAdjuster3));
        adjusterBarList.add((SeekBar)findViewById(R.id.fBandAdjuster4));

        //Add some temporary variables to use for setting the text within the manual profile setting
        short [] ranges = AppConfiguration.mEqualizer.getBandLevelRange();
        String temp;

        //Iterate through all ranges within the equalizer and set them accordingly to the values
        //set by the Equalizer
        for(short i=0;i<AppConfiguration.mEqualizer.getNumberOfBands();i++){
            //Set the text of the center frequency titles
            temp = "" + AppConfiguration.mEqualizer.getCenterFreq(i)/1000 + "Hz";
            centerFrequencyList.get(i).setText(temp);

            //Set the text of the minimum ranges of SeekBars
            temp = "" + ranges[0] + "mB";
            minRangeList.get(i).setText(temp);

            //Set the text of the maximum ranges of the SeekBars
            temp = "" + ranges[1] + "mB";
            maxRangeList.get(i).setText(temp);

            //Set the length and progress of the SeekBar according to the settings of the manual profile
            adjusterBarList.get(i).setMax(Math.abs(ranges[0])+Math.abs(ranges[1]));
            adjusterBarList.get(i).setProgress(AppConfiguration.mEqualizer.getBandLevel(i) + ranges[1]);
            adjusterBarList.get(i).setTag(i);
            adjusterBarList.get(i).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Short x = (Short) seekBar.getTag();
                    int maxRange = AppConfiguration.mEqualizer.getBandLevelRange()[1];

                    Log.d("HearTuner.debug", "Readjusting Seekbar value to equalizer level...");
                    short amplification = (short)(progress - maxRange);//((short) progress) - ((short) maxRange);

                    Log.d("HearTuner.debug", "Setting equalizer value based on bar:" + x);
                    Log.d("HearTuner.debug", "Value being set is: " + amplification);

                    AppConfiguration.mEqualizer.setBandLevel((short)(int) x, (short) amplification);

                    Log.d("HearTuner.debug", "Bar " + x + "is now " + AppConfiguration.mEqualizer.getBandLevel((short) (int) x));



                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }


    }
}
