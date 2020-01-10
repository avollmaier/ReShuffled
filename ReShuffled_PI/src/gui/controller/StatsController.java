/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXTreeTableView;
import data.game.Game;
import data.model.GameModel;
import data.model.GamemodeModel;
import data.model.PlayerModel;
import data.model.StatisticsModel;
import data.statistics.Statistics;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author alois
 */

public class StatsController implements Initializable {

    @FXML
    JFXTreeTableView tbStatistics;


    @Override
    public void initialize (URL url, ResourceBundle rb) {
        final TreeItem<PlayerModel> root = createTreeData();
        tbStatistics.setRoot(root);
        
        tbStatistics.getColumns().addAll(createColumns());
        

    }


    private TreeItem<PlayerModel> createTreeData () {
  
        final TreeItem<PlayerModel> root = createGroupTreeItem("All Games");

        //erzeugen der gruppen

        Statistics.getInstance().getGames().forEach(game -> {
            final TreeItem<PlayerModel> group1 = createGroupTreeItem(game.getGamemode().getName());
            System.out.println(game.getGamemode().getName());
            
            game.getPlayers().forEach(player -> {
                
                group1.getChildren().add(new TreeItem<>(player));
                
                group1.setExpanded(true);
                
                

            });
            root.getChildren().add(group1);
        });
        root.setExpanded(true);
        return root;


    }


    private TreeItem<PlayerModel> createGroupTreeItem (final String name) {
        return new TreeItem<>(new PlayerModel(name, 0));
    }
    
    private <V> TreeTableColumn<PlayerModel,V> createColumn(final String columnTitle, final String attributeName, final int prefWidth){
    final TreeTableColumn<PlayerModel, V> column = new TreeTableColumn<>(columnTitle);
    column.setPrefWidth(prefWidth);
    column.setCellValueFactory(new TreeItemPropertyValueFactory<>(attributeName));
    
    
    return column;
    }
    
    private List<TreeTableColumn<PlayerModel,?>> createColumns(){
        return Arrays.asList(createColumn("Name", "name", 175),
                             createColumn("Points", "points", 125));
    }


}
