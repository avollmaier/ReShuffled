package config.Models;

import com.google.gson.annotations.SerializedName;

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
  private List<Gamemode> gamemodes;

  public Config(String version, String logPath, Integer guiHeight, Integer guiWidth, List<Gamemode> gamemodes) {
    this.version = version;
    this.logPath = logPath;
    this.guiHeight = guiHeight;
    this.guiWidth = guiWidth;
    this.gamemodes = gamemodes;
  }

  public Integer getGuiHeight() {
    return guiHeight;
  }

  public Integer getGuiWidth() {
    return guiWidth;
  }

  public String getVersion() {
    return version;
  }

  public String getLogPath() {
    return logPath;
  }

  public List<Gamemode> getGamemodes() {
    return gamemodes;
  }
}
