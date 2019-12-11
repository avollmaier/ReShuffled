package data.config.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ConfigModel {
    @SerializedName("logLevel")
    private final String logLevel;
    @SerializedName("logPath")
    private String logPath;
    @SerializedName("guiHeight")
    private final Integer guiHeight;
    @SerializedName("guiWidth")
    private final Integer guiWidth;
    @SerializedName("gamemodes")
    private final List<GamemodeModel> gamemodes;
    @SerializedName("serial")
    private final ConfigSerial serial;

    public ConfigModel(String logLevel, String logPath, Integer guiHeight, Integer guiWidth, List<GamemodeModel> gamemodes, ConfigSerial serial) {
        this.logLevel = logLevel;
        this.logPath = logPath;
        this.guiHeight = guiHeight;
        this.guiWidth = guiWidth;
        this.gamemodes = gamemodes;
        this.serial = serial;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public String getLogPath() {
        return logPath;
    }

    public Integer getGuiHeight() {
        return guiHeight;
    }

    public Integer getGuiWidth() {
        return guiWidth;
    }

    public List<GamemodeModel> getGamemodes() {
        return gamemodes;
    }

    public ConfigSerial getSerial() {
        return serial;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    
   
}
