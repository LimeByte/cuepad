package me.aaronwilson.cuepad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CueScene {

    private String name;
    private List<Cue> cues;


    public CueScene(String name, int size) {
        this.name = name;
        initialiseScene(size);
    }


    /**
     * Fills the list with empty cues.
     * 
     * @param size the required size of the list
     */
    private void initialiseScene(int size) {
        cues = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            cues.add(new Cue());
        }
    }


    public String getName() {
        return name;
    }


    public List<Cue> getCues() {
        return Collections.unmodifiableList(cues);
    }

}
