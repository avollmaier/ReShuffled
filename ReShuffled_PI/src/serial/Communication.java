package serial;

import data.config.service.Config;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
            byte[] resFrame = new byte[Config.getInstance().getConfigSerial().getResponseByteLength()];
            InputStream is = Serial.getInstance().getInputStream();

            try {
                while (true) {
                    try {
                        synchronized (toSentList) {
                            toSentList.wait();

                            if (!toSentList.isEmpty() && isListening == false) {
                                pendingRequest = toSentList.removeFirst();
                                serial.writeString(pendingRequest.getMreqFrame());
                                pendingRequest.handleRequestSent();
                                isListening = true;
                            }
                        }

                        if (isListening == true) {
                            do {
                                Thread.sleep(100);
                                LOG.debug("Waiting for response: " + (System.currentTimeMillis() - pendingRequest.getTimeMillisFrameSent()) + "ms/" + timeoutMillis + "ms");

                                if (is.available() > 0) {
                                    break;
                                }

                            } while (pendingRequest.getTimeMillisFrameSent() + timeoutMillis > System.currentTimeMillis() && pendingRequest.getTimeMillisFrameReceived() == 0);

                            if (pendingRequest.getTimeMillisFrameSent() + timeoutMillis < System.currentTimeMillis()) {
                                LOG.debug("Timeout of response from request " + pendingRequest.getMreqFrame());
                                if (Config.getInstance().getConfigSerial().isSecondTryAllowed()) {
                                    
                                    sentRequest(pendingRequest);
                                } else {
                                    throw new SerialException("Serial error due timeout");
                                }
                            } else {
                                int i = 0;
                                while (is.available() > 0) {
                                    resFrame[i] = (byte) is.read();
                                    i++;
                                }
                                if (resFrame[0] == 58 && resFrame[12] == 10) {
                                    pendingRequest.handleResponse(resFrame);
                                } else if (Config.getInstance().getConfigSerial().isSecondTryAllowed()) {
                                    
                                    sentRequest(pendingRequest);
                                }else{
                                    throw new SerialException("Serial error due wrong transmission");
                                }

                                resFrame = null;
                                isListening = false;
                            }
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
