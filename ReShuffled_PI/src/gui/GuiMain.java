package gui;

import gui.*;
import data.config.service.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.Logger;

public class GuiMain {

    private static final Logger LOG = Logger.getLogger(GuiMain.class.getName());
    private static GuiMain instance;

     public static GuiMain resetInstace() {
         if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        instance = null;
        return instance;
    }
     
    public static GuiMain createInstance() {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new GuiMain();
        return instance;
    }

    public static GuiMain getInstance() {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    // *********************************************************
    private final Thread guiThread;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    private GuiMain() {
        
        guiThread = new Thread(new GuiThread());
        guiThread.start();
    }

    public void shutdown() {
        guiThread.interrupt();
    }

    public static class GuiThread extends Application implements Runnable {

        @Override
        public void start(Stage primaryStage) throws Exception {

            Thread.currentThread().setName("Gui Thread");
            Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
            Scene scene = new Scene(root, Config.getInstance().getGuiWidth(), Config.getInstance().getGuiHeight());
            primaryStage.setScene(scene);
            //primaryStage.initStyle(StageStyle.UNDECORATED);

            LOG.info("gui started successfully");
            primaryStage.show();
        }

        @Override
        public void run() {
               launch();
        }

    }

}
