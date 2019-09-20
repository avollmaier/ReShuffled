package serial;

import config.ConfigService;
import jssc.SerialPort;
import jssc.SerialPortException;
import logging.Logger;
import serial.checksum.ChecksumService;

public class OpenPortService {

    private static final Logger LOG = Logger.getLogger(ConfigService.class.getName());
    private static final String portName = "/dev/ttyAMA0";

    public void OpenPortService() throws SerialPortException, InterruptedException {
        final String[] ports = jssc.SerialPortList.getPortNames();
        for (String port : ports) {
            System.out.println(port);
        }
        jssc.SerialPort serialPort = new SerialPort(portName);
        serialPort.openPort();
        serialPort.setParams(
                SerialPort.BAUDRATE_115200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        LOG.info("params: Baudrate: 115200, Databits: 8, Stopbits: 1, Parity: NONE");
    }

    public static void main(String[] args) {
        System.out.println(ChecksumService.calcCRC("1"));
    }

}
