package com.heartuner;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.audiofx.Equalizer;

public class AppConfiguration {
    private static AppConfiguration myInstance = null;
    private Context mContext;
    private Equalizer.Settings manualProfile;  //Have manual equalizer profile set up in case of change
    private Equalizer.Settings testProfile;    //Have test equalizer profile set up in case of change
    private int profileSelection; //Maintain track of which profile was last selected.
    private boolean tunerEnabled = false;


    //Profile Selection Management:
    //@var profileSelection:
    //0: none selected: Should only be chosen at first install
    //1: Manual Profile selected: When user selects manual profile
    //2: Test Profile selected: When user selects test profile

    //Sets profile selection.
    //@arg a number between 0-2 representing selection
    public void setProfileSelection(int profileSelection){
        if(profileSelection>2 || profileSelection<0){
            return;
        }
        this.profileSelection = profileSelection;

    }

    //Gets current profile selection.
    //@return A number between 0-2 representing selection.
    public int getProfileSelection(){
        return this.profileSelection;
    }

    //Gets the current test profile saved
    //@return A Equalizer.Settings object that reflects the configuration of the test profile
    public Equalizer.Settings getTestProfile(){
        return this.testProfile;
    }

    //Sets the current test profile saved
    //@param An Equalizer.Settings object that will reflect the configuration of the new test profile
    public void setTestProfile(Equalizer.Settings testProfile){
        this.testProfile = testProfile;
    }

    //Gets the current manual profile saved
    //@return A Equalizer.Settings object that reflects the configuration of the manual profile
    public Equalizer.Settings getManualProfile(){
        return this.manualProfile;
    }

    //Sets the current manual profile saved
    //@param An Equalizer.Settings object that will reflect the configuration of the new manual profile
    public void setManualProfile(Equalizer.Settings manualProfile){
        this.manualProfile = manualProfile;
    }

    //Enables/Disables the Tuner
    //@param A boolean value that will enable (true) or disable (false) the tuner
    public void setTunerEnabled(boolean enable){
        this.tunerEnabled = enable;
    }

    //Gets the state of the tuner, enabled/disabled
    //@return A boolean value that reflects the state of the tuner: enabled (true), disabled (false)
    public boolean getTunerEnable(){
        return this.tunerEnabled;
    }


    protected AppConfiguration(Context mContext){
        this.mContext = mContext;
        //TODO: Add capability to retrieve previous user settings when App was open last
        //TODO: Add serialization to current member variables within AppConfiguration class to use shared preferences
//        SharedPreferences manualProfileData = mContext.getSharedPreferences("com.heartuner.manualProfileData",
//                mContext.MODE_PRIVATE);
//        String imageSaved = manualProfileData.getString("setPic", null);
//        //String msg = "Saved User Name: " + name;
//        if(imageSaved != null) {
//            position = Integer.parseInt(imageSaved);
//        }
    }
    public static synchronized AppConfiguration getInstance(Context myContext){
        if(myInstance==null){
           myInstance = new AppConfiguration(myContext);
        }
        return myInstance;
    }
}
