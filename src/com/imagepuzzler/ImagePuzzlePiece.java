package com.imagepuzzler;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePuzzlePiece extends ImageView {

    private boolean movable;
    private String currentIndex;
    private final String correctIndex;

    public ImagePuzzlePiece(String correctIndex, Image image) {
        super(image);
        this.movable = true;
        this.currentIndex = "";
        this.correctIndex = correctIndex;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setCurrentIndex(String index) {
        currentIndex = index;

        if(currentIndex.equals(correctIndex)) {
            movable = false;
        }
    }
}
