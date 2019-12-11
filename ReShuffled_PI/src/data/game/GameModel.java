/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.game;

import data.config.data.GamemodeModel;
import java.util.List;

/**
 *
 * @author alois
 */
public class GameModel {

    private static GameModel instance;

    public static GameModel createInstance(List<PlayerModel> players, boolean gameFinished, GamemodeModel gamemode) {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new GameModel(players, gameFinished, gamemode);
        return instance;
    }

    public static GameModel getInstance() {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }
    public static void clearInstance(){
    instance=null;
    }

    // *********************************************************
    private List<PlayerModel> players;
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

    public GamemodeModel getGamemode() {
        return gamemode;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public void setGamemode(GamemodeModel gamemode) {
        this.gamemode = gamemode;
    }

}
