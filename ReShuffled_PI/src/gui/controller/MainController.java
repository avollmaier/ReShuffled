/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import gui.services.AlertService;
import java.awt.Container;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    
    public MainController() {
        instance = this;
    }
    
    public static MainController getInstance() {
        return instance;
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/gui/fxml/startup.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            addStackDialog(parent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addStackDialog(Node node) {
        BoxBlur boxBlur = new BoxBlur(10, 10, 10);
        rootTabPane.setDisable(true);
        rootTabPane.setEffect(boxBlur);
        rootStackPane.getChildren().add(node);
        
    }

    public void removeStackDialog(Node node) {
        rootStackPane.getChildren().remove(node);
        rootTabPane.setDisable(false);
        rootTabPane.setEffect(null);
        
    }
    
    public void loadShuffleDialog() {
        JFXButton btCancel = new JFXButton("Cancel");
        JFXButton btOk = new JFXButton("Okay. Let's shuffle");
        
        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            HomeController.getInstance().contentInvisibility(false);
            RequestShuffle shuffle = new RequestShuffle();
            Communication.getInstance().sendRequestExecutor(shuffle);
        });
        
        AlertService.showMaterialDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btOk), "General Info Message", "Make sure that game cards were inserted!");
    }
    
    public void loadShutdownDialog() {
        JFXButton btCancel = new JFXButton("Cancel");
        JFXButton btOk = new JFXButton("Shutdown");
        
        btOk.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
            HomeController.getInstance().contentInvisibility(true);
            //_______________________________
            //
            //TODO save actual Game
            //_______________________________           
            RequestShutdown shutdown = new RequestShutdown();
            Communication.getInstance().sendRequestExecutor(shutdown);
        });
        
        AlertService.showMaterialDialog(rootStackPane, rootTabPane, Arrays.asList(btCancel, btOk), "General Info Message", "Make sure that game cards were inserted!");
    }
    
}
