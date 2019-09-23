/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial.requests;


import jssc.SerialPortException;
import logging.Logger;
import serial.SerialService;

/**
 *
 * @author alois
 */
public class ReqInit
{
    private static String reqString, reqName = "IN";
    private static final Logger LOG = Logger.getLogger(ReqInit.class.getName());

    public static void buildRequest() throws SerialPortException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":")
                .append(reqName)
                .append("#")
                .append(serial.checksum.ChecksumService.calcCRC(reqName))
                .append("\n");
        reqString = stringBuilder.toString();
        jssc.SerialPort serialPort = SerialService.OpenPortService();
        serialPort.writeString(reqString);
        LOG.info("Send Request %s as %s", reqName, reqString);
    }

    public static void checkResponse() {


    }

    public static String getReqString() {
        return reqString;
    }

    public static String getReqName() {
        return reqName;
    }

}
