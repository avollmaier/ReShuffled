/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.*;
import data.config.Config;
import data.game.Game;
import data.model.GamemodeModel;
import data.model.PlayerModel;
import gui.multilanguage.ResourceKeyEnum;
import gui.multilanguage.ResourceManager;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;
import util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * @author alois
 */
public class StartupController implements Initializable {

    //Listeners
    private static final Logger LOG = Logger.getLogger(StartupController.class.getName());
    @FXML
    StackPane rootStackPane;
    @FXML
    BorderPane rootBorderPane;
    @FXML
    JFXListView playerNameList;
    private final ChangeListener<String> nameChangeListener = (observableValue, oldValue, newValue) -> {
        notifyList(newValue);
    };
    private final ChangeListener<PlayerModel> playerListener = (observableValue, oldValue, newValue) -> {
        tfPlayerName.setText(newValue.getName());
    };
    @FXML
    JFXComboBox cbAutoDealValue;
    @FXML
    JFXCheckBox chbAutoDeal;
    @FXML
    JFXComboBox cbGamemodes;
    @FXML
    JFXTextField tfGamemodeName;
    @FXML
    JFXTextField tfPlayerName;
    @FXML
    JFXComboBox cbCardQuantity;
    @FXML
    JFXComboBox cbPlayerQuantity;
    @FXML
    JFXButton btSaveChanges;
    @FXML
    JFXButton btDelete;
    @FXML
    JFXButton btStartGame;
    @FXML
    JFXButton btSaveName;
    GamemodeModel selectedGamemode = null;
    final ChangeListener<GamemodeModel> gamemodeListener = (observableValue, oldValue, newValue) -> {
        handleGamemodeChange(newValue);
    };

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        tfPlayerName.textProperty().addListener(nameChangeListener);
        playerNameList.getSelectionModel().selectedItemProperty().addListener(playerListener);
        cbGamemodes.valueProperty().addListener(gamemodeListener);
        chbAutoDeal.setOnAction(this::handleAutoDealChange);
        btDelete.setOnAction(this::handleDelete);
        btSaveChanges.setOnAction(this::handleSaveChanges);
        btStartGame.setOnAction(this::handleStartGame);

        cbGamemodes.setCellFactory(list -> new GamemodeCell());
        cbGamemodes.setButtonCell(new GamemodeCell());

        playerNameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        playerNameList.setCellFactory(list -> new PlayerCell());
        Game.clearInstance();
        updateGameComboBox();
        initControlComboBox();

    }

    private void updatePlayerList() {

        final ObservableList<PlayerModel> players = FXCollections.observableArrayList();
        players.clear();
        for (int i = 1; i <= selectedGamemode.getPlayerQuantity(); i++) {
            players.add(new PlayerModel(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_player) + " " + i, 0));
        }

        playerNameList.setItems(players);


    }

    private void notifyList(String newValue) {
        int index = playerNameList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            playerNameList.getItems().set(index, new PlayerModel(newValue, 0));
        }
    }

    private void updateGameComboBox() {
        ObservableList<GamemodeModel> gamemodes = FXCollections.observableArrayList(Config.getInstance().getGamemodes());
        cbGamemodes.getItems().clear();
        cbGamemodes.setItems(gamemodes);
    }

    //Gamemode combobox management

    private void handleGamemodeChange(GamemodeModel gamemodeModel) {
        if (!cbGamemodes.getSelectionModel().isEmpty()) {
            btDelete.setDisable(false);

            selectedGamemode = gamemodeModel;
            updateControls(selectedGamemode);
        }
    }

    private void handleAutoDealChange(final ActionEvent event) {
        if (chbAutoDeal.isSelected()) {
            cbAutoDealValue.setDisable(false);
        } else {
            cbAutoDealValue.setDisable(true);
        }
    }

    private void handleDelete(final ActionEvent event) {
        JFXButton btCancel = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_cancel));
        JFXButton btDelete = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_startup_delete));

        btDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            LOG.info("Deleted gamemode " + selectedGamemode.getName());
            Config.getInstance().getGamemodes().remove(selectedGamemode);
            Config.getInstance().save();
            clearGamemodeCache();
            updateGameComboBox();
            selectedGamemode = null;
        });
        AlertUtil.showContentDialog(rootStackPane, rootBorderPane, Arrays.asList(btCancel, btDelete), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_info), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_deleteGamemode) + " " + selectedGamemode.getName());

    }

    private void handleSaveChanges(final ActionEvent event) {
        String gamemodeName = tfGamemodeName.getText();
        boolean isAutoDeal = chbAutoDeal.isSelected();
        Integer autoDealValue = (isAutoDeal) ? Integer.parseInt(cbAutoDealValue.getValue().toString()) : null;
        Integer playerQuantity = (cbPlayerQuantity.getSelectionModel().isEmpty()) ? null : Integer.parseInt(cbPlayerQuantity.getValue().toString());
        Integer cardQuantity = (cbCardQuantity.getSelectionModel().isEmpty()) ? null : Integer.parseInt(cbCardQuantity.getValue().toString());

        JFXButton btOk = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_ok));

        if (gamemodeName.isEmpty() || playerQuantity == null || cardQuantity == null) {

            AlertUtil.showContentDialog(rootStackPane, rootBorderPane, Arrays.asList(btOk), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_error), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_startup_nullError));
        }
        if (cardQuantity < playerQuantity) {
            AlertUtil.showContentDialog(rootStackPane, rootBorderPane, Arrays.asList(btOk), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_error), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_startup_cardQuantityError));
        }

        boolean existing = false;
        GamemodeModel existingGamemode = null;

        for (GamemodeModel gamemode : Config.getInstance().getGamemodes()) {
            if (gamemodeName.equalsIgnoreCase(gamemode.getName())) {
                existing = true;
                existingGamemode = gamemode;
            }
        }

        if (existing) {
            existingGamemode.setName(gamemodeName);
            existingGamemode.setAutoDeal(isAutoDeal);

            if (isAutoDeal) {
                existingGamemode.setAutoDealValue(autoDealValue);
            }

            existingGamemode.setCardQuantity(cardQuantity);
            existingGamemode.setPlayerQuantity(playerQuantity);

            Config.getInstance().save();
            updateGameComboBox();
            LOG.info("Gamemode " + existingGamemode.getName() + " updated");

        } else {
            GamemodeModel addedGamemode = new GamemodeModel(gamemodeName, isAutoDeal, autoDealValue, cardQuantity, playerQuantity);
            Config.getInstance().getGamemodes().add(addedGamemode);
            Config.getInstance().save();
            updateGameComboBox();
            cbGamemodes.getSelectionModel().select(addedGamemode);
            LOG.info("Gamemode " + addedGamemode.getName() + " created");
        }
    }

    private void handleStartGame(final ActionEvent event) {
        if (selectedGamemode != null) {
            Game.createInstance(playerNameList.getItems(), false, selectedGamemode);

            LOG.info("Starting game with gamemode " + Game.getInstance().getGamemode().getName() + " and " + Game.getInstance().getGamemode().getPlayerQuantity() + " Players");

            closeStage();
            loadMainStage();
        } else {
            JFXButton btOk = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_ok));
            AlertUtil.showContentDialog(rootStackPane, rootBorderPane, Arrays.asList(btOk), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_info), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_gamemodeCheck));
        }

    }

    private void initControlComboBox() {
        //FILL PLAYER, CARD AND AUTODEAL QUANTITY COMBO BOX
        ObservableList<Integer> intValues = FXCollections.observableArrayList();
        for (int i = 1; i <= 30; i++) {
            intValues.add(i);
        }
        cbAutoDealValue.setItems(intValues);
        cbCardQuantity.setItems(intValues);
        cbPlayerQuantity.setItems(intValues);
    }

    private void updateControls(GamemodeModel selectedGamemode) {
        updatePlayerList();
        //Update other Controls
        tfGamemodeName.setText(selectedGamemode.getName());
        chbAutoDeal.setSelected(selectedGamemode.isAutoDeal());
        cbAutoDealValue.setValue(selectedGamemode.getAutoDealValue());
        cbPlayerQuantity.setValue(selectedGamemode.getPlayerQuantity());
        cbCardQuantity.setValue(selectedGamemode.getCardQuantity());

    }

    private void clearGamemodeCache() {
        tfGamemodeName.setText("");
        chbAutoDeal.setSelected(false);
        cbAutoDealValue.getSelectionModel().clearSelection();
        cbCardQuantity.getSelectionModel().clearSelection();
        cbPlayerQuantity.getSelectionModel().clearSelection();
        playerNameList.getItems().clear();
    }

    public void contentInvisibility(boolean value) {
        tfGamemodeName.setDisable(value);
        chbAutoDeal.setDisable(value);
        cbAutoDealValue.setDisable(value);
        cbCardQuantity.setDisable(value);
        cbPlayerQuantity.setDisable(value);
        btSaveChanges.setDisable(value);
    }

    private void closeStage() {
        ((Stage) rootStackPane.getScene().getWindow()).close();
    }

    private void loadMainStage() {
        try {
            final URL fxmlUrl = getClass().getResource("/gui/fxml/main.fxml");
            final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl, ResourceManager.getInstance().getCurrentRecourceBundle());
            fxmlLoader.setController(new MainController());
            final Parent root = fxmlLoader.load();
            final Stage stage = new Stage();
            stage.setScene(new Scene(root, Config.getInstance().getGuiWidth(), Config.getInstance().getGuiHeight()));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ReShuffled Main GUI");
            stage.show();


            LOG.info(stage.getTitle() + " started successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            LOG.severe("Exception while running GUI" + ex);
        }
    }

    //Player list service
    private class PlayerCell extends ListCell<PlayerModel> {
        @Override
        protected void updateItem(PlayerModel item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null || !empty) {
                Label label = new Label(item.getName());
                setGraphic(label);
            } else {
                setGraphic(null);
            }
        }
    }

    private class GamemodeCell extends ListCell<GamemodeModel> {

        @Override
        protected void updateItem(GamemodeModel item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null || !empty) {
                Label label = new Label(item.getName());
                setGraphic(label);
            } else {
                setGraphic(null);
            }
        }
    }
}
