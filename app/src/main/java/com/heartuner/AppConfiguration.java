package com.heartuner;

import android.media.audiofx.Equalizer;

public class AppConfiguration {
    private static AppConfiguration myInstance = null;

    private Equalizer.Settings manualProfile;  //Have manual equalizer profile set up in case of change
    private Equalizer.Settings testProfile;    //Have test equalizer profile set up in case of change
    private int profileSelection; //Maintain track of which profile was last selected.


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

    public Equalizer.Settings getTestProfile(){
        return this.testProfile;
    }
    public void setTestProfile(Equalizer.Settings testProfile){
        this.testProfile = testProfile;
    }
    public Equalizer.Settings getManualProfile(){
        return this.manualProfile;
    }
    public void setManualProfile(Equalizer.Settings manualProfile){
        this.manualProfile = manualProfile;
    }


    protected AppConfiguration(){

    }
    public static synchronized  AppConfiguration getInstance(){
        if(myInstance==null){
           myInstance = new AppConfiguration();
        }
        return myInstance;
    }
}
