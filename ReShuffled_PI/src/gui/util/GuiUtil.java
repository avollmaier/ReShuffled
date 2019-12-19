/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.util;

import data.config.Config;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;
import main.Main;

/**
 *
 * @author alois
 */
public class GuiUtil {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void loadWindow(URL location, String title, Object controllerObject) {
        try {
            FXMLLoader loader = new FXMLLoader(location);
            loader.setController(controllerObject);
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent, Config.getInstance().getGuiWidth(), Config.getInstance().getGuiHeight()));
            stage.show();
            LOG.info(stage.getTitle() + " started successfully");

        } catch (IOException ex) {
            LOG.severe("Exception while running GUI" + ex);
        }
    }
}
