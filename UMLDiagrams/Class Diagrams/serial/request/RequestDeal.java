package serial.request;

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
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName()).append('{');
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
