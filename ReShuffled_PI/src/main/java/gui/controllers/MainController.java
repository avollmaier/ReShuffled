package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jssc.SerialPortException;
import logging.Logger;
import main.Main;

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
    private SettingsTabController  settingsTabController;

    private static final Logger LOG = Logger.getLogger(gui.controllers.SettingsTabController.class.getName());

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbVersion.setText("Reshuffled V" + Main.config.getVersion());
    }

    public void onStartGame() {
        try {
            serial.requests.ReqInit.parseRequest();
        } catch (SerialPortException | InterruptedException ex) {
            LOG.severe(ex);
        }
    }

}
