package data.config.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;


/**
 *
 * @author volalm15
 */
public class ConfigSerial {
    @SerializedName("disabled") private boolean disabled;
    @SerializedName("device") private String device;
    @SerializedName("baudrate") private int baudrate;
    @SerializedName("timeoutMillis") private long timeoutMillis;

    public ConfigSerial(boolean disabled, String device, int baudrate, long timeoutMillis) {
        this.disabled = disabled;
        this.device = device;
        this.baudrate = baudrate;
        this.timeoutMillis = timeoutMillis;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getDevice() {
        return device;
    }

    public int getBaudrate() {
        return baudrate;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }


    
}
