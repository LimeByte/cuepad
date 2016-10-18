package me.aaronwilson.cuepad.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import me.aaronwilson.cuepad.App;
import me.aaronwilson.cuepad.element.Cue;

public class GridController implements Initializable {

    public static final int ROWS = 5;
    public static final int COLUMNS = 6;



    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadScene();
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
