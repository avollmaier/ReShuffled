package data.config.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConfigModel {
    @SerializedName("logPath")
    private String logPath;
    @SerializedName("guiHeight")
    private Integer guiHeight;
    @SerializedName("guiWidth")
    private Integer guiWidth;
    @SerializedName("gamemodes")
    private List <GamemodeModel> gamemodes;

    public ConfigModel(String logPath, Integer guiHeight, Integer guiWidth, List<GamemodeModel> gamemodes) {
        this.logPath = logPath;
        this.guiHeight = guiHeight;
        this.guiWidth = guiWidth;
        this.gamemodes = gamemodes;
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

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public void setGuiHeight(Integer guiHeight) {
        this.guiHeight = guiHeight;
    }

    public void setGuiWidth(Integer guiWidth) {
        this.guiWidth = guiWidth;
    }

    public void setGamemodes(List<GamemodeModel> gamemodes) {
        this.gamemodes = gamemodes;
    }

    
}
