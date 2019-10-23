package serial.requests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 *
 * @author volalm15
 */
public abstract class Request {
    
    private static final java.util.zip.CRC32 CRC32 = new java.util.zip.CRC32();
    private static final DateFormat DATAFORMATTER = new SimpleDateFormat("mm:ss.S");
    
    private String mreqFrame;              // frame bytes sent to µC (only text)
    private byte [] mresFrame;             // frame bytes received from µC
    private final long mtimeMillisCreatedAt;     // epoch time when this object is created
    private long mtimeMillisFrameSent;     // epoch time when frame is sent to µC
    private long mtimeMillisFrameReceived; // epoch time ehne frame from µC is received 

    public Request () {
        mtimeMillisCreatedAt = System.currentTimeMillis();
    }
    
    
    public void handleRequestSent () {
        mtimeMillisFrameSent = System.currentTimeMillis();
    }
    
    public void handleResponse () {
    }

    protected void createRequestFrame (String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException();
        }
        CRC32.reset();
        CRC32.update(content.getBytes());
        final String crc32 = String.format("%08X", CRC32.getValue());

        final StringBuilder sb = new StringBuilder(128);
        sb.append(':').append(content).append('#').append(crc32).append('\n');
        mreqFrame = sb.toString();
    }


    public String getMreqFrame () {
        return mreqFrame;
    }

    
   
    @Override
    public String toString () {
        final StringBuilder sb = new StringBuilder(128);
        sb.append("Request").append('{');
        sb.append("created=").append(DATAFORMATTER.format(mtimeMillisCreatedAt));
        
        sb.append(", mreqFrame='");
        for (int i = 0; i < mreqFrame.length(); i++) {
            final char c = mreqFrame.charAt(i);
            if (c >= ' ' && c <= '~') {
                sb.append(c);
            } else if (c == '\n') {
                sb.append("\\n");
            } else {
                sb.append("\\").append(Character.getNumericValue(c));
            }
        }
        sb.append("'");
        if (mtimeMillisFrameSent > 0) {
            sb.append(", sentTime=+").append((mtimeMillisFrameSent - mtimeMillisCreatedAt)).append("ms");
        }
        if (mtimeMillisFrameReceived > 0) {
            sb.append(", sentTime=+").append((mtimeMillisFrameReceived - mtimeMillisCreatedAt)).append("ms");
        }
        
        if (mresFrame != null) {
            sb.append(", mresFrame='");
            for (byte b : mresFrame) {
                if (b >= 32 && b < 127) {
                    sb.append((char)b);
                } else if (b == '\n') {
                    sb.append("\\n");
                } else {
                    sb.append("\\").append(b < 0 ? (int)b + 256 : (int)b);
                }
            }
        }
        sb.append('}');
        
        return sb.toString();
    }
    
    

    
}
