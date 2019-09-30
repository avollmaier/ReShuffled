/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial.requests;


import jssc.SerialPortException;
import logging.Logger;
import serial.SerialService;
import serial.checksum.ChecksumService;

/**
 *
 * @author alois
 */
public class ReqInit
{
    private static String reqString, reqName = "IN";
    private static final Logger LOG = Logger.getLogger(ReqInit.class.getName());

    public static void parseRequest() throws SerialPortException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":")
                .append(reqName)
                .append("#")
                .append(serial.checksum.ChecksumService.calcCRC(reqName))
                .append("\n");
        reqString = stringBuilder.toString();
        SerialService serialService = new SerialService();
        jssc.SerialPort serialPort = serialService.OpenPortService();
        serialPort.writeString(reqString);
        LOG.info("Send Request %s as %s", reqName, reqString);
        serialPort.closePort();
        LOG.info("Closed Port %s", serialPort.getPortName());
    }

    public static void checkResponse(String resString) {
        if (resString.length() != 1 || !ChecksumService.checkCRC(resString)) {
            LOG.warning("Command: " + resString + " was wrong transmitted");
        }

    }

}
