package me.aaronwilson.cuepad.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import me.aaronwilson.cuepad.Cue;

public class GridController implements Initializable {

    private static final int ROWS = 5;
    private static final int COLUMNS = 6;

    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                grid.add(new Cue(), column, row);
            }
        }
    }


    public void clear() {
        for (Node child : grid.getChildren()) {
            ((Cue) child).clear();
        }
    }

}
