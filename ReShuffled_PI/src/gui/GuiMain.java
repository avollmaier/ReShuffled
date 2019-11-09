package gui;

import data.config.service.Config;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;

//logger imports
public class GuiMain extends Application {

    private static final Logger LOG = Logger.getLogger(GuiMain.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/MainFXML.fxml"));
        Scene scene = new Scene(root, Config.getInstance().getGuiWidth(), Config.getInstance().getGuiHeight());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        LOG.info("gui started successfully");
        primaryStage.show();
    }

    public static void main() {
        launch();
    }
}
