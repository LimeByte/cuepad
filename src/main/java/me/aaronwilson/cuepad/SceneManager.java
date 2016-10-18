package me.aaronwilson.cuepad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.aaronwilson.cuepad.element.Cue;
import me.aaronwilson.cuepad.element.CueScene;

public class SceneManager {

    private ObservableList<CueScene> scenes = FXCollections.observableList(new ArrayList<>());
    private int scene;


    public SceneManager() {
        createScene();
    }


    public void createScene() {
        CueScene scene = new CueScene("Scene " + (scenes.size() + 1));
        scenes.add(scene);
    }


    public void removeScene(CueScene scene) {
        scenes.remove(scene);
        this.scene = 0;
    }


    public void loadScenes(List<CueScene> scenes) {
        this.scenes.clear();
        this.scenes.addAll(scenes);
    }


    public CueScene getCurrentScene() {
        return scenes.get(scene);
    }


    public List<CueScene> getScenes() {
        return Collections.unmodifiableList(scenes);
    }


    public ObservableList<CueScene> getObservableScenes() {
        return scenes;
    }


    public boolean hasNextScene() {
        return scene + 1 < scenes.size();
    }


    public boolean hasPreviousScene() {
        return scene - 1 >= 0;
    }


    public void nextScene() {
        if (hasNextScene()) {
            scene++;
        }
    }


    public void previousScene() {
        if (hasPreviousScene()) {
            scene--;
        }
    }


    public void stopPlayback() {
        for (CueScene scene : scenes) {
            for (Cue cue : scene.getCues()) {
                cue.stop();
            }
        }
    }

}
