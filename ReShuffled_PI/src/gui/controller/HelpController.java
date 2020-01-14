/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import gui.guiMain.GuiMain;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logging.Logger;


/**
 * FXML Controller class
 *
 * @author alois
 */
public class HelpController implements Initializable {

    @FXML
    ImageView im1;

    @FXML
    ImageView im2;

    @FXML
    ImageView im3;

    @FXML
    ImageView im4;

    private static final Logger LOG = Logger.getLogger(HelpController.class.getName());


    @Override
    public void initialize (URL url, ResourceBundle rb) {



    }


    private Image getImage (String imgPath, ResourceBundle rb) {
        Image image = null;
        try {

            if (rb.getLocale().getLanguage() == "de") {
                File f = new File(imgPath);
                if (!f.exists()) {
                    throw new IllegalArgumentException("Image not found at path " + f.getPath());
                }
                image = new Image(f.toURI().toString());
            }
            else {
                File f = new File(imgPath);
                if (!f.exists()) {
                    throw new IllegalArgumentException("Image not found at path " + f.getPath());
                }
                image = new Image(f.toURI().toString());

            }


        }
        catch (IllegalArgumentException ex) {
            LOG.warning("Image not found", ex);
        }


        return image;
    }


}
