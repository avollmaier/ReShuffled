package gui.guiMain;

import data.config.Config;
import gui.controller.StartupController;
import gui.multilanguage.ResourceManager;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
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


    public static GuiMain createInstance () {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new GuiMain();
        return instance;
    }


    public static GuiMain getInstance () {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    // *********************************************************
    private final Thread guiThread;


    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    private GuiMain () {

        guiThread = new Thread(new GuiThread());
        guiThread.start();
    }


    public void shutdown () {
        guiThread.interrupt();
    }


    public static class GuiThread extends Application implements Runnable {

        @Override
        public void start (Stage stage) {
            try {
                final String presetLanguage = Config.getInstance().getInternationalization().getLanguage();
                final String presetCountry = Config.getInstance().getInternationalization().getCountry();
                final Locale presetLocal = new Locale(presetLanguage, presetCountry);
                ResourceManager.getInstance().activateLocale(presetLocal);

                Thread.currentThread().setName("GUI Thread");
                final URL fxmlUrl = getClass().getResource("/gui/fxml/startup.fxml");
                final FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl, ResourceManager.getInstance().getCurrentRecourceBundle());
                fxmlLoader.setController(new StartupController());
                final Parent root = fxmlLoader.load();
                stage.setScene(new Scene(root, Config.getInstance().getGuiWidth(), Config.getInstance().getGuiHeight()));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Startup GUI");
                stage.show();

                LOG.info(stage.getTitle() + " started successfully");

            }
            catch (IOException ex) {
                LOG.severe("Exception while running GUI" + ex);
                ex.printStackTrace();
            }

        }


        @Override
        public void run () {
            launch();
        }

    }
}
