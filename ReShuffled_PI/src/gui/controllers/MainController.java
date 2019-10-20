package gui.controllers;

import main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jssc.SerialPortException;
import logging.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label lbVersion;
    @FXML
    private static Label lbName;
    @FXML
    private Label lbCardQuantity;
    @FXML
    private Label lbPlayerQuantity;
    @FXML
    private SettingsTabController settingsTabController;

    private static final Logger LOG = Logger.getLogger(MainController.class.getName());

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbVersion.setText("Reshuffled V" + Main.VERSION);
    }

    public void onStartGame() {

    }

}
