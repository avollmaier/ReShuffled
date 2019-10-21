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


    public ConfigSerial (boolean disabled, String device, int baudrate) {
        this.disabled = disabled;
        this.device = device;
        this.baudrate = baudrate;
    }

    public ConfigSerial (String device, int baudrate) {
        disabled = false;
        this.device = device;
        this.baudrate = baudrate;
    }


    public boolean isDisabled () {
        return disabled;
    }


    public String getDevice () {
        return device;
    }


    public int getBaudrate () {
        return baudrate;
    }

    
}
