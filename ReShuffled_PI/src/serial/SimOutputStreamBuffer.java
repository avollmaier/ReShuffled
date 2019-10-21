
package serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;


/**
 *
 * @author volalm15
 */
public class SimOutputStreamBuffer extends OutputStream {

    private final PipedInputStream pis;
    private final PipedOutputStream pos;

    public SimOutputStreamBuffer () throws IOException {
        pis = new PipedInputStream(1024);
        pos = new PipedOutputStream(pis);
    }

    public int read () throws IOException {
        while (pis.available() <= 0) {
            synchronized (pos) {
                try {
                    pos.wait(); // read blocks execution until data available
                }
                catch (InterruptedException ex) {
                    return -1;
                }
            }
        }
        return pis.read();
    }

    @Override
    public void write (int b) throws IOException {
        synchronized (pos) {
            pos.write(b);
            pos.notifyAll();
        }
    }
    
    public int available () throws IOException {
        synchronized (pos) {
            return pis.available();
        }
    }
}   

