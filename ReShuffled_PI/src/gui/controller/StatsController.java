/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import data.game.Game;
import data.model.GameModel;
import data.model.PlayerModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author alois
 */


public class StatsController implements Initializable {

    @FXML
    JFXTreeTableView<PlayerModel> tbStatistics; 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXTreeTableColumn<PlayerModel, String> playerName = new JFXTreeTableColumn<>("Player Name");
    playerName.setPrefWidth(150);
    playerName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PlayerModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call (TreeTableColumn.CellDataFeatures<PlayerModel, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getName());
            }
    });
        
    
      JFXTreeTableColumn<PlayerModel, String> playerPoints = new JFXTreeTableColumn<>("Player Name");
    playerName.setPrefWidth(150);
    playerName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PlayerModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call (TreeTableColumn.CellDataFeatures<PlayerModel, String> p) {
                return new SimpleStringProperty(Integer.toString(p.getValue().getValue().getPoints()));
            }
    });
       
        ObservableList<PlayerModel> players = FXCollections.observableArrayList();
        Game.getInstance().getPlayers().forEach(player -> {
        players.add(new PlayerModel(player.getName(), player.getPoints(), player.getId()));
        });
        
        
        final TreeItem<PlayerModel> root = new RecursiveTreeItem<PlayerModel>(players, RecursiveTreeObject::getChildren);
        tbStatistics.getColumns().setAll(playerName, playerPoints);
        tbStatistics.setRoot(root);
        tbStatistics.setShowRoot(false);
        
    }    

    
    
}
