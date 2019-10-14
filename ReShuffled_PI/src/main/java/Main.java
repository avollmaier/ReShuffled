package main;

import config.data.Config;
import config.service.ConfigService;
import gui.GUIMain;
import logging.LogBackgroundHandler;
import logging.LogOutputStreamHandler;
import logging.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {
    public static String CONFIGPATH="/home/pi/config/config.json";   ///var/lib/reshuffled/reshuffled.log
    public static Config config;

    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(main.Main.class.getName());

    static {
        System.setProperty("logging.LogOutputStreamHandler.showRecordHashcode", "false");
        System.setProperty("logging.Logger.Level", "ALL");
    }

    public static void main(String[] args) throws FileNotFoundException {
        ConfigService configService =new ConfigService();
        config=configService.deserializeService(CONFIGPATH);

        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(System.out)));
        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(new FileOutputStream(config.getLogPath()))));
        LOG.info("start of programm with V%s", config.getVersion());

        GUIMain.main();


    }
}
