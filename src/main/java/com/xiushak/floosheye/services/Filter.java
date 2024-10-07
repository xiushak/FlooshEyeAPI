package com.xiushak.floosheye.services;

import java.awt.image.BufferedImage;

public interface Filter {
    /**
     * Processes the image by applying the filter
     *
     * @param x the x coordinate for the center of the filter effect
     * @param y the y coordinate for the center of the filter effect
     * @throws IllegalStateException if no image has been set
     */
    void processImage(int x, int y) throws IllegalStateException;

    /**
     * Gets the width of the image
     *
     * @return the width of the image
     * @throws IllegalStateException if no image has been set
     */
    int getWidth() throws IllegalStateException;

    /**
     * Gets the height of the image
     *
     * @return the height of the image
     * @throws IllegalStateException if no image has been set
     */

    int getHeight() throws IllegalStateException;

    /**
     * Gets the image in the model
     *
     * @return a copy of the image
     * @throws IllegalStateException if no imgae has been set
     */
    BufferedImage getImage() throws IllegalStateException;

    /**
     * Sets the image of the model
     *
     * @param bi the image to be used
     * @throws IllegalArgumentException if a null image is given
     */
    void setImage(BufferedImage bi) throws IllegalArgumentException;

}
