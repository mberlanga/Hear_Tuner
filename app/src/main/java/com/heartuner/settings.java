package com.heartuner;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class settings extends AppCompatActivity {
    //0 = non-selected
    //1 = manual-profile
    //2 = test_results-profile
    int whichProfile = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button configureManualEntryButton = (Button)findViewById(R.id.configure_manual_entry_button); //Initialize button for entering manual profile
        Button configureTestResultsButton = (Button)findViewById(R.id.configure_test_results_button); //Initialize button for entering test profile
        final ToggleButton chooseManualButton = (ToggleButton)findViewById(R.id.manual_choice_button); //Initialize button for choosing manual entry
        final ToggleButton chooseTestResultsButton = (ToggleButton)findViewById(R.id.test_results_choice_button); //Initialize button for choosing test results entry



        configureManualEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast enableToast = Toast.makeText(getApplicationContext(),
                        "Entering Manual Entry Setup...In Development",
                        Toast.LENGTH_SHORT
                );
                enableToast.show();

            }
        });

        configureTestResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast enableToast = Toast.makeText(getApplicationContext(),
                        "Currently in Development",
                        Toast.LENGTH_SHORT
                );
                enableToast.show();

            }
        });

        chooseManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichProfile = 1;
                Toast enableToast = Toast.makeText(getApplicationContext(),
                        "Manual Profile Chosen",
                        Toast.LENGTH_SHORT
                );
                chooseManualButton.setChecked(true);
                chooseManualButton.setBackgroundColor(
            ContextCompat.getColor(getApplicationContext(),
                    R.color.green_enable));
                chooseTestResultsButton.setChecked(false);
                chooseTestResultsButton.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(),
                                R.color.heartuner_Blue));
                enableToast.show();

            }
        });

        chooseTestResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whichProfile = 2;
                Toast enableToast = Toast.makeText(getApplicationContext(),
                        "Test Profile Chosen",
                        Toast.LENGTH_SHORT
                );
                chooseManualButton.setChecked(false);
                chooseManualButton.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(),
                                R.color.heartuner_Blue));
                chooseTestResultsButton.setChecked(true);
                chooseTestResultsButton.setBackgroundColor(
                        ContextCompat.getColor(getApplicationContext(),
                                R.color.green_enable));
                enableToast.show();

            }
        });
    }
}
