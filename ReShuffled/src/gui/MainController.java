package gui;

import config.ConfigService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label gamemodeStatus;
    @FXML
    private Label versionStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        versionStatus.setText("Reshuffled V" + Main.VERSION);
        gamemodeStatus.setText("Gamemode: " + "gfd");
    }
}
