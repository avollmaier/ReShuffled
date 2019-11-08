package serial;

import data.config.service.Config;
import java.io.InputStream;
import java.util.LinkedList;
import logging.Logger;
import serial.requests.Request;
import serial.requests.RequestInit;

/**
 *
 * @author volalm15
 */
public class Communication {

    private static final Logger LOG = Logger.getLogger(Communication.class.getName());
    private static Communication instance;

    public static Communication createInstance(Serial serial) {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new Communication(serial);
        return instance;
    }

    public static Communication getInstance() {
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
    private long timeoutMillis = Config.getInstance().getConfigSerial().getTimeoutMillis();

    private Communication(Serial serial) {
        this.serial = serial;
        communicationThread = new Thread(new CommThread());
        communicationThread.start();
    }

    public void sentRequest(Request req) {
        synchronized (toSentList) {
            toSentList.add(req);
            toSentList.notifyAll();
        }
    }

    private class CommThread implements Runnable {

        @Override
        public void run() {

            LOG.info("Communication Thread started");
            
            boolean isListening = false;
            byte[] resFrame = null; 
            
            try {
                while (true) {
                    try {
                        synchronized (toSentList) {
                            toSentList.wait();

                            if (!toSentList.isEmpty() && isListening == false) {
                                pendingRequest = toSentList.removeFirst();
                                serial.writeString(pendingRequest.getMreqFrame());
                                pendingRequest.handleRequestSent();
                                
                            }
                        }
                        
                        
                        synchronized (resFrame) {
                         do {
                        resFrame.wait(100);
                        LOG.debug("Waiting for response: " + (System.currentTimeMillis() - pendingRequest.getTimeMillisFrameSent()) + "ms/" + timeoutMillis + "ms");
                        } while (pendingRequest.getTimeMillisFrameSent() + timeoutMillis > System.currentTimeMillis() && pendingRequest.getTimeMillisFrameReceived() == 0);
                        
                            System.out.println("hi");
                        
                        
                        }

                    } catch (Exception ex) {
                        LOG.warning(ex, "Communication Thread exception");
                        ex.printStackTrace();
                    }
                }

            } finally {
                LOG.info("Communication Thread ended");
            }
        }

    }

}
