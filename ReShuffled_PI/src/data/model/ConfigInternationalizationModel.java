/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.model;

import com.google.gson.annotations.SerializedName;


/**
 *
 * @author alois
 */
public class ConfigInternationalizationModel {
    @SerializedName("bundlePath")
    private final String bundlePath;
    @SerializedName("bundlePrefix")
    private  final String bundlePrefix;


    public ConfigInternationalizationModel (String bundlePath, String bundlePrefix) {
        this.bundlePath = bundlePath;
        this.bundlePrefix = bundlePrefix;
    }


    public String getBundlePath () {
        return bundlePath;
    }


    public String getBundlePrefix () {
        return bundlePrefix;
    }
    
    
}
