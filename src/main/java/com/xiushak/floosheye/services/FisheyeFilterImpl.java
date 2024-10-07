package com.xiushak.floosheye.services;

import java.awt.image.BufferedImage;

public class FisheyeFilterImpl implements Filter {

    private BufferedImage bi;

    public FisheyeFilterImpl(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    public void processImage(int x, int y) throws IllegalStateException {
        int width = bi.getWidth();
        int height = bi.getHeight();

        System.out.println("Width: " + width + " Height: " + height);
        BufferedImage pbi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // sets all pixels to black transparent for the background
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pbi.setRGB(i, j, 0);
            }
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                double di = i - x;
                double dj = j - y;
                double theta = Math.atan2(dj, di);
                double r = Math.sqrt(di * di + dj * dj);

                double nr = r * r / Math.max(x, y);
                double ni = x + (nr * Math.cos(theta));
                double nj = y + (nr * Math.sin(theta));
                if (ni >= 0 && ni < width && nj >= 0 && nj < height) {
                    pbi.setRGB(i, j, bi.getRGB((int) ni, (int) nj));
                }
            }
        }

        this.bi = pbi;
    }

    @Override
    public int getWidth() throws IllegalStateException {
        return bi.getWidth();
    }

    @Override
    public int getHeight() throws IllegalStateException {
        return bi.getHeight();
    }

    @Override
    public BufferedImage getImage() throws IllegalStateException {
        return this.bi;
    }

    @Override
    public void setImage(BufferedImage bi) throws IllegalArgumentException {
        this.bi = bi;
    }
}
