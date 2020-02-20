/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.statistics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.game.Game;
import data.model.StatisticsModel;
import logging.Logger;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author alois
 */

public class Statistics {

    private static final Logger LOG = Logger.getLogger(Statistics.class.getName());


    private static Statistics instance;
    // *************************************************************************
    private final File statisticsFile;
    private StatisticsModel statisticsModel;

    private Statistics(String configPath) {
        statisticsFile = new File(configPath);
        readStatistics();
    }

    public static Statistics getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance not created yet");
        }
        return instance;
    }

    public static Statistics createInstance(String statsPath) {
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        } else {
            instance = new Statistics(statsPath);
        }
        return instance;
    }

    public void save() {
        writeStatistics();
    }


    private void readStatistics() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(statisticsFile), StandardCharsets.UTF_8))) {
            int length = 0;
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                length++;
            }
            Gson gson = new Gson();
            statisticsModel = gson.fromJson(stringBuilder.toString(), StatisticsModel.class);

            LOG.info("Statistics file %s successfully read (%d lines)", statisticsFile.getAbsolutePath(), length);

        } catch (Exception ex) {
            LOG.severe(ex, "Error while reading statistics file");
        }
    }


    private void writeStatistics() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        String json = gson.toJson(statisticsModel);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(statisticsFile), StandardCharsets.UTF_8))) {
            writer.write(json);
            LOG.info("Serialized new statistics successfully");
        } catch (IOException ex) {
            LOG.severe(ex, "Error while writing statistics file");
        }
    }


    public int getOverallGamesPlayed() {
        return statisticsModel.getOverallGamesPlayed();
    }


    public void incrementOverallGamesPlayed(int value) {
        statisticsModel.setOverallGamesPlayed(statisticsModel.getOverallGamesPlayed() + value);
    }


    public List<Game> getGames() {
        return statisticsModel.getGames();
    }
}
