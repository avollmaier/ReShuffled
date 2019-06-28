package config;


public class Config
{
    private String logPath;
    private String version;
    private int gamemode;
    private int autodeal;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public int getAutodeal() {
        return autodeal;
    }

    public void setAutodeal(int autodeal) {
        this.autodeal = autodeal;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }
}
