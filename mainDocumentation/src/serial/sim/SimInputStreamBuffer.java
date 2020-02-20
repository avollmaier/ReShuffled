package serial.sim;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author volalm15
 */
public class SimInputStreamBuffer extends InputStream {

    private final boolean blocking;
    private final PipedInputStream pis;
    private final PipedOutputStream pos;

    public SimInputStreamBuffer() throws IOException {
        blocking = true;
        pis = new PipedInputStream(1024);
        pos = new PipedOutputStream(pis);
    }

    @Override
    public int read() throws IOException {
        if (blocking) {
            while (pis.available() <= 0) {
                synchronized (pos) {
                    try {
                        pos.wait(); // read blocks execution until data available
                    } catch (InterruptedException ex) {
                        return -1;
                    }
                }
            }
        } else {
            if (pis.available() <= 0) {
                return -1;
            }
        }
        return pis.read();
    }

    public void write(int b) throws IOException {
        synchronized (pos) {
            pos.write(b);
            pos.notifyAll();
        }
    }

    @Override
    public int available() throws IOException {
        synchronized (pos) {
            return pis.available();
        }
    }

}
