package data.model;

import com.google.gson.annotations.SerializedName;
import data.model.ConfigSerialModel;
import data.model.GamemodeModel;
import java.util.List;

public class ConfigModel {
    @SerializedName("logLevel")
    private final String logLevel;
    @SerializedName("logPath")
    private  String logPath;
    @SerializedName("statisticsPath")
    private  String statisticsPath;
    @SerializedName("guiHeight")
    private final Integer guiHeight;
    @SerializedName("guiWidth")
    private final Integer guiWidth;
    @SerializedName("gamemodes")
    private final List<GamemodeModel> gamemodes;
    @SerializedName("serial")
    private final ConfigSerialModel serial;

    public ConfigModel(String logLevel, String logPath, String statisticsPath, Integer guiHeight, Integer guiWidth, List<GamemodeModel> gamemodes, ConfigSerialModel serial) {
        this.logLevel = logLevel;
        this.logPath = logPath;
        this.statisticsPath = statisticsPath;
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

    public String getStatisticsPath() {
        return statisticsPath;
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

    public ConfigSerialModel getSerial() {
        return serial;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setStatisticsPath(String statisticsPath) {
        this.statisticsPath = statisticsPath;
    } 
}
