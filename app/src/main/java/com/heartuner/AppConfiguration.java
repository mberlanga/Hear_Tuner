package com.heartuner;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.util.Log;
import android.view.InputQueue;

public class AppConfiguration {
    public static MediaPlayer mMediaPlayer;
    public static Equalizer mEqualizer;


    private static final String MY_SAVED_SETTINGS  = "MY_CONFIGURATION";
    private static final String MANUAL_PROFILE_LEVEL_KEY = "HearTuner.manualProfile.bandLevel";
    private static final String TEST_PROFILE_LEVEL_KEY = "HearTuner.testProfile.bandLevel";
    private static final String PROFILE_SELECTION_KEY = "HearTuner.profileSelection";
    private static final String HAS_SAVED_KEY = "HearTuner.hasSaved";
    private static final String TUNER_ENABLED_KEY = "HearTuner.tunerEnabled";
    private static final String NUM_BANDS = "HearTuner.numBands";

    private static AppConfiguration myInstance = null;
    private Context mContext;
    private Equalizer.Settings manualProfile;  //Have manual equalizer profile set up in case of change
    private Equalizer.Settings testProfile;    //Have test equalizer profile set up in case of change
    private int profileSelection; //Maintain track of which profile was last selected.
    private boolean tunerEnabled = false; //Tracks state of the tuner and whether it is active
    private SharedPreferences mSavedSettings;
    private boolean hasSavedOnce = false;


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

        //Set Equalizer to profile selected by user
        if(profileSelection == 1){ //Set equalizer settings to manual profile
            this.mEqualizer.setProperties(this.manualProfile);
        }
        else if(profileSelection == 2){ //Set equalizer settings to test profile
            this.mEqualizer.setProperties(this.testProfile);
        }


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
        Log.d("HearTuner.debug", "In AppConfiguration Constructor");
        this.mContext = mContext;
        //TODO: This will be changed to select what was saved previously. Currently just uses default
        // settings.

        this.profileSelection = 0; //Default set profile selection to unselected
        Log.d("HearTuner.debug", "Set profile to default: " + this.profileSelection);
        this.tunerEnabled = false;  //Default disable the tuner on startup
        Log.d("HearTuner.debug", "Set tunerEnable to default: " + this.tunerEnabled);

        Log.d("HearTuner.debug", "Setting Hearing Profiles...");

        //Initialize a media player and associated equalizer
        try { //Setup static Media Player that plays song with dynamic range
            this.mMediaPlayer = MediaPlayer.create(mContext, R.raw.test);
        }
        catch (Exception e){e.printStackTrace();}

        //Setup Equalizer to be associated with MediaPlayer
        this.mEqualizer = new Equalizer(0,this.mMediaPlayer.getAudioSessionId());
        this.mEqualizer.setEnabled(true); //Enable Equalizer

        //Set all bands to volume levels of 0 millibels amplification
        for(short i=0;i < this.mEqualizer.getNumberOfBands();i++){
            this.mEqualizer.setBandLevel(i,(short)0);
        }
        this.manualProfile = this.mEqualizer.getProperties();
        Log.d("HearTuner.debug", "Manual Profile set to default");
        this.testProfile = this.mEqualizer.getProperties();
        Log.d("HearTuner.debug", "Test Profile set to default");

        Log.d("HearTuner.debug", "" + this.toString());

        Log.d("HearTuner.debug", "About to enter Retrieve Data...\n");
        retrieveData();
        Log.d("HearTuner.debug", "The new values are:\n" + this.toString());


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

    @Override
    public String toString() {
        return "profileSelection=" + profileSelection + ";" +
                "tunerEnabled=" + tunerEnabled + ";" +
                "ManualSettings=" + manualProfile.toString() + ";" +
                "testSettings=" + testProfile.toString() + ";";
    }









