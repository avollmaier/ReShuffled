package serial;

import data.model.ConfigSerialModel;
import exception.SerialException;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import logging.Logger;
import serial.sim.ReshuffledMainboardSimulator;
import serial.sim.SimInputStreamBuffer;
import serial.sim.SimOutputStreamBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alois
 */
public class Serial {

    private static final Logger LOG = Logger.getLogger(Serial.class.getName());
    private static Serial instance;
    // *********************************************************************************
    private final ConfigSerialModel config;
    private InputStream inputStream;
    private OutputStream outputStream;
    private SimInputStreamBuffer toSim;
    private SimOutputStreamBuffer fromSim;

    private Serial(ConfigSerialModel config) throws SerialException, IOException {
        this.config = config;
        if (config.isDisabled()) {
            return;
        }
        if (config.getDevice().equals("sim")) {
            initSimulation();
        } else {
            initSerialDevice();
        }
    }

    public static Serial createInstance(ConfigSerialModel config) throws SerialException, IOException {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new Serial(config);
        return instance;
    }

    public static Serial getInstance() {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    private void initSimulation() throws IOException {
        toSim = new SimInputStreamBuffer();
        fromSim = new SimOutputStreamBuffer();
        inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return fromSim.read();
            }

            @Override
            public int available() throws IOException {
                return fromSim.available();
            }

        };
        outputStream = new SerialSimOutputStream();
        new ReshuffledMainboardSimulator(toSim, fromSim);
        LOG.info("serial simulation activated");
    }

    private void initSerialDevice() throws SerialException {
        try {
            final int baudrate = config.getBaudrate();
            final String device = config.getDevice();
            final jssc.SerialPort serialPort = new jssc.SerialPort(device);
            serialPort.openPort();
            serialPort.setParams(baudrate, jssc.SerialPort.DATABITS_8, jssc.SerialPort.STOPBITS_1, jssc.SerialPort.PARITY_NONE);
            inputStream = new SerialJsscInputStream(serialPort);
            outputStream = new SerialJsscOutputStream(serialPort);
            LOG.info("serial device port %s (baudrate %d) opened", device, baudrate);

        } catch (Exception ex) {
            throw new SerialException("initSerialDevice() fails", ex);
        }
    }

    public void writeString(String s) throws IOException {
        final byte[] bytes = s.getBytes("UTF-8");
        getOutputStream().write(bytes);
        getOutputStream().flush();

    }

    private class SerialSimInputStream extends InputStream {

        @Override
        public int read() throws IOException {
            return -1;
        }

    }

    private class SerialSimOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            toSim.write(b);
        }

    }

    // *************************************************************************
    private class SerialJsscInputStream extends InputStream {

        private final jssc.SerialPort serialPort;
        private final List<Byte> receivedByteList = new ArrayList<Byte>();

        //TODO
        public SerialJsscInputStream(SerialPort serialPort) {
            this.serialPort = serialPort;
            try {
                this.serialPort.addEventListener(new SerialPortEventListener() {
                    @Override
                    public void serialEvent(SerialPortEvent arg0) {
                        try {
                            final byte[] received = serialPort.readBytes(1);
                            if (received != null && received.length == 1) {
                                synchronized (receivedByteList) {
                                    receivedByteList.add(received[0]);
                                    receivedByteList.notifyAll();
                                }
                            }
                        } catch (Exception ex) {
                            LOG.warning(ex);
                        }
                    }
                });
            } catch (Exception ex) {
                LOG.warning(ex);
            }
        }

        @Override
        public int read() throws IOException {
            synchronized (receivedByteList) {
                try {
                    while (receivedByteList.isEmpty()) {
                        receivedByteList.wait();
                    }
                    Byte b = receivedByteList.remove(0);
                    return b < 0 ? b + 256 : b;

                } catch (Exception ex) {
                    throw new IOException(ex);
                }
            }
        }


    }

    private class SerialJsscOutputStream extends OutputStream {

        private final jssc.SerialPort serialPort;

        public SerialJsscOutputStream(SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        @Override
        public void write(int b) throws IOException {
            try {
                serialPort.writeByte((byte) b);
            } catch (SerialPortException ex) {
                LOG.warning("Execption while writing byte");
            }

        }

    }

}

//    
//    
//    public boolean setParams(int baudRate, int dataBits, int stopBits, int parity) throws SerialPortException {
//        return serialPort.setParams(baudRate, dataBits, stopBits, parity);
//    }
//
//    
//    public boolean openPort() throws SerialPortException {
//        return serialPort.openPort();
//    }
//
//    public boolean closePort() throws SerialPortException {
//        return serialPort.closePort();
//    }
//
//    public boolean writeString(String string, String charsetName) throws SerialPortException, UnsupportedEncodingException {
//        return serialPort.writeString(string, charsetName);
//    }
//
//    public String readString() throws SerialPortException {
//        return serialPort.readString();
//    }
//
//    public boolean purgePort(int flags) throws SerialPortException {
//        return serialPort.purgePort(flags);
//    }
