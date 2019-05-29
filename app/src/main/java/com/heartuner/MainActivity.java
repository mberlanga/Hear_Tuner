package com.heartuner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter mBlueAdapter; // To be used to interact with device Bluetooth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Condense set up of bluetooth verification into another Bluetooth.java file
        //      so that one can just run a bluetooth_init() function
        //Set up Bluetooth Adapter
        int intVal = 1;
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();  //Assigns default adapter
        if(mBlueAdapter == null){ //Displays error message in logs if failure
            Log.d("HearTuner.Errors","Device will not support Bluetooth");

        }
        if(!mBlueAdapter.isEnabled()){ //Attempts to enable Bluetooth if it is not enabled
            Intent enableIntent =  new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  //Intent to try to enable bluetooth
            startActivityForResult(enableIntent, intVal); //Will start bluetooth as a separate activity
        }

        //Make the device discoverable to other devices
        Intent deviceIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE); //Create intent to request device discovery
        deviceIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); //Add extra time to device discovery time
        startActivity(deviceIntent); // start the activity to be discovered

        //Get the paired devices
        Set<BluetoothDevice> pairedDevicesList = mBlueAdapter.getBondedDevices(); //Gets bonded devices and adds them to devices list
        if(pairedDevicesList.size() > 0){ //Attempts to list devices if the device list is greater than 0
            for(BluetoothDevice device: pairedDevicesList){ //Iterate over all items in device list
                String deviceName = device.getName(); //assigns String to device name
                String deviceHardwareAddress = device.getAddress(); //assigns String to device address

                Log.d("HearTuner.Bluetooth", "Device: "+ deviceName + "\n");
                Log.d("HearTuner.Bluetooth", "Address: "+ deviceHardwareAddress + "\n");
            }
        }


        //Setup Buttons for Main Menu/Title Screen
        Button enableButton = (Button)findViewById(R.id.enable_button);   //Initialize enable button
        Button disableButton = (Button)findViewById(R.id.disable_button); //Initialize disable button
        Button settingsButton = (Button)findViewById(R.id.settings_button); //Initialize settings button


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

            }
        });

        disableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast disableToast = Toast.makeText(getApplicationContext(),
                        "Disabled!",
                        Toast.LENGTH_SHORT
                );
                disableToast.show();
            }
        });

        //TODO: Add layout to transition to after settings button has been pressed
        //TODO: Create object to hold settings features to be configured

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast settingsToast = Toast.makeText(getApplicationContext(),
                        "Settings!",
                        Toast.LENGTH_SHORT);
                settingsToast.show();
                Intent settingsIntent = new Intent(getApplicationContext(), settings.class);
                startActivity(settingsIntent);


            }
        });
    }

    //TODO: Add audio playback
    //TODO: Add audio recording
    //TODO: Add test audio file to manipulate for testing
    //TODO: Create simple filter to filter sound of test filter
    }

