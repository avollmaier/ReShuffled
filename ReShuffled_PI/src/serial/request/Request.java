package serial.request;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import logging.Logger;
import exception.SerialException;

/**
 *
 * @author volalm15
 */
public abstract class Request {

    private static final Logger LOG = Logger.getLogger(Request.class.getName());

    private static final java.util.zip.CRC32 CRC32 = new java.util.zip.CRC32();
    private static final DateFormat DATAFORMATTER = new SimpleDateFormat("mm:ss.S");

    private String reqFrame;              // frame bytes sent to µC (only text)
    private final long timeMillisCreatedAt;     // epoch time when this object is created
    private long timeMillisFrameSent;     // epoch time when frame is sent to µC
    private byte[] resFrame;             // frame bytes received from µC
    private long timeMillisFrameReceived; // epoch time ehne frame from µC is received  

    public Request() {
        timeMillisCreatedAt = System.currentTimeMillis();
    }

    public void handleRequestSent() throws IOException {
        timeMillisFrameSent = System.currentTimeMillis();

    }

    public void handleResponse(Response res) throws SerialException {
        timeMillisFrameReceived = res.getTimeMillisCreatedAt();
        byte[] receivedContentCRC = new byte[2];
        String receivedCRC = "", receivedContent ="";
        CRC32.reset();
        resFrame = res.getResFrame();

        LOG.fine("Received response " + Arrays.toString(resFrame));

        System.arraycopy(resFrame, 1, receivedContentCRC, 0, 2);
        CRC32.update(receivedContentCRC);

        for (int i = 4; i <= 11; i++) {
            receivedCRC = receivedCRC + (char) resFrame[i];
        }

        if (receivedCRC.equals(String.format("%08X", CRC32.getValue()))) {
            LOG.fine("Correct CRC");
        } else {
            LOG.fine("Wrong CRC");
            throw new SerialException("Wrong CRC");
        }
        
        for (int i = 1; i <= 2; i++) {
            receivedContent = receivedContent + (char) resFrame[i];
        }
        
        switch (receivedContent) {
            case "OK": {
                LOG.fine("Response OK on board");
            
            } break;
            case "E1": {
                LOG.severe("Error 1 on board");
            
            } break;
            case "E2": {
                LOG.severe("Error 2 on board");
            
            } break;
            case "E3": {
                LOG.severe("Error 3 on board");
            
            } break;
            
            default: {
                LOG.warning("Received unmatchable response data");
            }
        }
        
                
        

    }

    protected void createRequestFrame(String content) {

        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException();
        }

        CRC32.reset();
        CRC32.update(content.getBytes());
        final String crc32 = String.format("%08X", CRC32.getValue());
        final StringBuilder sb = new StringBuilder(128);
        sb.append(':').append(content).append('#').append(crc32).append('\n');
        reqFrame = sb.toString();
    }

    public String getMreqFrame() {
        return reqFrame;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append("Request").append('{');
        sb.append("created=").append(DATAFORMATTER.format(timeMillisCreatedAt));

        sb.append(", mreqFrame='");
        for (int i = 0; i < reqFrame.length(); i++) {
            final char c = reqFrame.charAt(i);
            if (c >= ' ' && c <= '~') {
                sb.append(c);
            } else if (c == '\n') {
                sb.append("\\n");
            } else {
                sb.append("\\").append(Character.getNumericValue(c));
            }
        }
        sb.append("'");
        if (timeMillisFrameSent > 0) {
            sb.append(", sentTime=+").append((timeMillisFrameSent - timeMillisCreatedAt)).append("ms");
        }
        if (timeMillisFrameReceived > 0) {
            sb.append(", sentTime=+").append((timeMillisFrameReceived - timeMillisCreatedAt)).append("ms");
        }

        if (resFrame != null) {
            sb.append(", mresFrame='");
            for (byte b : resFrame) {
                if (b >= 32 && b < 127) {
                    sb.append((char) b);
                } else if (b == '\n') {
                    sb.append("\\n");
                } else {
                    sb.append("\\").append(b < 0 ? (int) b + 256 : (int) b);
                }
            }
        }
        sb.append('}');

        return sb.toString();
    }

    public long getTimeMillisCreatedAt() {
        return timeMillisCreatedAt;
    }

    public long getTimeMillisFrameSent() {
        return timeMillisFrameSent;
    }

    public long getTimeMillisFrameReceived() {
        return timeMillisFrameReceived;
    }

}
