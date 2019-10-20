package data.config.data;

import com.google.gson.annotations.SerializedName;

public class GamemodeModel {
    @SerializedName("name")
    private String name;
    @SerializedName("autoDeal")
    private boolean autoDeal;
    @SerializedName("autoDealValue")
    private Integer autoDealValue;
    @SerializedName("cardQuantity")
    private Integer cardQuantity;
    @SerializedName("playerQuantity")
    private Integer playerQuantity;

    public GamemodeModel(String name, boolean autoDeal, Integer autoDealValue, Integer cardQuantity, Integer playerQuantity) {
        this.name = name;
        this.autoDeal = autoDeal;
        this.autoDealValue = autoDealValue;
        this.cardQuantity = cardQuantity;
        this.playerQuantity = playerQuantity;
    }

    public String getName() {
        return name;
    }

    public boolean isAutoDeal() {
        return autoDeal;
    }

    public Integer getAutoDealValue() {
        return autoDealValue;
    }

    public Integer getCardQuantity() {
        return cardQuantity;
    }

    public Integer getPlayerQuantity() {
        return playerQuantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAutoDeal(boolean autoDeal) {
        this.autoDeal = autoDeal;
    }

    public void setAutoDealValue(Integer autoDealValue) {
        this.autoDealValue = autoDealValue;
    }

    public void setCardQuantity(Integer cardQuantity) {
        this.cardQuantity = cardQuantity;
    }

    public void setPlayerQuantity(Integer playerQuantity) {
        this.playerQuantity = playerQuantity;
    }
    
}