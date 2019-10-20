package serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import logging.Logger;

public class SerialService {

    private static final Logger LOG = Logger.getLogger(SerialService.class.getName());
    private static final String defaultPort = "/dev/ttyAMA0";
    private SerialPort serialPort;

    public void openPort() throws SerialPortException, InterruptedException {

        final String[] ports = jssc.SerialPortList.getPortNames();
        for (String port : ports) {
            System.out.println(port);
        }
        jssc.SerialPort serialPort = new SerialPort(defaultPort);
        serialPort.openPort();
        serialPort.setParams(
                SerialPort.BAUDRATE_57600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        LOG.info("Opened %s wit params: Baudrate: 57600, Databits: 8, Stopbits: 1, Parity: NONE", defaultPort);
    }
    public static long calcCRC(String receivedString) {
        byte[] bytes = receivedString.getBytes();
        java.util.zip.Checksum checksum = new java.util.zip.CRC32();

        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue();
    }

    public static boolean checkCRC(String receivedChecksum) {
        String[] checkData = null;
        try {
            receivedChecksum = receivedChecksum.substring(1, 14);
            checkData = receivedChecksum.split("#");
        } catch (ArrayIndexOutOfBoundsException ex) {
            LOG.severe(ex);
        }
        return calcCRC(checkData[0]) == Long.parseLong(checkData[1]);
    }
}
