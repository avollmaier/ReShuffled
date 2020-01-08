/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import data.config.Config;
import data.model.GamemodeModel;
import gui.multilanguage.ResourceKeyEnum;
import gui.multilanguage.ResourceManager;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author alois
 */
public class AboutController implements Initializable {

    @FXML 
    JFXComboBox cbLanguage;
    @FXML 
    JFXButton btSaveLanguage;
     final ChangeListener<String> languageChangeListener = (observableValue, oldValue, newValue) -> {
         handleLanguageChange();
    };
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btSaveLanguage.setOnAction(this::handleLanguageSave);
        
        fillLanguageComboBox();
        
        cbLanguage.valueProperty().addListener(languageChangeListener);
    }    
    
    
     private void fillLanguageComboBox() {
         ObservableList<String> prepearedTexts = FXCollections.observableArrayList();
        List<Locale> languageOptions = ResourceManager.getInstance().getAvailableLocales();
        
        languageOptions.forEach(locale ->{
        prepearedTexts.add(buildItemText(locale));
        });
        
        cbLanguage.setItems(prepearedTexts);
        
        
     }
     private void handleLanguageChange(){
     
     
     }
     private void handleLanguageSave(){
     ResourceManager.getInstance().activateLocale()
     
     }
     
     private String buildItemText(final Object receivedData){
         String itemText ="";
         if(receivedData instanceof ResourceKeyEnum){
            final ResourceKeyEnum resourceKeyEnum = (ResourceKeyEnum) receivedData;
            final String text = ResourceManager.getInstance().getLangString(resourceKeyEnum);
                }
         if(receivedData instanceof Locale){
             final Locale menueLocal= (Locale) receivedData;
             final Locale currentLocale = ResourceManager.getInstance().getCurrentLocale();
             final String language= menueLocal.getDisplayLanguage(currentLocale);
                final String country= menueLocal.getDisplayCountry(currentLocale);  
             
                itemText = language + " // " + country;
         }
        return itemText;
     
     
     }
        
}
