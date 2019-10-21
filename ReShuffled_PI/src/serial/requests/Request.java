package serial.requests;

import javax.naming.CommunicationException;
import jssc.SerialPortException;
import serial.checksum.CRC32;

public abstract class Request {
    public static enum SerialStatus{
        WAITFORSEND, WAITFORERESPONE, DONE, ERROR
    };

    protected SerialStatus serialStatus;

    public Request() {
        serialStatus = SerialStatus.WAITFORSEND;
    }

    public abstract void sendRequest(Object port) throws SerialPortException;

    public abstract String getReqMessage();

    public abstract String getReqName();

    public abstract void handleResponse(String res);

    public abstract String getResponse();

    public SerialStatus getStatus() {
        return serialStatus;
    }

    public void setStatus(SerialStatus status) {
        this.serialStatus = status;
    }

}
