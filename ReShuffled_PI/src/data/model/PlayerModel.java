package data.model;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class PlayerModel extends RecursiveTreeObject<PlayerModel>{

    private String name;
    private int points, id;


    public PlayerModel (String name, int points, int id) {
        this.name = name;
        this.points = points;
        this.id = id;
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
    
      public void decrement (int value) {
        points -= value;
    }

    public void reset() {
        points = 0;
    }


    public int getPoints () {
        return points;
    }


    public int getId () {
        return id;
    }


  
    
}
