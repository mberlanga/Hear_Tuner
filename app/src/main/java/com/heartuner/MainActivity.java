package com.heartuner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter mBlueAdapter; // To be used to interact with device Bluetooth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
