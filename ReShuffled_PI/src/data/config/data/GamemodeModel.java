package data.config.data;

import com.google.gson.annotations.SerializedName;

public class GamemodeModel {

    @SerializedName("name")
    private String name;
    @SerializedName("autoDeal")
    private boolean autoDeal;
    @SerializedName("autoDealValue")
    private int autoDealValue;
    @SerializedName("cardQuantity")
    private int cardQuantity;
    @SerializedName("playerQuantity")
    private int playerQuantity;

    public GamemodeModel(String name, boolean autoDeal, int autoDealValue, int cardQuantity, int playerQuantity) {
        this.name = name;
        this.autoDeal = autoDeal;
        this.autoDealValue = autoDealValue;
        this.cardQuantity = cardQuantity;
        this.playerQuantity = playerQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAutoDeal() {
        return autoDeal;
    }

    public void setAutoDeal(boolean autoDeal) {
        this.autoDeal = autoDeal;
    }

    public int getAutoDealValue() {
        return autoDealValue;
    }

    public void setAutoDealValue(int autoDealValue) {
        this.autoDealValue = autoDealValue;
    }

    public int getCardQuantity() {
        return cardQuantity;
    }

    public void setCardQuantity(int cardQuantity) {
        this.cardQuantity = cardQuantity;
    }

    public int getPlayerQuantity() {
        return playerQuantity;
    }

    public void setPlayerQuantity(int playerQuantity) {
        this.playerQuantity = playerQuantity;
    }

  

    
}
