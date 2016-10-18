package me.aaronwilson.cuepad.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import me.aaronwilson.cuepad.App;
import me.aaronwilson.cuepad.element.Cue;
import me.aaronwilson.cuepad.element.CueAdapter;
import me.aaronwilson.cuepad.element.CueScene;

public class SceneManagerController implements Initializable {

    @FXML
    private Button load;

    @FXML
    private Button save;

    @FXML
    private Button add;

    @FXML
    private Button remove;

    @FXML
    private Button rename;

    @FXML
    private ListView<CueScene> sceneList;


    private Stage stage;
    private RootController rootController;
    private Gson gson;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupGson();
        loadScenes();
        setupLoadSaveControls();
        setupEditControls();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }


    private void setupGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Cue.class, new CueAdapter());
        gson = builder.create();
    }


    private void loadScenes() {
        sceneList.setCellFactory(new Callback<ListView<CueScene>, ListCell<CueScene>>() {
            @Override
            public ListCell<CueScene> call(ListView<CueScene> param) {
                return new SceneCell();
            }
        });

        sceneList.setItems(App.getInstance().getSceneManager().getObservableScenes());
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
                    writer.write(gson.toJson(App.getInstance().getSceneManager().getScenes()));
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
                    App.getInstance().getSceneManager().loadScenes(gson.fromJson(buffer.toString(), type));
                    rootController.updateScene();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }


    private void setupEditControls() {
        add.setOnMouseClicked((event) -> {
            App.getInstance().getSceneManager().createScene();
            rootController.updateScene();
        });

        remove.setOnMouseClicked(event -> {
            ObservableList<CueScene> selected = sceneList.getSelectionModel().getSelectedItems();

            if (selected.size() < sceneList.getItems().size()) {
                for (CueScene scene : selected) {
                    App.getInstance().getSceneManager().removeScene(scene);
                }
                rootController.updateScene();
            }
        });

        rename.setOnMouseClicked(event -> {
            ObservableList<CueScene> selected = sceneList.getSelectionModel().getSelectedItems();

            if (selected.size() == 1) {
                CueScene scene = selected.get(0);

                TextInputDialog dialog = new TextInputDialog(scene.getName());
                dialog.setTitle("Rename Scene");
                dialog.setHeaderText("Please enter a new scene name");
                dialog.setContentText("Scene name:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> {
                    scene.setName(name.trim());
                    loadScenes();
                });
            }
        });
    }


    static class SceneCell extends ListCell<CueScene> {
        @Override
        public void updateItem(CueScene item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            } else {
                setText(null);
            }
        }
    }

}
