package data.model;

public class PlayerModel {
    private String name;
    private int points;


    public PlayerModel(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void increment(int value) {
        points += value;
    }

    public void decrement(int value) {
        points -= value;
    }

    public void reset() {
        points = 0;
    }


    public int getPoints() {
        return points;
    }
}
