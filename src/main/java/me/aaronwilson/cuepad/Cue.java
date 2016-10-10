package me.aaronwilson.cuepad;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.TextAlignment;

public class Cue extends BorderPane {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 60;

    private Label label;
    private AudioClip sound;


    public Cue() {
        getStyleClass().add("cue");

        setMinWidth(WIDTH);
        setMinHeight(HEIGHT);

        makeContent();

        handleDragEvents();
        handleClickEvents();
    }


    private void makeContent() {
        label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        setCenter(label);
    }


    /**
     * Registers the appropriate events for handling the drag and drop of files onto the cue.
     */
    private void handleDragEvents() {
        setOnDragOver((event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles() && db.getFiles().size() < 2) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });

        setOnDragDropped((event) -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                loadFile(db.getFiles().get(0));
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }


    /**
     * Registers the appropriate events for handling clicking on a cue.
     */
    private void handleClickEvents() {
        setOnMousePressed((event) -> {
            if (!event.isSynthesized() && sound != null) {
                switch (event.getButton()) {
                    case MIDDLE:
                        break;
                    case NONE:
                        break;
                    case PRIMARY:
                        sound.play();
                        break;
                    case SECONDARY:
                        break;
                    default:
                        break;
                }
            }
        });

        setOnTouchPressed((event) -> {
            if (sound != null) {
                sound.play();
            }
        });
    }


    private void loadFile(File file) {
        try {
            sound = new AudioClip(file.toURI().toString());

            getStyleClass().add("loaded");

            String text = file.getName();
            int pos = text.lastIndexOf(".");
            label.setText(pos > 0 ? text.substring(0, pos) : text);
        } catch (Exception exception) {
            // TODO Error Message
        }
    }


    public void clear() {
        sound = null;
        label.setText("");
        getStyleClass().remove("loaded");
    }


}
