package me.aaronwilson.cuepad.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import me.aaronwilson.cuepad.Cue;
import me.aaronwilson.cuepad.CueScene;

public class GridController implements Initializable {

    private static final int ROWS = 5;
    private static final int COLUMNS = 6;

    private List<CueScene> scenes = new ArrayList<>();
    private int scene;

    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (scenes.isEmpty()) {
            createScene();
        }

        loadScene(0);
    }


    public void createScene() {
        CueScene scene = new CueScene("Scene " + (scenes.size() + 1), ROWS * COLUMNS);
        scenes.add(scene);
    }


    private void loadScene(int scene) {
        this.scene = scene;
        grid.getChildren().clear();

        List<Cue> cues = scenes.get(scene).getCues();

        for (int i = 0; i < Math.min(cues.size(), ROWS * COLUMNS); i++) {
            int row = i / COLUMNS;
            int column = i % COLUMNS;
            grid.add(cues.get(i), column, row);
        }
    }


    public CueScene getScene() {
        return scenes.get(scene);
    }


    public boolean hasNextScene() {
        return scene + 1 < scenes.size();
    }


    public boolean hasPreviousScene() {
        return scene - 1 >= 0;
    }


    public void nextScene() {
        if (hasNextScene()) {
            loadScene(scene + 1);
        }
    }


    public void previousScene() {
        if (hasPreviousScene()) {
            loadScene(scene - 1);
        }
    }


    public void clear() {
        for (Node child : grid.getChildren()) {
            ((Cue) child).clear();
        }
    }

}
