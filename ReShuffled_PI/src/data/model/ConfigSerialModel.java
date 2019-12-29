package data.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author volalm15
 */
public class ConfigSerialModel {

    @SerializedName("disabled")
    private final boolean disabled;
    @SerializedName("device")
    private final String device;
    @SerializedName("baudrate")
    private final int baudrate;
    @SerializedName("timeoutMillis")
    private final long timeoutMillis;
    @SerializedName("secondTryAllowed")
    private final boolean secondTryAllowed;
    @SerializedName("responseByteLength")
    private final int maxReceiveFrameLength;

    public ConfigSerialModel(boolean disabled, String device, int baudrate, long timeoutMillis, boolean secondTryAllowed, int maxReceiveFrameLength) {
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
