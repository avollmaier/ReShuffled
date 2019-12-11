/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import data.config.data.GamemodeModel;
import data.game.GameModel;
import data.game.PlayerModel;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author alois
 */
public class HomeController implements Initializable {

    @FXML
    Label tfVersion;
    @FXML
    JFXButton btShuffle;
    @FXML
    JFXButton btShutdown;
    @FXML
    JFXButton btDeal1;
    @FXML
    JFXButton btDeal2;
    @FXML
    JFXButton btDeal3;
    @FXML
    JFXButton btDeal4;
    @FXML
    JFXSlider slDealQuantity;
    @FXML
    JFXButton btDealX;
    @FXML
    BarChart bcPlayerInfo;
    @FXML
    StackPane rootStackPane;
    @FXML
    private static HomeController instance;

    public HomeController() {
        instance = this;
    }

    public static HomeController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tfVersion.setText("ReShuffled Version " + main.Main.VERSION);
//         GameModel.createInstance(Arrays.asList(new PlayerModel("tester", 1), new PlayerModel("tester2", 18), new PlayerModel("tester3", 12)), true, new GamemodeModel("test", true, 1, 1, 1));
//        updateBarChart();
       
        
    }

    @FXML
    public void onShutdown() {
        MainController.getInstance().loadShutdownDialog();
    }

    @FXML
    public void onShuffle() {
        MainController.getInstance().loadShuffleDialog();
    }

    private void updateBarChart() {

        CategoryAxis xAxis = new CategoryAxis();
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Player Name");

        bcPlayerInfo.setLegendVisible(false);

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        GameModel.getInstance().getPlayers().forEach((player) -> {
            dataSeries.getData().add(new XYChart.Data<>(player.getName(), player.getCurrentValue()));
        });

        bcPlayerInfo.getData().add(dataSeries);
        
        

    }

    public void contentInvisibility(boolean value) {
        btDeal1.setDisable(value);
        btDeal2.setDisable(value);
        btDeal3.setDisable(value);
        btDeal4.setDisable(value);
        btDealX.setDisable(value);
        slDealQuantity.setDisable(value);
        bcPlayerInfo.setDisable(value);
    }

}
