package serial.requests;

import java.util.Arrays;

/**
 *
 * @author volalm15
 */
public class RequestDeal extends Request {

    public RequestDeal(int value) {
        super();
        createRequestFrame("D" + value);
    }

    @Override
    public void handleResponse(byte[] receivedResFrame) {
        super.handleResponse(receivedResFrame); //To change body of generated methods, choose Tools | Templates.
        
     
    }

    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName()).append('{');
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
