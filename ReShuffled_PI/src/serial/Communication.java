package serial;

import data.config.service.Config;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import logging.Logger;
import serial.requests.Request;
import serial.requests.Response;

/**
 *
 * @author volalm15
 */
public class Communication {

    private static final int MAX_RECEIVE_FRAME_LENGTH = 32;
    
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
    private final Thread communicationSendThread;
    private final Thread communicationReceiveThread;
    private final LinkedList<Request> toSentList = new LinkedList<>();
    private final LinkedList<Response> responseList = new LinkedList<>();
    private Request pendingRequest;

    private final long timeoutMillis = Config.getInstance().getConfigSerial().getTimeoutMillis();
    private final int responseSize = Config.getInstance().getConfigSerial().getResponseByteLength();

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    private Communication(Serial serial) {
        this.serial = serial;
        communicationReceiveThread = new Thread(new CommReceiveThread());
        communicationSendThread = new Thread(new CommSendThread());
        communicationReceiveThread.start();
        communicationSendThread.start();
    }
    
    public void shutdown () {
        communicationSendThread.interrupt();
        communicationReceiveThread.interrupt();
    }

    public void sendRequestExecutor(Request req) {
        synchronized (toSentList) {
            toSentList.add(req);
            toSentList.notifyAll();
        }
    }


    private class CommReceiveThread implements Runnable {

        @Override
        public void run () {
            Thread.currentThread().setName("Communication Receive Thread");
            LOG.info(Thread.currentThread().getName() + " started");
            
            try {
                final InputStream is = Serial.getInstance().getInputStream();
                final byte buffer [] = new byte [MAX_RECEIVE_FRAME_LENGTH];
                int bufferIndex = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    int b = is.read();
                    if (b >= 0 && b < 255) {
                        if (bufferIndex == 0) {
                            if (b == ':') {
                                buffer[bufferIndex++] = (byte)b;
                            } else {
                                LOG.warning("receiving unexpected byte (':' expected, get " + b);
                            }
                        
                        } else if (bufferIndex < buffer.length) {
                            buffer[bufferIndex++] = (byte)b;
                            if (b == '\n') {
                                synchronized(responseList) {
                                    byte [] resBuffer = Arrays.copyOf(buffer, bufferIndex);
                                    bufferIndex = 0;
                                    responseList.add(new Response(resBuffer));
                                    responseList.notifyAll();
                                }
                            }
                        
                        } else {
                            LOG.warning("receiving byte " + b + " cannot be stored (receive buffer overflow)");
                        }
                    }
                }
            } catch (Exception ex) {
                LOG.warning(ex, Thread.currentThread().getName() + " exception");
                ex.printStackTrace(System.err);
            } finally {
                LOG.info(Thread.currentThread().getName() + " ended");
            }
        }

    }
    
    private class CommSendThread implements Runnable {

        @Override
        public void run() {
            Thread.currentThread().setName("Communication Send Thread");
            LOG.info(Thread.currentThread().getName() + " started");

            InputStream is = Serial.getInstance().getInputStream();

            try {
                mainLoop: while (!Thread.currentThread().isInterrupted()) {
                    synchronized (toSentList) {
                        LOG.debug("Thread waiting for items ...");
                        toSentList.wait();
                        if (!toSentList.isEmpty()) {
                            pendingRequest = toSentList.removeFirst();
                            LOG.debug("sending request...");
                            serial.writeString(pendingRequest.getMreqFrame());
                            pendingRequest.handleRequestSent();
                            
                            final long waitingTimeoutMillis = pendingRequest.getTimeMillisFrameSent() + timeoutMillis;
                            Response res = null;
                            synchronized(responseList) {
                                responseList.wait(timeoutMillis);
                                if (responseList.isEmpty()) {
                                    if (Config.getInstance().getConfigSerial().isSecondTryAllowed()) {
                                        sendRequestExecutor(pendingRequest);//send second response
                                        continue mainLoop;
                                    }
                                    throw new SerialException("request timeout, mssing response"); // if second try isnt allowed throw serial exception
                                }
                                res = responseList.removeFirst();
                                if (!responseList.isEmpty()) {
                                    LOG.warning("response list should be empty, but " + responseList.size() + " items in list");
                                    responseList.clear();
                                }
                            }
                            pendingRequest.handleResponse(res);
                        }
                    }
                }

            } catch (Exception ex) {
                LOG.warning(ex, Thread.currentThread().getName() + " exception");
                ex.printStackTrace(System.err);
            } finally {
                LOG.info(Thread.currentThread().getName() + " ended");
            }

        }

    }

}
