/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import data.game.Game;
import data.model.PlayerModel;
import gui.multilanguage.ResourceKeyEnum;
import gui.multilanguage.ResourceManager;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import serial.Communication;
import serial.request.RequestDeal;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author alois
 */
public class HomeController implements Initializable {

    @FXML


    private static HomeController instance;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final XYChart.Series<String, Number> dataSeries = new XYChart
            .Series<String, Number>();
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
    final ChangeListener<Number> cardListener = (observableValue, oldValue, newValue) -> {
        if (newValue.intValue() == 1) {
            btDealX.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_deal) + " " + newValue.intValue());
        } else {
            btDealX.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_deal) + " " + newValue.intValue());
        }
    };
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
    JFXSlider slAddPoints;

    // *************************************************************************
    @FXML
    BarChart bcPlayerInfo;
    @FXML
    StackPane rootStackPane;
    private int playerId = 0;
    private int cardQuantity = Game.getInstance().getGamemode().getCardQuantity();

    public HomeController() {
        instance = this;
    }

    public static HomeController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle bundle) {
        //DEFINE ACTIONS
        slDealQuantity.valueProperty().addListener(cardListener);
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

        tfVersion.setText("ReShuffled Version " + main.Main.VERSION);
        tfGamemode.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_gamemode) + ": " + Game.getInstance().getGamemode().getName());
        tfPlayerQuantity.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_player) + ": " + Game.getInstance().getGamemode().getPlayerQuantity().toString());

        btDeal1.setText(btDeal1.getText() + " " + 1);
        btDeal2.setText(btDeal2.getText() + " " + 2);
        btDeal3.setText(btDeal3.getText() + " " + 3);
        btDeal4.setText(btDeal4.getText() + " " + 4);

        initSliderSettings(slAddPoints, 1, 20, 1);
        initSliderSettings(slDealQuantity, 0, cardQuantity, 1);

        btDealX.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_deal) + " 0");
        initBarChart();
        lbPlayerName.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_player) + " " + getIdPlayer().getName());

        //*********************************************************************
    }


    private void handleNextPlayer(final ActionEvent event) {
        if (playerId == Game.getInstance().getPlayers().size() - 1) {
            playerId = 0;
        } else {
            playerId++;
        }

        lbPlayerName.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_player) + " " + getIdPlayer().getName());
    }


    private void handlePreviousPlayer(final ActionEvent event) {
        if (playerId == 0) {
            playerId = Game.getInstance().getPlayers().size() - 1;
        } else {
            playerId--;
        }

        lbPlayerName.setText(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_player) + " " + getIdPlayer().getName());
    }


    private PlayerModel getIdPlayer() {
        return Game.getInstance().getPlayers().get(playerId);
    }


    private void handleAddPoint(final ActionEvent event) {
        getIdPlayer().increment((int) slAddPoints.getValue());
        updateBarChart();
    }


    private void handleDeletePoint(final ActionEvent event) {
        if (getIdPlayer().getPoints() > 0) {
            getIdPlayer().decrement(1);
        }
        updateBarChart();
    }


    private void handleShutdown(final ActionEvent event) {
        MainController.getInstance().loadShutdownDialog();
    }


    private void handleShuffle(final ActionEvent event) {
        MainController.getInstance().loadShuffleDialog();
        cardQuantity = Game.getInstance().getGamemode().getCardQuantity();

    }


    private void handleGameFinished(final ActionEvent event) {
        MainController.getInstance().loadGameFinishedDialog();
    }


    private void handleDeal1(final ActionEvent event) {
        RequestDeal deal1 = new RequestDeal(1);
        Communication.getInstance().sendRequestExecutor(deal1);
        cardQuantity -= 1;
        handleCardChanged();
    }


    private void handleDeal2(final ActionEvent event) {
        RequestDeal deal2 = new RequestDeal(2);
        Communication.getInstance().sendRequestExecutor(deal2);
        cardQuantity -= 2;
        handleCardChanged();
    }


    private void handleDeal3(final ActionEvent event) {
        RequestDeal deal3 = new RequestDeal(3);
        Communication.getInstance().sendRequestExecutor(deal3);
        cardQuantity -= 3;
        handleCardChanged();
    }


    private void handleDeal4(final ActionEvent event) {
        RequestDeal deal4 = new RequestDeal(4);
        Communication.getInstance().sendRequestExecutor(deal4);
        cardQuantity -= 3;
        handleCardChanged();
    }


    private void handleDealX(final ActionEvent event) {
        RequestDeal deal = new RequestDeal((int) slDealQuantity.getValue());
        Communication.getInstance().sendRequestExecutor(deal);
        cardQuantity -= (int) slDealQuantity.getValue();
        handleCardChanged();
    }

    private void initSliderSettings(JFXSlider slider, int minValue, int maxValue, int presetValue) {
        slider.setMin(minValue);
        slider.setMax(maxValue);
        slider.setValue(presetValue);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(3);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
    }


    public void handleCardChanged() {
        if (cardQuantity < 1) {
            btDeal1.setDisable(true);
        } else {
            btDeal1.setDisable(false);
        }
        if (cardQuantity < 2) {
            btDeal2.setDisable(true);
        } else {
            btDeal2.setDisable(false);
        }
        if (cardQuantity < 3) {
            btDeal3.setDisable(true);
        } else {
            btDeal3.setDisable(false);
        }
        if (cardQuantity < 4) {
            btDeal4.setDisable(true);
        } else {
            btDeal4.setDisable(false);
        }

        slDealQuantity.setMax(cardQuantity);
    }


    private void initBarChart() {

        dataSeries.getData().clear();
        yAxis.setLabel(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_home_player));
        bcPlayerInfo.setLegendVisible(false);
        Game.getInstance().getPlayers().forEach((player) -> {
            dataSeries.getData().add(new XYChart.Data<>(player.getName(), player.getPoints()));
        });

        bcPlayerInfo.getData().add(dataSeries);
    }


    private void updateBarChart() {

        dataSeries.getData().clear();
        Game.getInstance().getPlayers().forEach((player) -> {
            dataSeries.getData().add(new XYChart.Data<>(player.getName(), player.getPoints()));
        });
    }


    public void contentInvisibility(boolean value) {
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
