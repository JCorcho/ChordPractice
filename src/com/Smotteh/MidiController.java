package com.Smotteh;

import javax.sound.midi.*;

public class MidiController {

    //GLOBAL CLASS VARIABLES
    MidiDevice device;

    public void setApp(App app) {
        this.app = app;
    }

    App app;

    //CONSTRUCTORS
    public MidiController() {
        getMidiDevice();
    }

    //LOGICAL METHODS

    public void getMidiDevice() {
        try {

            MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
            for(int i=0;i<infos.length;i++)
            {
                System.out.println(infos[i].getName() + " - " + infos[i].getDescription());
                device = MidiSystem.getMidiDevice(infos[i]);
            }

            device.open();
            System.out.println(device.getDeviceInfo() + " Was Opened");


        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }


    }

    public void sendKeyPress(char key) {
        System.out.println("Key Pressed: " + key);
        try {
            app.checkForNewChord(key);
        } catch(Exception e) {

        }

    }

    public void sendKeyRelease(char key) {
       // System.out.println("Key Released: " + key);
    }


}