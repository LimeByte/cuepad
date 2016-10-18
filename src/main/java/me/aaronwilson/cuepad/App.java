package me.aaronwilson.cuepad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = MIN_WIDTH / 16 * 9;

    private static App instance;

    private SceneManager sceneManager;

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;

        sceneManager = new SceneManager();

        primaryStage.setTitle("CuePad");
        primaryStage.setAlwaysOnTop(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toString());
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);

        primaryStage.show();
    }


    public static App getInstance() {
        return instance;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

}
