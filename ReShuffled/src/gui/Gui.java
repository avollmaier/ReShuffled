package gui;

//other imports

import config.ConfigService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//logger imports
import javafx.stage.StageStyle;
import logging.Logger;
import main.Main;

public class Gui extends Application {

    private static final Logger LOG = Logger.getLogger(main.Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Reshuffled V" + main.Main.VERSION);

        //create scene and set size from config data
        primaryStage.setScene(new Scene(root, ConfigService.config.getGuiWidth(), ConfigService.config.getGuiHeight()));

        //delete borders
        primaryStage.initStyle(StageStyle.UNDECORATED);
        LOG.info("gui started successfully");

        primaryStage.show();
    }

    public static void main() {
        launch();
    }
}
