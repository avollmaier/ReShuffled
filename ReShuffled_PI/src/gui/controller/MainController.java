/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import serial.Communication;
import serial.request.RequestShuffle;

/**
 * FXML Controller class
 *
 * @author alois
 */
public class MainController implements Initializable {

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane rootAnchorPane;
    
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

    }

    public void loadShuffleDialog() {
        
        BoxBlur boxBlur = new BoxBlur(2, 2, 2);

        JFXDialogLayout dialogContent = new JFXDialogLayout();

        JFXButton btOk = new JFXButton("Okay. I'll check");
        JFXButton btCancel = new JFXButton("Cancel");

        dialogContent.setBody(new Text("Make sure that game cards were inserted!"));
        dialogContent.setActions(btCancel, btOk);

        JFXDialog dialog = new JFXDialog(rootStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.show();
        
        rootAnchorPane.setEffect(boxBlur);
        
        btOk.setOnAction((ActionEvent arg0) -> {
            dialog.close();
            rootAnchorPane.setEffect(null);
            //Start shuffle process
            RequestShuffle shuffle = new RequestShuffle();
            Communication.getInstance().sendRequestExecutor(shuffle);
        });

        btCancel.setOnAction((ActionEvent arg0) -> {
            dialog.close();
        });
        

    }

}
