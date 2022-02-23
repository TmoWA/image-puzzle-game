package com.imagepuzzler;

import java.io.File;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Controller {

    private int correctlyPlacedImagePortions = 0;
    private int imagePiecesQuantity = 4; //Note: imagePiecesQuantity refers to the number per row/column, not the total.
    private ImagePuzzlePiece selectedPiece = null;
    private String imagePath;
    private static final PseudoClass COMPLETE = PseudoClass.getPseudoClass("complete");

    @FXML
    private GridPane gridPane;
    @FXML
    private ComboBox<String> imageComboBox;
    @FXML
    private ComboBox<String> difficultyComboBox;


    @FXML
    private void initialize() {
        imagePath = "src/castle-picture.jpg";
        generatePuzzle();
    }

    private void imageSelected(MouseEvent event) {

        ImagePuzzlePiece temp;

        if(event.getSource() instanceof ImagePuzzlePiece) {
            temp = (ImagePuzzlePiece) event.getSource();

        } else {
            return;
        }

        if(selectedPiece != null) {
            if(selectedPiece.isMovable() && temp.isMovable()) {
                swapImages(selectedPiece, temp);
                selectedPiece.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                temp.setCurrentIndex("" + GridPane.getColumnIndex(temp) + GridPane.getRowIndex(temp));
                selectedPiece.setCurrentIndex("" + GridPane.getColumnIndex(selectedPiece) + GridPane.getRowIndex(selectedPiece));

                //Pieces are only movable when in the wrong location.
                if(!selectedPiece.isMovable()) {
                    correctlyPlacedImagePortions++;
                }

                if(!temp.isMovable()) {
                    correctlyPlacedImagePortions++;
                }
            }

            selectedPiece = null;
        } else if(temp.isMovable()){
            selectedPiece = temp;
            selectedPiece.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        }

        checkForVictory();
    }

    private void swapImages(Node n1, Node n2) {
        int temp = GridPane.getRowIndex(n1);
        GridPane.setRowIndex(n1, GridPane.getRowIndex(n2));
        GridPane.setRowIndex(n2, temp);

        temp = GridPane.getColumnIndex(n1);
        GridPane.setColumnIndex(n1, GridPane.getColumnIndex(n2));
        GridPane.setColumnIndex(n2, temp);
    }

    @FXML
    public void generatePuzzle() {
        correctlyPlacedImagePortions = 0;
        checkForVictory(); // Called to reset the completion state
        gridPane.getChildren().clear(); // Removes old images. Otherwise, there is some weird overlap and it's all messed up

        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        Image[][] imagePortions = ImageSeparator.getImageMatrix(image, imagePiecesQuantity);
        ImagePuzzlePiece imagePiece;

        for(int row = 0; row < imagePortions.length; row++) {
            for(int column = 0; column < imagePortions[0].length; column++) {

                imagePiece = new ImagePuzzlePiece(("" + row + column), imagePortions[row][column]);

                imagePiece.setOnMouseClicked(this::imageSelected);

                gridPane.add(imagePiece, column, row);

                if(column > 0 && row == column) {
                    swapImages(imagePiece, gridPane.getChildren().get(0));
                }
            }
        }
    }

    @FXML
    private void selectImage() {

        switch(imageComboBox.getValue()) {
            case "Apple Image":
                imagePath = "src/apple.jpg";
                break;
            case "Mountain Image":
                imagePath = "src/mountain.jpg";
                break;
            case "Castle Image":
            default:
                imagePath = "src/castle-picture.jpg";
        }

        generatePuzzle();
    }

    @FXML
    private void selectDifficulty() {

        switch(difficultyComboBox.getValue()) {
            case "9 Pieces":
                imagePiecesQuantity = 3;
                break;
            case "16 Pieces":
                imagePiecesQuantity = 4;
                break;
            case "32 Pieces":
                imagePiecesQuantity = 8;
                break;
            default:
                imagePiecesQuantity = 4;
        }

        generatePuzzle();
    }

    private void checkForVictory() {
        gridPane.pseudoClassStateChanged(COMPLETE, correctlyPlacedImagePortions >= (imagePiecesQuantity * imagePiecesQuantity));
    }
}