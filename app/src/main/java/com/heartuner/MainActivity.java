package com.heartuner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //public MediaPlayer mp;
    //private Equalizer mEqualizer;
    private BluetoothAdapter mBlueAdapter; // To be used to interact with device Bluetooth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppConfiguration.getInstance(this); // Initializes global settings object to be used between activities
        //TODO: Condense set up of bluetooth verification into another Bluetooth.java file
        //      so that one can just run a bluetooth_init() function
        //Set up Bluetooth Adapter
//        int intVal = 1;
//        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();  //Assigns default adapter
//        if (mBlueAdapter == null) { //Displays error message in logs if failure
//            Log.d("HearTuner.Errors", "Device will not support Bluetooth");
//
//        }
//        if (!mBlueAdapter.isEnabled()) { //Attempts to enable Bluetooth if it is not enabled
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  //Intent to try to enable bluetooth
//            startActivityForResult(enableIntent, intVal); //Will start bluetooth as a separate activity
//        }

//        //Make the device discoverable to other devices
//        Intent deviceIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE); //Create intent to request device discovery
//        deviceIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); //Add extra time to device discovery time
//        startActivity(deviceIntent); // start the activity to be discovered
//
//        //Get the paired devices
//        Set<BluetoothDevice> pairedDevicesList = mBlueAdapter.getBondedDevices(); //Gets bonded devices and adds them to devices list
//        if (pairedDevicesList.size() > 0) { //Attempts to list devices if the device list is greater than 0
//            for (BluetoothDevice device : pairedDevicesList) { //Iterate over all items in device list
//                String deviceName = device.getName(); //assigns String to device name
//                String deviceHardwareAddress = device.getAddress(); //assigns String to device address
//
//                Log.d("HearTuner.Bluetooth", "Device: " + deviceName + "\n");
//                Log.d("HearTuner.Bluetooth", "Address: " + deviceHardwareAddress + "\n");
//            }
//        }


        //Setup Buttons for Main Menu/Title Screen
        Button enableButton = (Button) findViewById(R.id.enable_button);   //Initialize enable button
        Button disableButton = (Button) findViewById(R.id.disable_button); //Initialize disable button
        Button settingsButton = (Button) findViewById(R.id.settings_button); //Initialize settings button


        //TODO: Set up application state object that will add behavior to enable and disable buttons

        //FloatingActionButton fab = findViewById(R.id.fab);
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast enableToast = Toast.makeText(getApplicationContext(),
                        "Enabled!",
                        Toast.LENGTH_SHORT
                );
                enableToast.show();
                try{
                AppConfiguration.mMediaPlayer.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Toast settingsToast = Toast.makeText(getApplicationContext(),
                        "Music Playing!",
                        Toast.LENGTH_LONG);
                settingsToast.show();
                AppConfiguration.getInstance(getApplicationContext()).setTunerEnabled(true);

            }
        });

        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast disableToast = Toast.makeText(getApplicationContext(),
                        "Disabled!",
                        Toast.LENGTH_SHORT
                );
                disableToast.show();
                try{
                    AppConfiguration.mMediaPlayer.pause();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Toast settingsToast = Toast.makeText(getApplicationContext(),
                        "Music Stopped!",
                        Toast.LENGTH_LONG);
                settingsToast.show();
                AppConfiguration.getInstance(getApplicationContext()).setTunerEnabled(false);


            }
        });

        //TODO: Add layout to transition to after settings button has been pressed
        //TODO: Create object to hold settings features to be configured

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast settingsToast = Toast.makeText(getApplicationContext(),
                        "Settings!",
                        Toast.LENGTH_SHORT);
                settingsToast.show();
                Intent settingsIntent = new Intent(getApplicationContext(), settings.class);
                startActivity(settingsIntent);


            }
        });

//        //Test Media Features of application through media playback and recording
//        try { //Setup static Media Player that plays song with dynamic range
//            mp = MediaPlayer.create(this, R.raw.test);
//        }
//        catch (Exception e){e.printStackTrace();}
//
//        //Setup Equalizer to be associated with MediaPlayer
//        mEqualizer = new Equalizer(0,mp.getAudioSessionId());
//        mEqualizer.setEnabled(true); //Enable Equalizer
//
//        short numFrequencyBands = mEqualizer.getNumberOfBands();
//        short[] bandLevelRange = mEqualizer.getBandLevelRange();
//        Log.d("HearTuner.debug", "Number of Frequency Bands: " + numFrequencyBands + "\n");
//        Log.d("HearTuner.debug", "Lowest Level: " + bandLevelRange[0] + "\n");
//        Log.d("HearTuner.debug", "Highest Level: " + bandLevelRange[1] + "\n");
//
//        for(short i=0;i<numFrequencyBands;i++){
//            int centerFreq = mEqualizer.getCenterFreq(i);
//            Log.d("HearTuner.debug", "Center Frequency for band [" + i + "] :"+ centerFreq/1000 + " Hz\n");
//            Log.d("HearTuner.debug", "Range for band [" + i + "] :"+ mEqualizer.getBandLevel(i) + " millibels\n\n");
//
//        }
//
//
//
//        Log.d("HearTuner.debug", "NOW SETTING EQUALIZER TO DIFFERENT VALUES: [500,500,-200,200,0] \n");
//        mEqualizer.setBandLevel((short)0, (short)1500);
//        mEqualizer.setBandLevel((short)1,(short)1500);
//        mEqualizer.setBandLevel((short)2,(short)-1000);
//        mEqualizer.setBandLevel((short)3,(short)1500);
//        mEqualizer.setBandLevel((short)4,(short)1500);
//
//        for(short i=0;i<numFrequencyBands;i++){
//
//            int centerFreq = mEqualizer.getCenterFreq(i);
//            Log.d("HearTuner.debug", "Center Frequency for band [" + i + "] :"+ centerFreq/1000 + " Hz\n");
//            Log.d("HearTuner.debug", "Range for band [" + i + "] :"+ mEqualizer.getBandLevel(i) + " millibels\n\n");
//
//        }
//
//        Log.d("HearTuner.debug", "Settings for Equalizer \n" + mEqualizer.getProperties());



    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d("HearTuner.debug", "Now in onPause");
        AppConfiguration.getInstance(getApplicationContext()).saveData();


    }




        //TODO: Add audio playback
        //TODO: Add audio recording
        //TODO: Add test audio file to manipulate for testing
        //TODO: Create simple filter to filter sound of test filter
}


