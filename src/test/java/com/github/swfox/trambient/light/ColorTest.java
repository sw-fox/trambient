package com.github.swfox.trambient.light;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorTest {

    @Test
    public void scaleTimesTwo(){
        Color color = new Color(1, 1, 1);

        color.scale(2);

        assertEquals(2,color.getBlue(),"blue should be 2");
        assertEquals(2,color.getGreen(),"green should be 2");
        assertEquals(2,color.getRed(),"red should be 2");
    }

    @Test
    public void scaleTimesByZeroPointFive(){
        Color color = new Color(4, 4, 4);

        color.scale(0.5);

        assertEquals(2,color.getBlue(),"blue should be 2");
        assertEquals(2,color.getGreen(),"green should be 2");
        assertEquals(2,color.getRed(),"red should be 2");
    }

    @Test
    public void saturationRedTimesOnePointFive(){
        Color color = new Color(2, 1, 1);

        color.saturation(1.5);

        assertEquals(1,color.getBlue(),"blue should be 1");
        assertEquals(1,color.getGreen(),"green should be 1");
        assertEquals(3,color.getRed(),"red should be 3");
    }

    @Test
    public void saturationGreenTimesOnePointFive(){
        Color color = new Color(1, 2, 1);

        color.saturation(1.5);

        assertEquals(1,color.getBlue(),"blue should be 1");
        assertEquals(3,color.getGreen(),"green should be 3");
        assertEquals(1,color.getRed(),"red should be 1");
    }

    @Test
    public void saturationBlueTimesThree(){
        Color color = new Color(1, 1, 3);

        color.saturation(3);

        assertEquals(9,color.getBlue(),"blue should be 9");
        assertEquals(1,color.getGreen(),"green should be 1");
        assertEquals(1,color.getRed(),"red should be 1");
    }
}
