package serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import logging.Logger;

public class SerialService {

    private static final Logger LOG = Logger.getLogger(SerialService.class.getName());
    private static final String defaultPort = "/dev/ttyAMA0";

    public static SerialPort OpenPortService() throws SerialPortException, InterruptedException {

        final String[] ports = jssc.SerialPortList.getPortNames();
        for (String port : ports) {
            System.out.println(port);
        }
        jssc.SerialPort serialPort = new SerialPort(defaultPort);
        serialPort.openPort();
        serialPort.setParams(
                SerialPort.BAUDRATE_115200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        LOG.info("Opened %s wit params:Baudrate: 115200, Databits: 8, Stopbits: 1, Parity: NONE", defaultPort);

        return serialPort;
    }
}
