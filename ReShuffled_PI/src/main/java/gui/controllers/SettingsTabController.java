package gui.controllers;

import com.jfoenix.controls.*;
import config.data.Config;
import config.service.ConfigService;
import config.data.Gamemode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import logging.Logger;
import main.Main;

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
    private Gamemode editGamemode, actualGamemode, selectedGamemode;
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
        if(!Main.config.getGamemodes().isEmpty())
        {
            Main.config.getGamemodes().forEach(gamemode -> {
                listViewData.add(gamemode.getName());
            });
            listView.setItems(listViewData);
        }
    }

    public void onListSelectionChange() {
        if(!Main.config.getGamemodes().isEmpty()) {
            Main.config.getGamemodes().forEach(gamemode -> {
                if (gamemode.getName().equals(listView.getSelectionModel().getSelectedItem()))
                    selectedGamemode = gamemode;
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

        Main.config.getGamemodes().add(new Gamemode(tfName.getText(), chbAutoDeal.isSelected(), cbAutoDealValue.getValue(), cbCardQuantity.getValue(), cbPlayerQuantity.getValue()));
        refreshListView();
        btAddGM.setDisable(true);

        ConfigService configService =new ConfigService();
        configService.serializeService(Main.config.getLogPath(),Main.config);
    }

    public void onEditGM() {
        editMode = true;
        editGamemode=selectedGamemode;
        updateButtons();

        tfName.setText(selectedGamemode.getName());
        cbCardQuantity.setValue(selectedGamemode.getCardQuantity());
        cbAutoDealValue.setValue(selectedGamemode.getAutoDealValue());
        cbPlayerQuantity.setValue(selectedGamemode.getCardQuantity());
    }

    public void onSaveGM() {
        if(!Main.config.getGamemodes().isEmpty()) {
            Main.config.getGamemodes().forEach((gamemode) -> {
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
        editMode=false;
        ConfigService configService=new ConfigService();
        configService.serializeService(Main.CONFIGPATH, Main.config);
        refreshListView();
    }

    public void onAutoDealChanged() {
        if (chbAutoDeal.isSelected()) cbAutoDealValue.setDisable(false);
        else cbAutoDealValue.setDisable(true);

    }

    public void onNameChange() {

        if(!Main.config.getGamemodes().isEmpty()) {
            Main.config.getGamemodes().forEach((gamemode) -> {

                if(editMode){
                    if(!gamemode.getName().equals(editGamemode.getName())) {
                        if (gamemode.getName().equals(tfName.getText())) {
                            btSaveGM.setDisable(true);
                        } else {
                            btSaveGM.setDisable(false);
                        }
                    }
                }
                else {
                    if(gamemode.getName().equals(tfName.getText())) {
                        btAddGM.setDisable(true);
                    }else {
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
