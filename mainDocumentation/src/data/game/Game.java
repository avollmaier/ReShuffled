/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.game;

import data.model.GameModel;
import data.model.GamemodeModel;
import data.model.PlayerModel;

import java.util.List;

/**
 * @author alois
 */
public class Game {
    private static Game instance;
    private final GameModel gameModel;

    private Game(List<PlayerModel> players, boolean gameFinished, GamemodeModel gamemode) {
        gameModel = new GameModel(players, gameFinished, gamemode);
    }

    public static Game createInstance(List<PlayerModel> players, boolean gameFinished, data.model.GamemodeModel gamemode) {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new Game(players, gameFinished, gamemode);
        return instance;
    }
    // *********************************************************

    public static Game getInstance() {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    public List<PlayerModel> getPlayers() {
        return gameModel.getPlayers();
    }

    public boolean isGameFinished() {
        return gameModel.isGameFinished();
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameModel.setGameFinished(gameFinished);
    }

    public GamemodeModel getGamemode() {
        return gameModel.getGamemode();
    }

    public void setGamemode(GamemodeModel gamemode) {
        this.gameModel.setGamemode(gamemode);
    }
}
