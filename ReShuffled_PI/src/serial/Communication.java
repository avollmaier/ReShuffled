package serial;

import data.config.service.Config;
import java.io.InputStream;
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

    public void sentRequestExecutor(Request req) {
        synchronized (toSentList) {
            toSentList.add(req);
            toSentList.notifyAll();
        }
    }

    private void waitForItemsAvailable(final LinkedList<Request> items) throws InterruptedException {
        while (items.isEmpty()) {
            items.wait();
        }
    }

    private class CommThread implements Runnable {

        @Override
        public void run() {

            LOG.info("Communication Thread started");

            InputStream is = Serial.getInstance().getInputStream();
            byte[] resFrame = new byte[Config.getInstance().getConfigSerial().getResponseByteLength()];

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (toSentList) {
                        LOG.debug("Thread waiting for items ...");
                        waitForItemsAvailable(toSentList);

                        pendingRequest = toSentList.removeFirst();
                        LOG.debug("Thread is consuming " + pendingRequest.getMreqFrame());
                        serial.writeString(pendingRequest.getMreqFrame());
                        pendingRequest.handleRequestSent();
                    }

                    do {//waiting for response
                        Thread.sleep(20);
                        LOG.debug("Waiting for response: " + (System.currentTimeMillis() - pendingRequest.getTimeMillisFrameSent()) + "ms/" + timeoutMillis + "ms");
                    } while (pendingRequest.getTimeMillisFrameSent() + timeoutMillis > System.currentTimeMillis() && is.available() == 0);

                    if (pendingRequest.getTimeMillisFrameSent() + timeoutMillis < System.currentTimeMillis()) { //timeout
                        LOG.severe("Timeout of response from request " + pendingRequest.getMreqFrame());

                        if (Config.getInstance().getConfigSerial().isSecondTryAllowed()) { //check if second send try is allowed
                            sentRequestExecutor(pendingRequest);//send second response
                        } else {
                            throw new SerialException("Serial error due timeout"); //if second try isnt allowed throw serial exception
                        }

                    } else {

                        is.read(resFrame, 0, Config.getInstance().getConfigSerial().getResponseByteLength());

                        if (resFrame[0] == 58 && resFrame[12] == 10) {//check if the frame begins with " : " and ends with a " \n "
                            pendingRequest.handleResponse(resFrame); //handle response 
                        } else if (Config.getInstance().getConfigSerial().isSecondTryAllowed()) { //try to send second response because request was wrong transmitted
                            sentRequestExecutor(pendingRequest);
                        } else {
                            throw new SerialException("Serial error because of a wrong transmission"); //if second try isnt allowed throw serial exception
                        }

                    }
                }

            } catch (Exception ex) {
                LOG.warning(ex, "Communication Thread exception");
                ex.printStackTrace();
            } finally {
                LOG.info("Communication Thread ended");
            }
        }

    }

}
