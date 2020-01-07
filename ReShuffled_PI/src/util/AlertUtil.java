/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author alois
 */
public class AlertUtil {

    public static void showContentDialog(StackPane root, Node noteToBeBlur, List<JFXButton> controls, String header, String body) {

        BoxBlur boxBlur = new BoxBlur(2, 2, 2);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout,JFXDialog.DialogTransition.NONE);

        controls.forEach(control -> {
            control.addEventHandler(MouseEvent.MOUSE_CLICKED, (arg0) -> {
                dialog.close();
            });
        });

        dialogLayout.setHeading(new Text(header));
        dialogLayout.setBody(new Text(body));
        dialogLayout.setActions(controls);
        dialog.show();

        dialog.setOnDialogClosed((arg0) -> {
            noteToBeBlur.setEffect(null);
        });

        noteToBeBlur.setEffect(boxBlur);
    }
    
    
     public static void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


