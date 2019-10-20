package serial.requests;


import jssc.SerialPortException;
import logging.Logger;
import serial.SerialService;
import serial.checksum.ChecksumService;

/**
 * @author alois
 */
public class ReqInit extends Request{
    private static String reqString, reqName = "IN";
    private static final Logger LOG = Logger.getLogger(ReqInit.class.getName());

    public void parseRequest() throws SerialPortException, InterruptedException { // kein static
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":")
                .append(reqName)
                .append("#")
                .append(SerialService.calcCRC(reqName))
                .append("\n");
        reqString = stringBuilder.toString();
       
        LOG.info("Send Request %s as %s", reqName, reqString);
        //serialPort.closePort();// am ende des programms
    }

    public void checkResponse(String resString) {
        if (resString.length() != 1 || !SerialService.checkCRC(resString)) {
            LOG.warning("Command: " + resString + " was wrong transmitted");
        }

    }

}
