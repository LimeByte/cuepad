package me.aaronwilson.cuepad.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import me.aaronwilson.cuepad.CueScene;

public class RootController implements Initializable {

    private GridController gridController;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label sceneName;

    @FXML
    private Button previousScene;

    @FXML
    private Button nextScene;

    @FXML
    private Button newScene;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupNestedControllers();
        } catch (IOException e) {
            System.err.println("Failed to load nested FXML files.");
            e.printStackTrace();
            return;
        }

        setupSceneControls();
    }


    /**
     * Loads the directly nested FXML files and adds them to the root pane.
     * 
     * @throws IOException
     */
    private void setupNestedControllers() throws IOException {
        FXMLLoader gridLoader = new FXMLLoader(getClass().getResource("/fxml/grid.fxml"));
        rootPane.setCenter(gridLoader.load());
        gridController = gridLoader.getController();
    }


    /**
     * Registers events and sets up the properties for the scene label and buttons.
     */
    private void setupSceneControls() {
        updateSceneControls();

        previousScene.setOnMouseClicked((event) -> {
            gridController.previousScene();
            updateSceneControls();
        });

        nextScene.setOnMouseClicked((event) -> {
            gridController.nextScene();
            updateSceneControls();
        });

        newScene.setOnMouseClicked((event) -> {
            gridController.createScene();
            updateSceneControls();
        });
    }


    private void updateSceneControls() {
        sceneName.setText(gridController.getScene().getName());
        previousScene.setDisable(!gridController.hasPreviousScene());
        nextScene.setDisable(!gridController.hasNextScene());
    }


    public void onSceneChange(CueScene scene) {
        sceneName.setText(scene.getName());
    }



    @FXML
    public void clearGrid() {
        gridController.clear();
    }

}
