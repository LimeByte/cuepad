package me.aaronwilson.cuepad.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class RootController implements Initializable {

    @FXML
    private BorderPane rootPane;


    private GridController gridController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setupNestedControllers();
        } catch (IOException e) {
            System.err.println("Failed to load nested FXML files.");
            e.printStackTrace();
        }
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


    @FXML
    public void clearGrid() {
        gridController.clear();
    }

}
