/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import data.config.data.GamemodeModel;
import data.config.service.Config;
import data.game.GameModel;
import data.game.PlayerModel;
import gui.services.AlertService;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 *
 * @author alois
 */
public class StartupController implements Initializable {

    @FXML
    BorderPane rootBorderPane;
    @FXML
    JFXListView playerNameList;
    @FXML
    JFXComboBox cbAutoDealValue;
    @FXML
    JFXCheckBox chbAutoDeal;

    @FXML
    JFXComboBox cbGamemodes;

    @FXML
    JFXTextField tfGamemodeName;

    @FXML
    JFXComboBox cbCardQuantity;
    @FXML
    JFXComboBox cbPlayerQuantity;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        GameModel.clearInstance();

        //FILL GAMEMODES COMBO BOX
        ObservableList<String> gamemodeNames = FXCollections.observableArrayList();
        for (GamemodeModel gamemode : Config.getInstance().getGamemodes()) {
            gamemodeNames.add(gamemode.getName());
        }
        cbGamemodes.setItems(gamemodeNames);

        //FILL PLAYER, CARD AND AUTODEAL QUANTITY COMBO BOX
        ObservableList<Integer> intValues = FXCollections.observableArrayList();
        for (int i = 0; i <= 30; i++) {
            intValues.add(i);
        }
        cbAutoDealValue.setItems(intValues);
        cbCardQuantity.setItems(intValues);
        cbPlayerQuantity.setItems(intValues);

    }

    private void fillPlayerList(GamemodeModel selectedGamemode) {
        final ObservableList<String> playerNames = FXCollections.observableArrayList();

        for (int i = 1; i <= selectedGamemode.getPlayerQuantity(); i++) {
            playerNames.add("Player " + i);
        }

        playerNameList.setItems(playerNames);
        playerNameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    public void onGamemodeChanged() {
        GamemodeModel selectedGamemode = null;
        
        String selectedGamemodeName = cbGamemodes.getValue().toString();

        //FIND SELECTED GAMEMODE OBJECT
        for (GamemodeModel gamemode : Config.getInstance().getGamemodes()) {
            if (selectedGamemodeName.equalsIgnoreCase(gamemode.getName())) {
                selectedGamemode = gamemode;
            }

        }
        fillPlayerList(selectedGamemode);

        //FILL COMBO BOXES WITH VALUES OF SELECTED GAMEMODE
        if (selectedGamemode != null) {
            chbAutoDeal.setSelected(selectedGamemode.isAutoDeal());
            cbAutoDealValue.setValue(selectedGamemode.getAutoDealValue());
            cbPlayerQuantity.setValue(selectedGamemode.getPlayerQuantity());
            cbCardQuantity.setValue(selectedGamemode.getCardQuantity());
        }
    }

    @FXML
    public void onAddGamemode() {
        String gamemodeName = tfGamemodeName.getText();
        boolean isAutoDeal = chbAutoDeal.isSelected();
        Integer autoDealValue = (isAutoDeal) ? Integer.parseInt(cbAutoDealValue.getValue().toString()) : null;
        Integer playerQuantity = (cbPlayerQuantity.getSelectionModel().isEmpty()) ? null : Integer.parseInt(cbPlayerQuantity.getValue().toString());
        Integer cardQuantity = (cbCardQuantity.getSelectionModel().isEmpty()) ? null : Integer.parseInt(cbCardQuantity.getValue().toString());

        if (gamemodeName.isEmpty() || playerQuantity != null || cardQuantity != null) {
            AlertService.showErrorMessage("Error", "Wrong values for new Gamemode");
        }

        boolean existing = false;

        for (GamemodeModel gamemode : Config.getInstance().getGamemodes()) {
            if (gamemodeName.equalsIgnoreCase(gamemode.getName())) {
                existing = true;
            }
        }
        
        //TODO

    }

    @FXML
    public void onEditGamemode() {
        
    }
    @FXML
    public void onAutoDealChanged() {
        if (chbAutoDeal.isSelected()) {
            cbAutoDealValue.setDisable(false);
        } else {
            cbAutoDealValue.setDisable(true);
        }
    }

    

    private void clearGamemodeCache() {
        tfGamemodeName.setText("");
        chbAutoDeal.setSelected(false);
        cbAutoDealValue.getSelectionModel().clearSelection();
        cbCardQuantity.getSelectionModel().clearSelection();
        cbPlayerQuantity.getSelectionModel().clearSelection();
    }
    
     public void contentInvisibility(boolean value) {
       tfGamemodeName.setDisable(value);
        chbAutoDeal.setDisable(value);
        cbAutoDealValue.setDisable(value);
        cbCardQuantity.setDisable(value);
        cbPlayerQuantity.setDisable(value);
    }
     
    
    @FXML
    public void onSave() {
        MainController.getInstance().removeStackDialog(rootBorderPane);

    }
}
