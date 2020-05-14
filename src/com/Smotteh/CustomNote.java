package com.Smotteh;

public class CustomNote {

    char quality;
    boolean pressed;

    public CustomNote(char quality) {
        this.quality = quality;
        pressed = false;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }


    public char getQuality() {
        return quality;
    }

    public void setQuality(char quality) {
        this.quality = quality;
    }


}
