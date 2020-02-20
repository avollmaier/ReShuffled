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
import gui.multilanguage.ResourceKeyEnum;
import gui.multilanguage.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import serial.Communication;
import serial.request.RequestShuffle;
import serial.request.RequestShutdown;
import util.AlertUtil;
import util.GuiUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author alois
 */
public class MainController implements Initializable {

    @FXML

    private static MainController instance;
    @FXML
    private StackPane rootStackPane;
    @FXML
    private JFXTabPane rootTabPane;


    public MainController() {
        instance = this;
    }


    public static MainController getInstance() {
        return instance;
    }

    // *************************************************************************

    @Override
    public void initialize(URL arg0, ResourceBundle bundle) {
    }


    private Stage getStage() {
        return (Stage) rootStackPane.getScene().getWindow();
    }


    public void loadShuffleDialog() {
        JFXButton btCancel = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_cancel));
        JFXButton btOk = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_shuffle));

        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            HomeController.getInstance().contentInvisibility(false);
            RequestShuffle shuffle = new RequestShuffle();
            Communication.getInstance().sendRequestExecutor(shuffle);
            HomeController.getInstance().handleCardChanged();
        });

        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btOk), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_info), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_cardCheck));
    }


    public void loadShutdownDialog() {
        JFXButton btCancel = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_cancel));
        JFXButton btOk = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_shutdown));

        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            HomeController.getInstance().contentInvisibility(true);

            Game.getInstance().setGameFinished(false);
            Statistics.getInstance().incrementOverallGamesPlayed(1);
            Statistics.getInstance().getGames().add(Game.getInstance());
            Statistics.getInstance().save();
            RequestShutdown shutdown = new RequestShutdown();
            Communication.getInstance().sendRequestExecutor(shutdown);


            String shutdownCmd = "shutdown -h now";
            if (Runtime.getRuntime().availableProcessors() >= 4) {
                System.exit(0);
            } else {
                try {
                    Process child = Runtime.getRuntime().exec(shutdownCmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btOk), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_info), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_shutdownCheck));
    }


    public void loadGameFinishedDialog() {
        JFXButton btCancel = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_cancel));
        JFXButton btFinished = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_finished));

        btFinished.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {

            //PREPARE GAME FOR SAVING
            Game.getInstance().setGameFinished(true);
            Statistics.getInstance().incrementOverallGamesPlayed(1);
            Statistics.getInstance().getGames().add(Game.getInstance());
            Statistics.getInstance().save();
            GuiUtil.loadWindow(getClass().getResource("/gui/fxml/startup.fxml"), "Gamemode Selection", new StartupController());
            getStage().close();

        });
        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btFinished), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_info), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_finishGame));
    }


    public void loadLanguageChangeDialog() {
        JFXButton btOk = new JFXButton(ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_ok));

        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            HomeController.getInstance().contentInvisibility(false);
            RequestShuffle shuffle = new RequestShuffle();
            Communication.getInstance().sendRequestExecutor(shuffle);
            HomeController.getInstance().handleCardChanged();
        });

        AlertUtil.showContentDialog(rootStackPane, rootTabPane, Arrays.asList(btOk), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_info), ResourceManager.getInstance().getLangString(ResourceKeyEnum.txt_msg_languageChanged));
    }

}
