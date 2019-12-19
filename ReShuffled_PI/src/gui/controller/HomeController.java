/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import data.game.Game;
import data.model.PlayerModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import serial.Communication;
import serial.request.RequestDeal;
import serial.request.RequestShutdown;


/**
 * FXML Controller class
 *
 * @author alois
 */
public class HomeController implements Initializable {

    @FXML
    Label tfVersion;
    @FXML
    Label tfGamemode;
    @FXML
    Label tfPlayerQuantity;
    @FXML
    Label lbPlayerName;
    @FXML
    JFXButton btShuffle;
    @FXML
    JFXButton btShutdown;
    @FXML
    JFXButton handleGameFinished;
    @FXML
    JFXButton btDeal1;
    @FXML
    JFXButton btDeal2;
    @FXML
    JFXButton btDeal3;
    @FXML
    JFXButton btDeal4;
    @FXML
    JFXButton btDealX;
    @FXML
    JFXButton btNextPlayer;
    @FXML
    JFXButton btPreviousPlayer;
    @FXML
    JFXButton btAddPoint;
    @FXML
    JFXButton btRemovePoint;
    @FXML
    JFXButton btGameFinished;
    @FXML
    JFXSlider slDealQuantity;
    @FXML
    BarChart bcPlayerInfo;
    @FXML
    StackPane rootStackPane;
    @FXML


    private static HomeController instance;


    public HomeController () {
        instance = this;
    }


    public static HomeController getInstance () {
        return instance;
    }

    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
    private XYChart.Series<String, Number> dataSeries = new XYChart.Series<String, Number>();
    private int playerId = 0;
    private int cardQuantity = Game.getInstance().getGamemode().getCardQuantity();

