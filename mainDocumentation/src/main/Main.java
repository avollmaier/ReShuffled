package main;

import data.config.Config;
import data.statistics.Statistics;
import gui.multilanguage.ResourceManager;
import logging.LogBackgroundHandler;
import logging.LogOutputStreamHandler;
import logging.Logger;
import serial.Communication;
import serial.Serial;
import serial.request.RequestInit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {

    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    public static String CONFIGPATH;
    public static String VERSION = "0.8";

    static {
        System.setProperty("logging.LogOutputStreamHandler.showRecordHashcode", "false");
        System.setProperty("logging.Logger.Level", "ALL");
    }

    public static void main(String[] args) {

        Main main = new Main();
        final String logPath = main.checkLogExistence().getPath();
        final String statisticsPath = main.checkStatisticsExistence().getPath();

        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(System.out)));
        try {
            LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(new FileOutputStream(logPath))));
        } catch (FileNotFoundException ex) {
        }
        LOG.info("Start of programm with V%s", VERSION);

        try {


            main.checkConfigExistence();
            Statistics.createInstance(statisticsPath);
            Config.createInstance(CONFIGPATH);
            Config.getInstance().setLogPath(logPath);
            Config.getInstance().setStatisticsPath(statisticsPath);
            Config.getInstance().save();

            ResourceManager.createInstance();
            Serial.createInstance(Config.getInstance().getConfigSerial());
            Communication.createInstance(Serial.getInstance());
            gui.guiMain.GuiMain.createInstance();

            RequestInit init = new RequestInit();
            Communication.getInstance().sendRequestExecutor(init);

        } catch (Exception ex) {
            LOG.severe(ex, "startup fails - config error");
        }

    }

    public File checkStatisticsExistence() {
        File f;
        f = new File(System.getProperty("user.dir") + File.separator + "statistics.json");
        if (f.exists()) {
            System.out.println("Statistics file found at " + f.getPath());
        } else {
            System.out.println("No statistics file found");
            try {
                f.createNewFile();
            } catch (Exception ex) {
                System.out.println("Cant create config automatically - please create it manually " + ex);
                System.exit(0);
            }
            System.out.println("Created empty statistics file successfully at " + f.getPath() + " - change the data to the pattern");
            System.exit(0);
        }
        return f;
    }

    public File checkLogExistence() {
        File f;
        f = new File(System.getProperty("user.dir") + File.separator + "reshuffled.log");
        if (f.exists()) {
            System.out.println("Log file found at " + f.getPath());
        } else {
            f = new File(File.separator + "var" + File.separator + "lib" + File.separator + "reshuffled" + File.separator + "reshuffled.log");
            if (f.exists()) {
                System.out.println("Log file found at" + f.getPath());
            } else {
                System.out.println("No config file found");
                try {
                    f.createNewFile();
                } catch (Exception ex) {
                    ex.printStackTrace(System.out);
                    System.out.println("Cant create log file " + f.getAbsolutePath() + " - Programm stopped " + ex);
                    System.exit(0);
                }
                System.out.println("Created log file successfully at " + f.getPath());
            }
        }
        return f;
    }

    public void checkConfigExistence() {
        File f;
        f = new File(System.getProperty("user.dir") + File.separator + "config.json");
        if (f.exists()) {
            CONFIGPATH = f.getPath();
            System.out.println("Config file found at " + f.getPath());
        } else {
            f = new File(System.getProperty("user.dir") + File.separator + "config.json");//TODO
            if (f.exists()) {
                CONFIGPATH = f.getPath();
                System.out.println("Config file found at " + f.getPath());
            } else {
                System.out.println("No config file found");
                try {
                    f.createNewFile();
                } catch (Exception ex) {
                    System.out.println("Cant create config automatically - please create it manually" + ex);
                    System.exit(0);
                }
                System.out.println("Created empty config file successfully at " + f.getPath() + " - change the data to the pattern");
                System.exit(0);
            }
        }
    }
}
