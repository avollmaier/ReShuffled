/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alois
 */
public class HomeController implements Initializable {

    @FXML Label tfVersion;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tfVersion.setText("ReShuffled Version " + main.Main.VERSION);
    }

    @FXML
    public void onShuffle() {
        MainController.getInstance().loadShuffleDialog();
    }

}
