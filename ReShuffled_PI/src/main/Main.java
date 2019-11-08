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
import java.io.InputStream;
import java.util.logging.Level;
import javax.naming.CommunicationException;
import serial.Communication;
import serial.requests.RequestDeal;
import serial.Serial;
import serial.requests.RequestInit;
import serial.requestsOld.ReqInit;


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
                catch (Exception ex) {
                    ex.printStackTrace(System.out);
                    System.out.println("cant create log file " + f.getAbsolutePath() + " - Programm stopped");
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
                catch (Exception ex) {
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
        
        try {
            main.checkConfigExistence();
            final Config cfg = Config.createInstance(CONFIGPATH);
            cfg.setLogPath(logPath);
            cfg.save();
            Serial.createInstance(cfg.getConfigSerial());
            Communication.createInstance(Serial.getInstance());
            //GuiMain.main();
                        
            Thread.sleep(100);
            RequestDeal r =new RequestDeal(2);
            Communication.getInstance().sentRequest(r);
            LOG.debug(r.toString());

          
            
            
        } catch (Exception ex) {
            LOG.severe(ex, "startup fails - config error");
        }

    }
}
