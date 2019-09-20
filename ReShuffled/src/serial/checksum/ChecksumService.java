/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial.checksum;


import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * @author alois
 */
public class ChecksumService {
    public static long calcCRC(String input) {
        byte[] bytes = input.getBytes();
        Checksum checksum = new CRC32();


        checksum.update(bytes, 0, bytes.length);
        return checksum.getValue();
    }
}
