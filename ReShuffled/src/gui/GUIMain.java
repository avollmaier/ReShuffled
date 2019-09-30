package gui;

//other imports

import config.ConfigService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;

//logger imports

public class GUIMain extends Application {
    private static final Logger LOG = Logger.getLogger(GUIMain.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainFXML.fxml"));


        //create scene and set size from config data
        Scene scene = new Scene(root, ConfigService.config.getGuiWidth(), ConfigService.config.getGuiHeight());
        primaryStage.setScene(scene);
        //delete borders
        primaryStage.initStyle(StageStyle.UNDECORATED);
        LOG.info("gui started successfully");
        primaryStage.show();

    }

    public static void main() {
        launch();

    }
}
