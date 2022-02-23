package com.imagepuzzler;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;


public class ImageSeparator {

    public static Image[][] getImageMatrix(Image image, int numberOfPieces) {
        Image[][] imageMatrix = new Image[numberOfPieces][numberOfPieces];
        PixelReader reader = image.getPixelReader();

        int width = ((int) image.getWidth() / (numberOfPieces));
        int height = ((int) image.getHeight() / (numberOfPieces));

        /* If it's not immediately obvious, the increment amount is calculated like this to keep
        image pieces in bounds and prevent overlap when displayed. */
        int incrementAmount = (int) (image.getWidth() + image.getHeight()) / (numberOfPieces*2);

        for(int row = 0, portionX = 0; row < numberOfPieces; row++, portionX+=incrementAmount) {
            for(int column = 0, portionY = 0; column < numberOfPieces; column++, portionY+=incrementAmount) {
                imageMatrix[row][column] = new WritableImage(reader, portionX, portionY, width, height);
            }
        }

        return imageMatrix;
    }
}
