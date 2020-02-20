/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import com.google.gson.annotations.SerializedName;


/**
 * @author alois
 */
public class ConfigInternationalizationModel {

    @SerializedName("bundlePath")
    private final String bundlePath;
    @SerializedName("bundlePrefix")
    private final String bundlePrefix;
    @SerializedName("language")
    private String language;
    @SerializedName("country")
    private String country;


    public ConfigInternationalizationModel(String language, String country, String bundlePath, String bundlePrefix) {
        this.language = language;
        this.country = country;
        this.bundlePath = bundlePath;
        this.bundlePrefix = bundlePrefix;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getBundlePath() {
        return bundlePath;
    }


    public String getBundlePrefix() {
        return bundlePrefix;
    }


}
