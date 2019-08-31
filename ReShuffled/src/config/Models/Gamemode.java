package config.Models;

import com.google.gson.annotations.SerializedName;

public class Gamemode {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("autoDeal")
    private boolean autoDeal;
    @SerializedName("autoDealValue")
    private String autoDealValue;
    @SerializedName("cardQuantity")
    private String cardQuantity;

    public Gamemode(int id, String name, boolean autoDeal, String autoDealValue, String cardQuantity) {
        this.id = id;
        this.name = name;
        this.autoDeal = autoDeal;
        this.autoDealValue = autoDealValue;
        this.cardQuantity = cardQuantity;
    }
}
