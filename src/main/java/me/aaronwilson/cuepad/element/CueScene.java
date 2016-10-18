package me.aaronwilson.cuepad.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.aaronwilson.cuepad.controller.GridController;

public class CueScene {

    private String name;
    private List<Cue> cues;


    @SuppressWarnings("unused")
    private CueScene() {
        // For GSON
    }


    public CueScene(String name) {
        this.name = name;
        initialiseScene();
    }


    /**
     * Fills the list with empty cues.
     */
    private void initialiseScene() {
        cues = new ArrayList<>();

        for (int i = 0; i < GridController.ROWS * GridController.COLUMNS; i++) {
            cues.add(new Cue());
        }
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Cue> getCues() {
        return Collections.unmodifiableList(cues);
    }

}
