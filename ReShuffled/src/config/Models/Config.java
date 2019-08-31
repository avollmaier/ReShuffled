package config.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Config {
  @SerializedName("version")
  private String version;
  @SerializedName("gamemodes")
  private List<Gamemode> gamemodes;

  public Config(String version, List<Gamemode> gamemodes) {
    this.version = version;
    this.gamemodes = gamemodes;
  }

}
