/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import java.util.List;

/**
 * @author alois
 */
public class GameModel {

    private final List<PlayerModel> players;
    private boolean gameFinished;
    private GamemodeModel gamemode;

    public GameModel(List<PlayerModel> players, boolean gameFinished, GamemodeModel gamemode) {
        this.players = players;
        this.gameFinished = gameFinished;
        this.gamemode = gamemode;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public GamemodeModel getGamemode() {
        return gamemode;
    }

    public void setGamemode(GamemodeModel gamemode) {
        this.gamemode = gamemode;
    }

}
