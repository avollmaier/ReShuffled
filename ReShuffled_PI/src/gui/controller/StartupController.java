/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import data.model.GamemodeModel;
import data.config.Config;
import data.model.PlayerModel;
import gui.util.AlertService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;
import data.game.Game;
import java.awt.event.ActionListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

/**
 *
 * @author alois
 */
public class StartupController implements Initializable {

    @FXML
    StackPane rootStackPane;
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
    
    //LISTENERS
    private static final Logger LOG = Logger.getLogger(StartupController.class.getName());     
    final ChangeListener<String> gamemodeListener = (observableValue, oldValue, newValue) -> {
        handleGamemodeChange();
    };
    
    final ChangeListener<String> seleltionListener = (observableValue, oldValue, newValue) -> {
        tfPlayerName.setText(newValue);
    };
    
    final ChangeListener<String> nameChangeListener = (observableValue, oldValue, newValue) -> {
        notifyList(newValue);
    };
    
  
    

    
 
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        //DEFINE ACTIONS
        tfPlayerName.textProperty().addListener(nameChangeListener);
        playerNameList.getSelectionModel().selectedItemProperty().addListener(seleltionListener);
        cbGamemodes.valueProperty().addListener(gamemodeListener);
        chbAutoDeal.setOnAction(this::handleAutoDealChange);
        btDelete.setOnAction(this::handleDelete);
        btSaveChanges.setOnAction(this::handleSaveChanges);
        btStartGame.setOnAction(this::handleStartGame);
        //********************************************************************* 
        //INIT ELEMENTS
        Game.clearInstance();
        updateGameComboBox();
        initControlComboBox();
        
       
        //*********************************************************************
        
    }
    private void notifyList(String newValue){
        int index=playerNameList.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index>=0)
        {
          playerNameList.getItems().set(index, newValue);
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
        JFXButton btCancel = new JFXButton("Cancel");
        JFXButton btDelete = new JFXButton("Delete!");

        btDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            LOG.info("Deleted gamemode " + selectedGamemode.getName());
            Config.getInstance().getGamemodes().remove(selectedGamemode);
            Config.getInstance().save();
            clearGamemodeCache();
            updateGameComboBox();
            selectedGamemode = null;
        });
        AlertService.showContentDialog(rootStackPane, rootBorderPane, Arrays.asList(btCancel, btDelete), "General Info Message", "Do you really want to delete the selected gamemode " + selectedGamemode.getName());

    }

    private void handleSaveChanges(final ActionEvent event) {
        try {
            String gamemodeName = tfGamemodeName.getText();
            boolean isAutoDeal = chbAutoDeal.isSelected();
            Integer autoDealValue = (isAutoDeal) ? Integer.parseInt(cbAutoDealValue.getValue().toString()) : null;
            Integer playerQuantity = (cbPlayerQuantity.getSelectionModel().isEmpty()) ? null : Integer.parseInt(cbPlayerQuantity.getValue().toString());
            Integer cardQuantity = (cbCardQuantity.getSelectionModel().isEmpty()) ? null : Integer.parseInt(cbCardQuantity.getValue().toString());

            if (gamemodeName.isEmpty() || playerQuantity == null || cardQuantity == null) {
               throw new Exception("Gamemode is empty or required valued are null");
            }
            if (cardQuantity<playerQuantity)
                throw new Exception("Card quantity cant be bigger than player quantity");

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
                LOG.info("Gamemode " + existingGamemode.getName() + "updated");

            } else {
                GamemodeModel addedGamemode = new GamemodeModel(gamemodeName, isAutoDeal, autoDealValue, cardQuantity, playerQuantity);
                Config.getInstance().getGamemodes().add(addedGamemode);
                Config.getInstance().save();
                updateGameComboBox();
                cbGamemodes.getSelectionModel().select(addedGamemode.getName());
                LOG.info("Gamemode " + addedGamemode.getName() + "updated");
            }

        } catch (Exception ex) {
            AlertService.showErrorMessage("Error", "Error " + ex);
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleNameChange(final ActionEvent event) {

    }

    @FXML
    private void handleStartGame(final ActionEvent event) {
        if (selectedGamemode != null) {

            List<PlayerModel> players = new ArrayList<>();
            for (int i = 0; i <= selectedGamemode.getPlayerQuantity() - 1; i++) {
                players.add(new PlayerModel(playerNameList.getItems().get(i).toString(), 0, players.size()));
            }
            Game.createInstance(players, false, selectedGamemode);

            LOG.info("Starting game with gamemode " + Game.getInstance().getGamemode().getName() + " and " + Game.getInstance().getGamemode().getPlayerQuantity() + " Players");
            
            closeStage();
            loadMainStage();
        } else {
            AlertService.showSimpleAlert("INFO", "Make sure that a gamemode is selected");
        }

    }
    
    //****************************
    //HELPER METHODS
    //****************************
    
    private void handleGamemodeChange() {
        if (!cbGamemodes.getSelectionModel().isEmpty()) {
            btDelete.setDisable(false);
            String selectedGamemodeName = cbGamemodes.getValue().toString();

            //Find selectedGamemode
            for (GamemodeModel gamemode : Config.getInstance().getGamemodes()) {
                if (selectedGamemodeName.equalsIgnoreCase(gamemode.getName())) {
                    selectedGamemode = gamemode;
                }
            }
            updateControls(selectedGamemode);
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

    private void updateGameComboBox() {
        ObservableList<String> gamemodeNames = FXCollections.observableArrayList();
        for (GamemodeModel gamemode : Config.getInstance().getGamemodes()) {
            gamemodeNames.add(gamemode.getName());
        }
        cbGamemodes.setItems(gamemodeNames);
    }

    private void updateControls(GamemodeModel selectedGamemode) {
        //Update Player List
        final ObservableList<String> playerNames = FXCollections.observableArrayList();

        for (int i = 1; i <= selectedGamemode.getPlayerQuantity(); i++) {
            playerNames.add("Player " + i);
        }

        playerNameList.setItems(playerNames);
        playerNameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
                final FXMLLoader fxmlLoader= new FXMLLoader(fxmlUrl);
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
}
