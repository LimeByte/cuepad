package me.aaronwilson.cuepad.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.aaronwilson.cuepad.App;
import me.aaronwilson.cuepad.element.CueScene;

public class RootController implements Initializable {

    private Stage stage;
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
    private Button manageScenes;

    @FXML
    private Button stop;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupNestedControllers();
        } catch (IOException e) {
            System.err.println("Failed to load nested FXML files.");
            e.printStackTrace();
            return;
        }

        setupLoadSaveControls();
        setupPlaybackControls();
        setupSceneControls();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
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


    private void setupLoadSaveControls() {
        manageScenes.setOnMouseClicked(event -> {
            final Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(stage);
            popup.setTitle("Manage Scenes");
            popup.setResizable(false);
            popup.setAlwaysOnTop(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manage-scenes.fxml"));
            Scene scene;
            try {
                scene = new Scene(loader.load());
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toString());
                popup.setScene(scene);

                SceneManagerController controller = loader.getController();
                controller.setStage(popup);
                controller.setRootController(this);

                popup.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Registers events for the playback slider and buttons.
     */
    private void setupPlaybackControls() {
        stop.setOnMouseClicked(event -> {
            App.getInstance().getSceneManager().stopPlayback();
        });
    }


    /**
     * Registers events and sets up the properties for the scene label and buttons.
     */
    private void setupSceneControls() {
        updateScene();

        previousScene.setOnMouseClicked((event) -> {
            App.getInstance().getSceneManager().previousScene();
            updateScene();
        });

        nextScene.setOnMouseClicked((event) -> {
            App.getInstance().getSceneManager().nextScene();
            updateScene();
        });
    }


    public void updateScene() {
        gridController.loadScene();
        sceneName.setText(App.getInstance().getSceneManager().getCurrentScene().getName());
        previousScene.setDisable(!App.getInstance().getSceneManager().hasPreviousScene());
        nextScene.setDisable(!App.getInstance().getSceneManager().hasNextScene());
    }


    public void onSceneChange(CueScene scene) {
        sceneName.setText(scene.getName());
    }

}
