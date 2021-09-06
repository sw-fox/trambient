package com.github.swfox.trambient.light;

public class Color {
    private int red, green, blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public void saturation(double fact){
        if(red > green && red > blue){
            red *= fact;
        }else if(green > red && green > blue){
            green *= fact;
        }else if(blue > green && blue > red){
            blue *= fact;
        }
    }

    public void scale(double scale){
        red *= scale;
        green *= scale;
        blue *= scale;
    }
}
