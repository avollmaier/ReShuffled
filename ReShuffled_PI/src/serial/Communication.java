package serial;

import java.util.LinkedList;
import logging.Logger;
import serial.requests.Request;


/**
 *
 * @author volalm15
 */
public class Communication {

    private static final Logger LOG = Logger.getLogger(Communication.class.getName());
    private static Communication instance;
    
    public static Communication createInstance (Serial serial) {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new Communication(serial);
        return instance;
    }
    
    public static Communication getInstance () {
       if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance; 
    }
    
    // *********************************************************
    
    private final Serial serial;
    private final Thread communicationThread;
    private final LinkedList<Request> toSentList = new LinkedList<>();
    private Request pendingRequest;
    
    private Communication (Serial serial) {
        this.serial = serial;
        communicationThread = new Thread (new CommThread ());
        communicationThread.start();
    }
    
    public void sentRequest (Request req) {
        synchronized (toSentList) {
            toSentList.add(req);
            toSentList.notifyAll();
        }
    }
    
    private class CommThread implements Runnable {

        @Override
        public void run () {
            LOG.info("Communication Thread started");
            try {
                while (true) {
                    try {
                        synchronized (toSentList) {
                            toSentList.wait();
                            if (!toSentList.isEmpty()) {
                                pendingRequest = toSentList.removeFirst();
                                serial.writeString(pendingRequest.getMreqFrame());
                                pendingRequest.handleRequestSent();
                            }
                        }
                        
                    } catch (Exception ex) {
                        LOG.warning(ex, "Communication Thread excaption");
                    }
                }

            } finally {
                LOG.info("Communication Thread ended");
            }
        }
        
    }
    
    
}
