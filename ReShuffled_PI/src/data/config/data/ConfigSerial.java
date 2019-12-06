package data.config.data;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author volalm15
 */
public class ConfigSerial {

    @SerializedName("disabled")
    private boolean disabled;
    @SerializedName("device")
    private String device;
    @SerializedName("baudrate")
    private int baudrate;
    @SerializedName("timeoutMillis")
    private long timeoutMillis;
    @SerializedName("secondTryAllowed")
    private boolean secondTryAllowed;
    @SerializedName("responseByteLength")
    private int maxReceiveFrameLength;

    public ConfigSerial(boolean disabled, String device, int baudrate, long timeoutMillis, boolean secondTryAllowed, int maxReceiveFrameLength) {
        this.disabled = disabled;
        this.device = device;
        this.baudrate = baudrate;
        this.timeoutMillis = timeoutMillis;
        this.secondTryAllowed = secondTryAllowed;
        this.maxReceiveFrameLength = maxReceiveFrameLength;
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

    public boolean isSecondTryAllowed() {
        return secondTryAllowed;
    }

    public int getMaxReceiveFrameLength() {
        return maxReceiveFrameLength;
    }

   
    
    
}
