package gui.controllers;

import main.Main;
import com.jfoenix.controls.*;
import data.config.data.GamemodeModel;
import data.config.service.Config;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import logging.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsTabController implements Initializable {

    @FXML
    private JFXListView<String> listView;
    @FXML
    private JFXButton btSaveGM;
    @FXML
    private JFXButton btAddGM;
    @FXML
    private JFXButton btEditGM;
    @FXML
    private JFXButton btSetGM;
    @FXML
    private JFXTextField tfName;
    @FXML
    private JFXComboBox<Integer> cbAutoDealValue;
    @FXML
    private JFXComboBox<Integer> cbCardQuantity;
    @FXML
    private JFXComboBox<Integer> cbPlayerQuantity;
    @FXML
    private JFXCheckBox chbAutoDeal;

    @FXML
    private MainController mainController;

    private static final Logger LOG = Logger.getLogger(SettingsTabController.class.getName());
    private GamemodeModel editGamemode, actualGamemode, selectedGamemode;
    private boolean editMode = false;

    ObservableList<String> listViewData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshListView();
        for (int i = 1; i <= 64; i++) {
            cbPlayerQuantity.getItems().add(i);
            cbAutoDealValue.getItems().add(i);
            cbCardQuantity.getItems().add(i);
        }
    }

    public void refreshListView() {
        listViewData.clear();
        if (!Config.getInstance().getGamemodes().isEmpty()) {
            Config.getInstance().getGamemodes().forEach(gamemode -> {
                listViewData.add(gamemode.getName());
            });
            listView.setItems(listViewData);
        }
    }

    public void onListSelectionChange() {
        if (!Config.getInstance().getGamemodes().isEmpty()) {
            Config.getInstance().getGamemodes().forEach(gamemode -> {
                if (gamemode.getName().equals(listView.getSelectionModel().getSelectedItem())) {
                    selectedGamemode = gamemode;
                }
            });
        }
        if (actualGamemode != null) {
            if (selectedGamemode.getName().equals(actualGamemode.getName())) {
                btEditGM.setDisable(true);
                btSetGM.setDisable(true);
            } else {
                btEditGM.setDisable(false);
                btSetGM.setDisable(false);
            }
        } else {
            btEditGM.setDisable(false);
            btSetGM.setDisable(false);
        }

        tfName.clear();
        cbPlayerQuantity.getItems().clear();
        cbCardQuantity.getItems().clear();
        cbAutoDealValue.getItems().clear();
        chbAutoDeal.setSelected(false);
        btSaveGM.setDisable(true);
        btAddGM.setDisable(false);
    }

    public void onSetGM() {
        actualGamemode = selectedGamemode;
        btEditGM.setDisable(true);
        btSetGM.setDisable(true);

        //TODO SERIAL COMM & RESET GAME DATA
    }

    public void onAddGM() {

        Config.getInstance().getGamemodes().add(new GamemodeModel(tfName.getText(), chbAutoDeal.isSelected(), cbAutoDealValue.getValue(), cbCardQuantity.getValue(), cbPlayerQuantity.getValue()));
        refreshListView();
        btAddGM.setDisable(true);
        Config.getInstance().save();
    }

    public void onEditGM() {
        editMode = true;
        editGamemode = selectedGamemode;
        updateButtons();

        tfName.setText(selectedGamemode.getName());
        cbCardQuantity.setValue(selectedGamemode.getCardQuantity());
        cbAutoDealValue.setValue(selectedGamemode.getAutoDealValue());
        cbPlayerQuantity.setValue(selectedGamemode.getCardQuantity());
    }

    public void onSaveGM() {
        if (!Config.getInstance().getGamemodes().isEmpty()) {
            Config.getInstance().getGamemodes().forEach((gamemode) -> {
                if (gamemode.getName().equals(selectedGamemode.getName())) {
                    if (chbAutoDeal.isSelected()) {
                        gamemode.setAutoDeal(true);
                        gamemode.setAutoDealValue(cbAutoDealValue.getValue());
                    } else {
                        gamemode.setAutoDeal(false);
                        gamemode.setAutoDealValue(null);
                        cbAutoDealValue.getItems().clear();
                    }
                    gamemode.setName(tfName.getText());
                    gamemode.setPlayerQuantity(cbPlayerQuantity.getValue());
                    gamemode.setCardQuantity(cbCardQuantity.getValue());
                }
            });
        }
        editMode = false;
        Config.getInstance().save();
        refreshListView();
    }

    public void onAutoDealChanged() {
        if (chbAutoDeal.isSelected()) {
            cbAutoDealValue.setDisable(false);
        } else {
            cbAutoDealValue.setDisable(true);
        }

    }

    public void onNameChange() {

        if (!Config.getInstance().getGamemodes().isEmpty()) {
            Config.getInstance().getGamemodes().forEach((gamemode) -> {

                if (editMode) {
                    if (!gamemode.getName().equals(editGamemode.getName())) {
                        if (gamemode.getName().equals(tfName.getText())) {
                            btSaveGM.setDisable(true);
                        } else {
                            btSaveGM.setDisable(false);
                        }
                    }
                } else {
                    if (gamemode.getName().equals(tfName.getText())) {
                        btAddGM.setDisable(true);
                    } else {
                        btAddGM.setDisable(false);
                    }
                }
            });
        }
    }

    public void updateButtons() {
        if (editMode) {
            btSetGM.setDisable(true);
            btAddGM.setDisable(true);
            btSaveGM.setDisable(false);
            btEditGM.setDisable(true);

            if (selectedGamemode.isAutoDeal()) {
                chbAutoDeal.setSelected(true);
                cbAutoDealValue.setDisable(false);
            } else {
                chbAutoDeal.setSelected(false);
                cbAutoDealValue.setDisable(true);
            }
        }
        if (!editMode) {
            btSetGM.setDisable(false);
            btAddGM.setDisable(false);
            btSaveGM.setDisable(true);
            btEditGM.setDisable(false);
        }
    }
}
