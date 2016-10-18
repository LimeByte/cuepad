package me.aaronwilson.cuepad.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import me.aaronwilson.cuepad.App;
import me.aaronwilson.cuepad.element.Cue;

public class GridController implements Initializable {

    public static final int ROWS = 5;
    public static final int COLUMNS = 6;

    private static final double ROW_WIDTH = 100.0 / ROWS;
    private static final double COLUMN_WIDTH = 100.0 / COLUMNS;

    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadScene();
        setupGrid();
    }


    private void setupGrid() {
        for (int i = 0; i < COLUMNS; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(COLUMN_WIDTH);
            grid.getColumnConstraints().add(column);
        }

        for (int i = 0; i < ROWS; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(ROW_WIDTH);
            grid.getRowConstraints().add(row);
        }
    }


    public void loadScene() {
        grid.getChildren().clear();

        List<Cue> cues = App.getInstance().getSceneManager().getCurrentScene().getCues();

        for (int i = 0; i < Math.min(cues.size(), ROWS * COLUMNS); i++) {
            int row = i / COLUMNS;
            int column = i % COLUMNS;
            grid.add(cues.get(i), column, row);
        }
    }

}
