package serial;

import java.io.UnsupportedEncodingException;
import java.util.List;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author alois
 */
public class SerialService {
    private final SerialPort serialPort;

    public SerialService(String portName) {
        serialPort=new SerialPort(portName);
    }

    public boolean setParams(int baudRate, int dataBits, int stopBits, int parity) throws SerialPortException {
        return serialPort.setParams(baudRate, dataBits, stopBits, parity);
    }

    
    public boolean openPort() throws SerialPortException {
        return serialPort.openPort();
    }

    public boolean closePort() throws SerialPortException {
        return serialPort.closePort();
    }

    public boolean writeString(String string, String charsetName) throws SerialPortException, UnsupportedEncodingException {
        return serialPort.writeString(string, charsetName);
    }

    public String readString() throws SerialPortException {
        return serialPort.readString();
    }

    public boolean purgePort(int flags) throws SerialPortException {
        return serialPort.purgePort(flags);
    }

    

    
    
}
