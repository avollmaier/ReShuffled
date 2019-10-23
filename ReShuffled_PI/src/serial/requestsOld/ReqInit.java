package serial.requestsOld;


import javax.naming.CommunicationException;
import jssc.SerialPortException;
import logging.Logger;
import serial.checksum.CRC32;

/**
 * @author alois
 */
public class ReqInit extends Request{
    private static String resString, reqString, reqName = "IN";
    private static final Logger LOG = Logger.getLogger(ReqInit.class.getName());

    @Override
    public void sendRequest(Object port) throws SerialPortException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":")
                .append(reqName)
                .append("#")
                // .append(CRC32.calcCRC(reqName))
                .append("\n");
        reqString = stringBuilder.toString();
        //TODO SEND
        LOG.info("Send Request %s as %s", reqName, reqString);
    }

    @Override
    public String getReqMessage() {
        return reqString;
    }

    @Override
    public String getReqName() {
        return reqName;
    }

    @Override
    public String getResponse() {
        return resString;
    }

//    @Override
//    public void handleResponse(String res) {
//        res=resString;
//        if (resString.length() != 1 || !CRC32.checkCRC(resString)) {
//            LOG.warning("Command: " + resString + " was wrong transmitted");
//            serialStatus=serialStatus.ERROR;
//        }
//        else{
//        serialStatus=serialStatus.DONE;
//        }
//    }


    @Override
    public void handleResponse (String res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