    //LISTENERS
    final ChangeListener<Number> changeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.intValue() == 1) {
            btDealX.setText("Deal " + newValue.intValue() + " card");
        }
        else {
            btDealX.setText("Deal " + newValue.intValue() + " cards");
        }
    };

    // *************************************************************************

    @Override
    public void initialize (URL arg0, ResourceBundle arg1
    ) {

        //DEFINE ACTIONS
        slDealQuantity.valueProperty().addListener(changeListener);
        btShutdown.setOnAction(this::handleShutdown);
        btShuffle.setOnAction(this::handleShuffle);
        btGameFinished.setOnAction(this::handleGameFinished);
        btDeal1.setOnAction(this::handleDeal1);
        btDeal2.setOnAction(this::handleDeal2);
        btDeal3.setOnAction(this::handleDeal3);
        btDeal4.setOnAction(this::handleDeal4);
        btDealX.setOnAction(this::handleDealX);
        btNextPlayer.setOnAction(this::handleNextPlayer);
        btPreviousPlayer.setOnAction(this::handlePreviousPlayer);
        btAddPoint.setOnAction(this::handleAddPoint);
        btRemovePoint.setOnAction(this::handleDeletePoint);
        //*********************************************************************
        //INIT ELEMENTS
        tfVersion.setText("ReShuffled Version " + main.Main.VERSION);
        tfGamemode.setText("Gamemode: " + Game.getInstance().getGamemode().getName());
        tfPlayerQuantity.setText("Players: " + Game.getInstance().getGamemode().getPlayerQuantity().toString());
        slDealQuantity.setMin(0);
        slDealQuantity.setMax(cardQuantity);
        btDealX.setText("Deal 0 cards");
        initBarChart();
        lbPlayerName.setText(getIdPlayer().getName());

        //*********************************************************************
    }


    private void handleNextPlayer (final ActionEvent event) {
        if (playerId == Game.getInstance().getPlayers().size() - 1) {
            playerId = 0;
        }
        else {
            playerId++;
        }

        lbPlayerName.setText(getIdPlayer().getName());
    }


    private void handlePreviousPlayer (final ActionEvent event) {
        if (playerId == 0) {
            playerId = Game.getInstance().getPlayers().size() - 1;
        }
        else {
            playerId--;
        }

        lbPlayerName.setText(getIdPlayer().getName());
    }


    private PlayerModel getIdPlayer () {
        return Game.getInstance().getPlayers().get(playerId);
    }


    private void handleAddPoint (final ActionEvent event) {
        getIdPlayer().increment(1);
        updateBarChart();
    }


    private void handleDeletePoint (final ActionEvent event) {
        if (getIdPlayer().getPoints() > 0) {
            getIdPlayer().decrement(1);
        }
        updateBarChart();
    }


    private void handleShutdown (final ActionEvent event) {
        MainController.getInstance().loadShutdownDialog();
        //TODO
    }


    private void handleShuffle (final ActionEvent event) {
        MainController.getInstance().loadShuffleDialog();
        cardQuantity = Game.getInstance().getGamemode().getCardQuantity();
    }


    private void handleGameFinished (final ActionEvent event) {
        MainController.getInstance().loadGameFinishedDialog();
    }


    private void handleDeal1 (final ActionEvent event) {
        RequestDeal deal1 = new RequestDeal(1);
        Communication.getInstance().sendRequestExecutor(deal1);
        cardQuantity -= 1;
        handleCardChanged();
    }


    private void handleDeal2 (final ActionEvent event) {
        RequestDeal deal2 = new RequestDeal(2);
        Communication.getInstance().sendRequestExecutor(deal2);
        cardQuantity -= 2;
        handleCardChanged();
    }


    private void handleDeal3 (final ActionEvent event) {
        RequestDeal deal3 = new RequestDeal(3);
        Communication.getInstance().sendRequestExecutor(deal3);
        cardQuantity -= 3;
        handleCardChanged();
    }


    private void handleDeal4 (final ActionEvent event) {
        RequestDeal deal4 = new RequestDeal(4);
        Communication.getInstance().sendRequestExecutor(deal4);
        cardQuantity -= 3;
        handleCardChanged();
    }


    private void handleDealX (final ActionEvent event) {
        RequestDeal deal = new RequestDeal((int) slDealQuantity.getValue());
        Communication.getInstance().sendRequestExecutor(deal);
        cardQuantity -= (int) slDealQuantity.getValue();
        handleCardChanged();
    }

    //****************************
    //HELPER METHODS
    //****************************

    private void handleCardChanged () {
        System.out.println(cardQuantity);
        if (cardQuantity < 1) {
            btDeal1.setDisable(true);
        }
        else {
            btDeal1.setDisable(false);
        }
        if (cardQuantity < 2) {
            btDeal2.setDisable(true);
        }
        else {
            btDeal2.setDisable(false);
        }
        if (cardQuantity < 3) {
            btDeal3.setDisable(true);
        }
        else {
            btDeal3.setDisable(false);
        }
        if (cardQuantity < 4) {
            btDeal4.setDisable(true);
        }
        else {
            btDeal4.setDisable(false);
        }

        slDealQuantity.setMax(cardQuantity);
    }


    private void initBarChart () {
      
        dataSeries.getData().clear();
        yAxis.setLabel("Player Name");
        bcPlayerInfo.setLegendVisible(false);
        Game.getInstance().getPlayers().forEach((player) -> {
            dataSeries.getData().add(new XYChart.Data<>(player.getName(), player.getPoints()));
        });

        bcPlayerInfo.getData().add(dataSeries);
    }
     private void updateBarChart () {
      
        dataSeries.getData().clear();
        Game.getInstance().getPlayers().forEach((player) -> {
            dataSeries.getData().add(new XYChart.Data<>(player.getName(), player.getPoints()));
        });
    }


    public void contentInvisibility (boolean value) {
        btDeal1.setDisable(value);
        btDeal2.setDisable(value);
        btDeal3.setDisable(value);
        btDeal4.setDisable(value);
        btDealX.setDisable(value);
        slDealQuantity.setDisable(value);
        bcPlayerInfo.setDisable(value);
        btNextPlayer.setDisable(value);
        btPreviousPlayer.setDisable(value);
        btAddPoint.setDisable(value);
        btRemovePoint.setDisable(value);
        btGameFinished.setDisable(value);
    }

}
