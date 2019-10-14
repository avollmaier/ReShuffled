package gui;

//other imports

import config.service.ConfigService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;
import main.Main;

//logger imports

public class GUIMain extends Application {
    private static final Logger LOG = Logger.getLogger(GUIMain.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainFXML.fxml"));

        Scene scene = new Scene(root, Main.config.getGuiWidth(), Main.config.getGuiHeight());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        LOG.info("gui started successfully");
        primaryStage.show();
    }

    public static void main() {
        launch();
    }
}
