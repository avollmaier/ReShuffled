package config.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Gamemodes {

    private final List<Gamemode> gamemodes= new ArrayList<>();

    public void add(Gamemode gamemode) {
        gamemodes.add(gamemode);
    }

    public Gamemode remove(int i) {
        return gamemodes.remove(i);
    }

    public void forEach(Consumer<? super Gamemode> action) {
        gamemodes.forEach(action);
    }

    public boolean isEmpty() {
        return gamemodes.isEmpty();
    }


}
