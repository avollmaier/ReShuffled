package serial.request;

/**
 *
 * @author volalm15
 */
public class RequestShutdown extends Request {

    public RequestShutdown() {
        super();
        createRequestFrame("XX");
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
