package me.aaronwilson.cuepad.element;

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
        getStyleClass().add(SwatchColor.GREEN.getStyleClass());

        setMinWidth(WIDTH);
        setMinHeight(HEIGHT);

        makeContent();

        handleDragEvents();
        handleClickEvents();
    }


    public Cue(String name, String filePath) {
        this();

        if (!filePath.isEmpty()) {
            loadFile(name, filePath);
        }
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
        String text = file.getName();
        int pos = text.lastIndexOf(".");
        loadFile(pos > 0 ? text.substring(0, pos) : text, file.toURI().toString());
    }


    private void loadFile(String name, String filePath) {
        try {
            sound = new AudioClip(filePath);

            getStyleClass().add("loaded");
            label.setText(name);
        } catch (Exception exception) {
            // TODO Error Message
        }
    }


    public String getName() {
        return label.getText();
    }


    public String getSource() {
        return sound == null ? "" : sound.getSource();
    }


    public void clear() {
        sound = null;
        label.setText("");
        getStyleClass().removeAll("loaded");
    }

}
