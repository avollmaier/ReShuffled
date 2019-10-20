package main;

import data.config.data.ConfigModel;
import data.config.service.Config;
import gui.GuiMain;
import logging.LogBackgroundHandler;
import logging.LogOutputStreamHandler;
import logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;


public class Main {
    public static String CONFIGPATH;
    public static String VERSION = "0.1";
    

    public File checkLogExistence () {
        File f = null;
        f = new File(System.getProperty("user.home") + File.separator + "ReShuffled_PI" + File.separator + "reshuffled.log");
        if (f.exists()) {
            System.out.println("log file found atc" + f.getPath());
        }
        else {
            f = new File(File.separator + "var" + File.separator + "lib" + File.separator + "reshuffled" + File.separator + "reshuffled.log");
            if (f.exists()) {
                System.out.println("log file found at" + f.getPath());
            }
            else {
                System.out.println("no config file found");
                try {
                   f.createNewFile();
                }
                catch (IOException ex) {
                    System.out.println("cant log file - Programm stopped");
                    System.exit(0);
                }
                 System.out.println("created log file successfully at " + f.getPath());
            }
        }
        return f;
    }
    
    public void checkConfigExistence () {
        File f = null;
        f = new File(System.getProperty("user.dir") + File.separator + "config.json");
        if (f.exists()) {
            CONFIGPATH = f.getPath();
            System.out.println("config file found at " + f.getPath());
        }
        else {
            f = new File(System.getProperty("user.dir") + File.separator + "config.json");//TODO
            if (f.exists()) {
                CONFIGPATH = f.getPath();
                System.out.println("config file found at " + f.getPath());
            }
            else {
                System.out.println("no config file found");
                try {
                    f.createNewFile();
                }
                catch (IOException ex) {
                    System.out.println("cant create config automatically - please create it manually");
                    System.exit(0);
                }
                System.out.println("created empty config file successfully at " + f.getPath() + " - change the data to the pattern");
                System.exit(0);
            }
        }
    }


    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(Main.class.getName());


    static {
        System.setProperty("logging.LogOutputStreamHandler.showRecordHashcode", "false");
        System.setProperty("logging.Logger.Level", "ALL");
    }


    public static void main (String[] args) {
        Main main = new Main();
        final String logPath = main.checkLogExistence().getPath();


        
        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(System.out)));
        try {
            LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(new FileOutputStream(logPath))));
        }
        catch (FileNotFoundException ex) {
        }
        LOG.info("start of programm with V%s", VERSION);
        
        main.checkConfigExistence();
        Config.createInstance(CONFIGPATH);
        Config.getInstance().setLogPath(logPath);
        Config.updateInstace(CONFIGPATH);
        GuiMain.main();
        

    }
}
