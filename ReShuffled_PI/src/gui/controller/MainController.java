/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import data.game.Game;
import data.statistics.Statistics;
import gui.multilanguage.ResourceManager;
import util.AlertUtil;
import util.GuiUtil;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import serial.Communication;
import serial.request.RequestShuffle;
import serial.request.RequestShutdown;


/**
 * FXML Controller class
 *
 * @author alois
 */
public class MainController implements Initializable {

    @FXML
    private StackPane rootStackPane;
    @FXML
    private JFXTabPane rootTabPane;
    @FXML

    private static MainController instance;


    public MainController () {
        instance = this;
    }


    public static MainController getInstance () {
        return instance;
    }

    // *************************************************************************

    @Override
    public void initialize (URL arg0, ResourceBundle arg1) {
    }
    private Stage getStage () {
        return (Stage) rootStackPane.getScene().getWindow();
    }

    //*************************************************
    //DIALOGS
    //*************************************************

    public void loadShuffleDialog () {
        JFXButton btCancel = new JFXButton("Cancel");
        JFXButton btOk = new JFXButton("Okay. Let's shuffle");

        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
                             HomeController.getInstance().contentInvisibility(false);
                             RequestShuffle shuffle = new RequestShuffle();
                             Communication.getInstance().sendRequestExecutor(shuffle);
                             HomeController.getInstance().handleCardChanged();
                         });

        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btOk), "General Info Message", "Make sure that game cards were inserted!");
    }


    public void loadShutdownDialog () {
        JFXButton btCancel = new JFXButton("Cancel");
        JFXButton btOk = new JFXButton("Shutdown");

        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
                             HomeController.getInstance().contentInvisibility(true);

                             Game.getInstance().setGameFinished(false);
                             Statistics.getInstance().incrementOverallGamesPlayed(1);
                             Statistics.getInstance().getGames().add(Game.getInstance());
                             Statistics.getInstance().save();
                             RequestShutdown shutdown = new RequestShutdown();
                             Communication.getInstance().sendRequestExecutor(shutdown);
                         });
        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btOk), "Attention", "Do you really want to shutdown?");
    }


    public void loadGameFinishedDialog () {
        JFXButton btCancel = new JFXButton("Cancel");
        JFXButton btFinished = new JFXButton("Finished");

        btFinished.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {

                                   //PREPARE GAME FOR SAVING
                                   Game.getInstance().setGameFinished(true);
                                   Statistics.getInstance().incrementOverallGamesPlayed(1);
                                   Statistics.getInstance().getGames().add(Game.getInstance());
                                   Statistics.getInstance().save();
                                   GuiUtil.loadWindow(getClass().getResource("/gui/fxml/startup.fxml"), "Gamemode Selection", new StartupController());
                                   getStage().close();

                               });
        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btFinished), "General Info Message", "Do you really want to finish the game?");
    }

}
