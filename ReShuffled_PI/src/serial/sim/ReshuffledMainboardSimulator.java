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
                while (true) {
                    String reqFrame = "";

                    while (true) {
                        int c = in.read();
                        if (c < 0) {
                            return;
                        }
                        if (c == 10) {
                            break;
                        } else {
                            reqFrame = reqFrame + (char) c;
                        }

                    }

                    LOG.info("[ Simulator ] received " + reqFrame);

                    //build sim response
                    StringBuilder sb = new StringBuilder();

                    sb.append(":").append("#").append("D736D92D").append("\n");

                    LOG.info("[ Simulator ] sending response " + sb.toString());

                    out.write(reqFrame);
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
