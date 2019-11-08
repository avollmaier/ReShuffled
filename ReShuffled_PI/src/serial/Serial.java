package serial;

import serial.sim.SimOutputStreamBuffer;
import serial.sim.SimInputStreamBuffer;
import data.config.data.ConfigSerial;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import jssc.SerialPort;
import jssc.SerialPortTimeoutException;
import logging.Logger;
import serial.SerialException;
import serial.sim.ReshuffledMainboardSimulator;
import serial.sim.SimInputStreamBuffer;
import serial.sim.SimOutputStreamBuffer;


/**
 *
 * @author alois
 */
public class Serial {

    private static final Logger LOG = Logger.getLogger(Serial.class.getName());
    private static Serial instance;
    
    public static Serial createInstance (ConfigSerial config) throws SerialException, IOException {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new Serial (config);
        return instance;
    }

    public static Serial getInstance () {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    // *********************************************************************************

    private final ConfigSerial config;
    private InputStream inputStream;
    private OutputStream outputStream;
    private SimInputStreamBuffer toSim;
    private SimOutputStreamBuffer fromSim;
    

    private Serial (ConfigSerial config) throws SerialException, IOException {
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

    public InputStream getInputStream () {
        return inputStream;
    }
    
    public OutputStream getOutputStream () {
        return outputStream;
    }
    
    private void initSimulation () throws IOException {
        toSim = new SimInputStreamBuffer();
        fromSim = new SimOutputStreamBuffer();
        inputStream = new InputStream () {
            @Override
            public int read () throws IOException {
                return fromSim.read();
            }

            @Override
            public int available () throws IOException {
                return fromSim.available();
            }
            
        };
        outputStream = new SerialSimOutputStream();
        new ReshuffledMainboardSimulator(toSim, fromSim);
        LOG.info("serial simulation activated");
    }

    private void initSerialDevice () throws SerialException {
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


    public void writeString (String s) throws IOException {
        final byte [] bytes = s.getBytes("UTF-8");
        getOutputStream().write(bytes);
        getOutputStream().flush();
    }
    

    private class SerialSimInputStream extends InputStream {

        @Override
        public int read () throws IOException {
            return -1;
        }
        
    }


    private class SerialSimOutputStream extends OutputStream {

        @Override
        public void write (int b) throws IOException {
            toSim.write(b);
        }
        
    }
    
    // *************************************************************************
    
    private class SerialJsscInputStream extends InputStream {
        private final jssc.SerialPort serialPort;

        public SerialJsscInputStream (SerialPort serialPort) {
            this.serialPort = serialPort;
        }
        
        @Override
        public int read () throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    
    

    private class SerialJsscOutputStream extends OutputStream {
        private final jssc.SerialPort serialPort;

        public SerialJsscOutputStream (SerialPort serialPort) {
            this.serialPort = serialPort;
        }

        @Override
        public void write (int b) throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
