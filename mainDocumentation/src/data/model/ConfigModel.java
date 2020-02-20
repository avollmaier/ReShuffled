package data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigModel {
    @SerializedName("guiHeight")
    private final Integer guiHeight;
    @SerializedName("guiWidth")
    private final Integer guiWidth;
    @SerializedName("gamemodes")
    private final List<GamemodeModel> gamemodes;
    @SerializedName("serial")
    private final ConfigSerialModel serial;
    @SerializedName("internationalization")
    private final ConfigInternationalizationModel internationalization;
    @SerializedName("logPath")
    private String logPath;
    @SerializedName("statisticsPath")
    private String statisticsPath;


    public ConfigModel(String logPath, String statisticsPath, Integer guiHeight, Integer guiWidth, List<GamemodeModel> gamemodes, ConfigSerialModel serial, ConfigInternationalizationModel language) {
        this.logPath = logPath;
        this.statisticsPath = statisticsPath;
        this.guiHeight = guiHeight;
        this.guiWidth = guiWidth;
        this.gamemodes = gamemodes;
        this.serial = serial;
        this.internationalization = language;
    }


    public ConfigInternationalizationModel getInternationalization() {
        return internationalization;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getStatisticsPath() {
        return statisticsPath;
    }

    public void setStatisticsPath(String statisticsPath) {
        this.statisticsPath = statisticsPath;
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


}
