package config.data;

import com.google.gson.annotations.SerializedName;
import config.data.Gamemode;

import java.util.List;

public class Config {
  @SerializedName("version")
  private String version;
  @SerializedName("logPath")
  private String logPath;
  @SerializedName("guiHeight")
  private Integer guiHeight;
  @SerializedName("guiWidth")
  private Integer guiWidth;
  @SerializedName("gamemodes")
  private Gamemodes gamemodes;

  public Config(String version, String logPath, Integer guiHeight, Integer guiWidth, Gamemodes gamemodes) {
    this.version = version;
    this.logPath = logPath;
    this.guiHeight = guiHeight;
    this.guiWidth = guiWidth;
    this.gamemodes = gamemodes;
  }

  public String getVersion() {
    return version;
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

  public Gamemodes getGamemodes() {
    return gamemodes;
  }

  public void setVersion(String version) {
    this.version = version;
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

  public void setGamemodes(Gamemodes gamemodes) {
    this.gamemodes = gamemodes;
  }
}
