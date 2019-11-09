package serial.sim;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import logging.Logger;

/**
 *
 * @author volalm15
 */
public class ReshuffledMainboardSimulator implements Runnable {

    private static final Logger LOG = Logger.getLogger(ReshuffledMainboardSimulator.class.getName());
    private final InputStream in;
    private final BufferedWriter out;

    public ReshuffledMainboardSimulator(InputStream in, OutputStream out) throws UnsupportedEncodingException {
        this.in = in;
        this.out = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        new Thread(this).start();
    }

    @Override
    public void run() {
        LOG.info("ReshuffledMainboardSimulator started");
        while (!Thread.interrupted()) {
            try {
//                while (true) {
//                    int b = in.read();
//                    if (b < 0) {
//                        return;
//                    }
//                    LOG.debug("Byte received...");
//                    out.write(b);
//                }
                while (true) {
                    String frame = "";
                    while (true) {
                        int c = in.read();
                        if (c < 0) {
                            return;
                        }
                        if (c == 10) {
                            break;
                        } else {
                            frame = frame + (char) c;
                        }

                    }
                    out.write("response for " + frame);
                    out.newLine();
                    out.flush();
                }

            } catch (Exception ex) {
                LOG.warning(ex);
            } finally {
                LOG.info("ReshuffledMainboardSimulator stopped");
            }
        }
    }

}
