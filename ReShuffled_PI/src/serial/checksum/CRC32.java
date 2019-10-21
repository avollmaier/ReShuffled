/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial.checksum;


import logging.Logger;

/**
 * @author alois
 */
public class CRC32 {
    private static final Logger LOG = Logger.getLogger(CRC32.class.getName());

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
