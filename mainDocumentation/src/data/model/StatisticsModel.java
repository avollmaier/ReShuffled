/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import com.google.gson.annotations.SerializedName;
import data.game.Game;

import java.util.List;

/**
 * @author alois
 */
public class StatisticsModel {
    @SerializedName("games")
    private final List<Game> games;
    @SerializedName("overallGamesPlayed")
    private int overallGamesPlayed;


    public StatisticsModel(int overallGamesPlayed, List<Game> games) {
        this.overallGamesPlayed = overallGamesPlayed;
        this.games = games;
    }


    public int getOverallGamesPlayed() {
        return overallGamesPlayed;
    }

    public void setOverallGamesPlayed(int overallGamesPlayed) {
        this.overallGamesPlayed = overallGamesPlayed;
    }

    public List<Game> getGames() {
        return games;
    }


}
