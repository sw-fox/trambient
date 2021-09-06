package com.github.swfox.trambient.light;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screengraber {

    public enum MODE {ALL, MID, OUTER}

    public Color grab(MODE mode) {
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            Color color = getColor(image, mode);
            return color;
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return new Color(0, 0, 0);
    }

    private Color getColor(BufferedImage image, MODE mode) {
        int width = 100;
        int height = 100;
        Image small = image.getScaledInstance(width, height, Image.SCALE_FAST);
        int red = 0;
        int green = 0;
        int blue = 0;
        int counter = 0;
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (useForCalculation(width, height, i, j, mode)) {
                    counter++;
                    int color = toBufferedImage(small).getRGB(i, j);
                    red += (color & 0x00ff0000) >> 16;
                    green += (color & 0x0000ff00) >> 8;
                    blue += color & 0x000000ff;
                }
            }
        }
        Color retval = new Color(red / counter, green / counter, blue / counter);
        retval.saturation(1.3);
        retval.scale(0.6);
        return retval;
    }

    private boolean useForCalculation(int sizeX, int sizeY, int i, int j, MODE mode) {
        double percentage = 0.2;
        switch (mode) {
            case OUTER:
                return (sizeX * percentage > i) || (sizeY * percentage > j) || (sizeX * (1 - percentage) < i) || (sizeY * (1 - percentage) < j);
            case MID:
                return !useForCalculation(sizeX, sizeY, i, j, MODE.OUTER);
            case ALL:
            default:
                return true;
        }
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
