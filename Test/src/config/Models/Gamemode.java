package config.Models;

import com.google.gson.annotations.SerializedName;

public class Gamemode {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("autoDeal")
    private boolean autoDeal;
    @SerializedName("autoDealValue")
    private Integer autoDealValue;
    @SerializedName("cardQuantity")
    private Integer cardQuantity;

    public Gamemode(Integer id, String name, boolean autoDeal, Integer autoDealValue, Integer cardQuantity) {
        this.id = id;
        this.name = name;
        this.autoDeal = autoDeal;
        this.autoDealValue = autoDealValue;
        this.cardQuantity = cardQuantity;
    }
}
