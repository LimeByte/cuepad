package me.aaronwilson.cuepad.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import me.aaronwilson.cuepad.element.Cue;
import me.aaronwilson.cuepad.element.CueAdapter;
import me.aaronwilson.cuepad.element.CueScene;

public class RootController implements Initializable {

    private Stage stage;
    private GridController gridController;
    private Gson gson;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Button load;

    @FXML
    private Button save;

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
        setupGson();

        try {
            setupNestedControllers();
        } catch (IOException e) {
            System.err.println("Failed to load nested FXML files.");
            e.printStackTrace();
            return;
        }

        setupLoadSaveControls();
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
        save.setOnMouseClicked((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Scenes");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("CuePad Scenes", "*.cps"));

            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(gson.toJson(gridController.getScenes()));
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        load.setOnMouseClicked((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Scenes");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("CuePad Scenes", "*.cps"));

            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                try {
                    FileReader reader = new FileReader(file);
                    StringBuffer buffer = new StringBuffer();
                    int numCharsRead;
                    char[] charArray = new char[1024];
                    while ((numCharsRead = reader.read(charArray)) > 0) {
                        buffer.append(charArray, 0, numCharsRead);
                    }
                    reader.close();

                    Type type = new TypeToken<List<CueScene>>() {}.getType();
                    gridController.loadScenes(gson.fromJson(buffer.toString(), type));
                    updateSceneControls();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
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


    private void setupGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Cue.class, new CueAdapter());
        gson = builder.create();
    }


    public void onSceneChange(CueScene scene) {
        sceneName.setText(scene.getName());
    }



    @FXML
    public void clearGrid() {
        gridController.clear();
    }

}
