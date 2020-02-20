/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.List;

/**
 * @author alois
 */
public class AlertUtil {

    public static void showContentDialog(StackPane root, Node noteToBeBlur, List<JFXButton> controls, String header, String body) {

        BoxBlur boxBlur = new BoxBlur(2, 2, 2);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.NONE);

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

}
