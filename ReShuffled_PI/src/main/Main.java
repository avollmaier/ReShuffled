package main;

import config.ConfigService;
import gui.GUIMain;
import logging.LogBackgroundHandler;
import logging.LogOutputStreamHandler;
import logging.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {
    public static String LOGPATH = "/home/pi/reshuffled.log";   ///var/lib/reshuffled/reshuffled.log
    public static String VERSION = "0.1";
    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(main.Main.class.getName());


    static {
        System.setProperty("logging.LogOutputStreamHandler.showRecordHashcode", "false");
        // System.setProperty("logging.LogRecordDataFormattedText.Terminal", "LINUX");

        System.setProperty("logging.Logger.Level", "ALL");
        // System.setProperty("logging.Logger.Level", "INFO");
        // System.setProperty("logging.Logger.Level", "WARNING");
    }

    public static void main(String[] args) throws FileNotFoundException {
        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(System.out)));
        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(new FileOutputStream(LOGPATH))));
        ConfigService.deserializeService();
        VERSION=ConfigService.config.getVersion();
        LOGPATH=ConfigService.config.getLogPath();
        LOG.info("start of programm with V%s", VERSION);
        GUIMain.main();


        // serial.requests.ReqInit.checkResponse(":XX#1443568741\n");


    }
}
