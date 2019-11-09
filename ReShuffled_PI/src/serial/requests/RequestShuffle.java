package serial.requests;

/**
 *
 * @author volalm15
 */
public class RequestShuffle extends Request {

    public RequestShuffle() {
        super();
        createRequestFrame("SH");
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
