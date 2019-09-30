package gui.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpTabController implements Initializable {

    @FXML
    private JFXComboBox<String> cbFaecher;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbFaecher.getItems().add("AIIT");
        cbFaecher.getItems().add("MATHE");
        cbFaecher.getItems().add("GGP");
    }


    public void onGetMahnung() {


    }
}
