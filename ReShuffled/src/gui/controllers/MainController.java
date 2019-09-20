package gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;

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


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbVersion.setText("Reshuffled V" + Main.VERSION);
    }

    public void onStartGame() {

    }


}
