package gui.controllers;

import config.Models.Gamemode;
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
    private Label lbName;
    @FXML
    private Label lbCardQuantity;
    @FXML
    private Label lbPlayerQuantity;


    private static final Logger LOG = Logger.getLogger(gui.controllers.SettingsTabController.class.getName());

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbVersion.setText("Reshuffled V" + main.Main.VERSION);
    }

    public void onStartGame() {
        try {
            serial.requests.ReqInit.parseRequest();
        } catch (SerialPortException | InterruptedException ex) {
            LOG.severe(ex);
        }
    }

    public void onChangeActualGamemode(Gamemode g) {

        lbName.setText(g.getName());
    }

}
