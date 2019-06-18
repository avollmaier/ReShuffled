/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import logging.LogBackgroundHandler;
import logging.LogOutputStreamHandler;
import logging.Logger;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 *
 * @author alois
 */
public class Main
{
    public static String VERSION = "0.1";
    
    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    
    static {
        System.setProperty("logging.LogOutputStreamHandler.showRecordHashcode", "false");
        // System.setProperty("logging.LogRecordDataFormattedText.Terminal", "LINUX");
        System.setProperty("logging.Logger.Level", "ALL"); 
        // System.setProperty("logging.Logger.Level", "INFO");
        // System.setProperty("logging.Logger.Level", "WARNING"); 
    }
    
    public static void main (String[] args) throws FileNotFoundException
    {
        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(System.out)));
        LOGP.addHandler(new LogBackgroundHandler(new LogOutputStreamHandler(new FileOutputStream("/var/lib/reshuffled/reshuffled.log"))));
        
        LOG.info("Start of program V%s", VERSION);
        gui.ShuffleGUI.main(args);
        
    }
    
}
