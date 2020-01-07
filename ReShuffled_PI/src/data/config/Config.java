package data.config;

import exception.ConfigException;
import com.google.gson.*;
import data.model.ConfigInternationalizationModel;
import data.model.ConfigModel;
import data.model.ConfigSerialModel;
import data.model.GamemodeModel;
import logging.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Config {

    private static final Logger LOG = Logger.getLogger(Config.class.getName());
    private static Config instance;


    public static Config getInstance () {
        if (instance == null) {
            throw new IllegalStateException("Instance not created yet");
        }
        return instance;
    }


    public static Config createInstance (String configPath) {
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        }
        else {
            instance = new Config(configPath);
        }
        return instance;
    }

    // *************************************************************************
    private final File configFile;
    private ConfigModel configModel;


    private Config (String configPath) {
        configFile = new File(configPath);
        readConfig();
    }


    public void save () {
        writeConfig();
    }


    private void readConfig () {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8))) {
            int length = 0;
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                length++;
            }
            Gson gson = new Gson();
            configModel = gson.fromJson(stringBuilder.toString(), ConfigModel.class);
            if (configModel.getGuiHeight() == null) {
                throw new ConfigException(configFile, "Missing attribute guiHeight");
            }
            if (configModel.getGuiWidth() == null) {
                throw new ConfigException(configFile, "Missing attribute guiWidth");
            }

            if (configModel.getSerial() == null) {
                throw new ConfigException(configFile, "Missing attribute serial");
            }

            if (configModel.getInternationalization() == null) {
                throw new ConfigException(configFile, "Missing attribute language");
            }


            LOG.info("Config file %s successfully read (%d lines)", configFile.getAbsolutePath(), length);

        }
        catch (Exception ex) {
            LOG.severe(ex, "Error while reading config file");
        }
    }


    private void writeConfig () {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String json = gson.toJson(configModel);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8))) {
            writer.write(json);
            LOG.info("Serialized new config successfully");
        }
        catch (IOException ex) {
            LOG.severe(ex, "Error while writing config file");
        }
    }


    public String getLogLevel () {
        return configModel.getLogLevel();
    }


    public void setLogPath (String logPath) {
        configModel.setLogPath(logPath);
    }


    public void setStatisticsPath (String statisticsPath) {
        configModel.setLogPath(statisticsPath);
    }


    public List<GamemodeModel> getGamemodes () {
        return configModel.getGamemodes();
    }


    public int getGuiWidth () {
        return configModel.getGuiWidth();
    }


    public int getGuiHeight () {
        return configModel.getGuiHeight();
    }


    public ConfigSerialModel getConfigSerial () {
        return configModel.getSerial();
    }


    public ConfigInternationalizationModel getInternationalization () {
        return configModel.getInternationalization();
    }


}