    public void saveData(){
        Log.d("HearTuner.debug", "In Save Data...");
        int oldProfile = this.getProfileSelection();
        Log.d("HearTuner.debug", "Profile originally at: " + oldProfile);
        this.hasSavedOnce = true;

        //Begin saving of settings by intializing saved settings profile of shared preferences
        this.mSavedSettings = this.mContext.getSharedPreferences(this.MY_SAVED_SETTINGS, Context.MODE_PRIVATE);
        // Initialize the editor of the shared preferences
        SharedPreferences.Editor editor = this.mSavedSettings.edit();
        // Save whether the settings have ever been saved
        editor.putString(this.HAS_SAVED_KEY, Boolean.toString(this.hasSavedOnce));
        editor.commit();

        Log.d("HearTuner.debug", "hasSaved is saved as:" + Boolean.toString(this.hasSavedOnce));

        //Save the number of bands within this particular equalizer
        editor.putString(this.NUM_BANDS,Short.toString(this.mEqualizer.getNumberOfBands()));
        editor.commit();
        Log.d("HearTuner.debug", "numBands is saved as:" + Short.toString(this.mEqualizer.getNumberOfBands()));
        //Save the band levels for each band in the manual profile
        this.setProfileSelection(1);
        for(short i=0;i<this.mEqualizer.getNumberOfBands();i++){
            editor.putString(this.MANUAL_PROFILE_LEVEL_KEY+i,Short.toString(this.mEqualizer.getBandLevel(i)));
            editor.commit();
            Log.d("HearTuner.debug", "Manual Band level [" + i + "] is saved as:" + Short.toString(this.mEqualizer.getBandLevel(i)));
        }

        //Save the band levels for each band in the test profile
        this.setProfileSelection(2);
        for(short i=0;i<this.mEqualizer.getNumberOfBands();i++){
            editor.putString(this.TEST_PROFILE_LEVEL_KEY+i,Short.toString(this.mEqualizer.getBandLevel(i)));
            editor.commit();
            Log.d("HearTuner.debug", "Test Band level [ " + i + "] is saved as:" + Short.toString(this.mEqualizer.getBandLevel(i)));
        }

        // Reset the tuner profile selection to its state before saving since it must move between states
        // to access Equalizer getter functions for its settings.
        this.setProfileSelection(oldProfile);
        Log.d("HearTuner.debug", "oldprofile is " + oldProfile +" and current profile should remain " +this.getProfileSelection());
        // Save the profile that was currently selected at the time of saving
        editor.putString(this.PROFILE_SELECTION_KEY,Integer.toString(oldProfile));
        editor.commit();
        Log.d("HearTuner.debug", "profileSelection set as: " + Integer.toString(this.getProfileSelection()));
        //Save whether the tuner was enabled or disabled upon saving
        editor.putString(this.TUNER_ENABLED_KEY,Boolean.toString(this.getTunerEnable()));
        editor.commit();
        Log.d("HearTuner.debug", "tunerEnabled saved as: " + Boolean.toString(this.getTunerEnable()));


    }








    public void retrieveData(){
        Log.d("HearTuner.debug", "In Retrieve Data...");

        this.mSavedSettings = this.mContext.getSharedPreferences(this.MY_SAVED_SETTINGS,Context.MODE_PRIVATE);
        String temp = this.mSavedSettings.getString(this.HAS_SAVED_KEY, null);
        Log.d("HearTuner.debug", "About to enter conditional... temp is:" + Boolean.parseBoolean(temp));
//        temp = this.mSavedSettings.getString("HearTuner.hasSaved", null);
//        Log.d("HearTuner.debug", "After save, temp is: " + temp);
//        this.saveData();

        if(temp !=null){
            Log.d("HearTuner.debug", "Data exists in shared preferences... so get all the data");
            this.hasSavedOnce = Boolean.parseBoolean(temp);
            temp = this.mSavedSettings.getString(this.NUM_BANDS, null);
            int numBands = Integer.parseInt(temp);
            for(short i=0;i<numBands;i++){
                temp = this.mSavedSettings.getString(this.MANUAL_PROFILE_LEVEL_KEY + i, null);
                short bandLevel = Short.parseShort(temp);
                this.mEqualizer.setBandLevel(i,bandLevel);
            }
            this.setManualProfile(this.mEqualizer.getProperties());
            for(short i=0;i<numBands;i++){
                temp = this.mSavedSettings.getString(this.TEST_PROFILE_LEVEL_KEY + i, null);
                short bandLevel = Short.parseShort(temp);
                this.mEqualizer.setBandLevel(i,bandLevel);
            }
            this.setTestProfile(this.mEqualizer.getProperties());

            temp = this.mSavedSettings.getString(this.PROFILE_SELECTION_KEY,null);
            this.profileSelection = Integer.parseInt(temp);
            this.setProfileSelection(this.profileSelection);

            temp = this.mSavedSettings.getString(this.TUNER_ENABLED_KEY,null);
            this.tunerEnabled = Boolean.parseBoolean(temp);
            this.setTunerEnabled(this.tunerEnabled);

        }


    }

}
