package serial.request;

/**
 *
 * @author volalm15
 */
public class RequestAutoDeal extends Request {

    public RequestAutoDeal(int value) {
        super();
        createRequestFrame("A" + value);
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
