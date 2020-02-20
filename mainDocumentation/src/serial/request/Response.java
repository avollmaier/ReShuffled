package serial.request;


/**
 * @author volalm15
 */
public class Response {

    private final long timeMillisCreatedAt;
    private final byte[] resFrame;

    public Response(byte[] resFrame) {
        timeMillisCreatedAt = System.currentTimeMillis();
        this.resFrame = resFrame;
    }

    public byte[] getResFrame() {
        return resFrame;
    }

    public long getTimeMillisCreatedAt() {
        return timeMillisCreatedAt;
    }

}
