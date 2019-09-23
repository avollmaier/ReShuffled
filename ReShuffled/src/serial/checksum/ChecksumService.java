/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial.checksum;


import logging.Logger;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author alois
 */
public class ChecksumService {
    private static final Logger LOG = Logger.getLogger(ChecksumService.class.getName());

    public static long calcCRC(String input) {
        byte[] bytes = input.getBytes();
        Checksum checksum = new CRC32();

        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue();
    }

    public static boolean checkCRC(String receivedChecksumString) {
        String[] checkData = null;
        try {
            receivedChecksumString = receivedChecksumString.replaceAll("^.", "");
            receivedChecksumString = receivedChecksumString.replaceAll("..$", "");
            checkData = receivedChecksumString.split("#");
        } catch (ArrayIndexOutOfBoundsException ex) {
            LOG.severe(ex);
        }
        if (calcCRC(checkData[0]) == Long.parseLong(checkData[1])) return true;
        else return false;
    }
}
