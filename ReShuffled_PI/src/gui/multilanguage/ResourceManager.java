/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.multilanguage;

import data.config.Config;
import util.ResourceBundleUtils;
import gui.guiMain.GuiMain;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import logging.Logger;
import java.util.ResourceBundle;
import util.FileUtil;


/**
 *
 * @author alois
 */
public class ResourceManager {

    private static final Logger LOG = Logger.getLogger(ResourceManager.class.getName());

    private static final String BUNDLE_PATH = "bundle";
    private final Map<Locale, ResourceBundle> availableRecourceBundles = new HashMap<>();

    private ResourceBundle currentRecourceBundle = null;
    private Locale currentLocale = null;

    private static ResourceManager instance;


    public static ResourceManager createInstance () {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new ResourceManager();
        return instance;
    }


    public static ResourceManager getInstance () {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    // *********************************************************

    public ResourceManager () {
        init();
    }


    private void init () {
            final FileFilter fileFilter = ResourceBundleUtils.createResourceBundleFileFilter("bundle");
            final File[] propertyFiles = FileUtil.getMatchingFiles(new File(Config.getInstance().getInternationalization().getBundlePath()), fileFilter);


            for (final File propertyFile : propertyFiles) {
                try (final InputStream is = new BufferedInputStream(new FileInputStream(propertyFile))) {
                    final PropertyResourceBundle resourceBundle = new PropertyResourceBundle(is);

                    final Locale locale = ResourceBundleUtils.createLocaleFromBundleName(propertyFile.getName());
                    

                }
                catch (IOException ex) {
                    LOG.warning("Failed to language bundle frome file " + propertyFile.getAbsolutePath());

                }

            }
        }

    private void loadAndAddResourceBundle (final Locale locale) {
        try {
            final ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(BUNDLE_PATH, locale);
            availableRecourceBundles.put(locale, resourceBundle);

            LOG.info("Loaded language bundle for locale: " + locale);
        }
        catch (final MissingResourceException ex) {
            LOG.warning("Missing resource " + BUNDLE_PATH + " for locale: " + locale + ex);

        }
    }


    public String getLangString (final ResourceKeyEnum keys) {
        return ResourceBundleUtils.getLangString(currentRecourceBundle, keys);
    }


    public boolean activateLocale (final Locale locale) {
        if (supportsLocale(locale)) {
            currentRecourceBundle = availableRecourceBundles.get(locale);
            currentLocale = locale;
            return true;

        }
        else {
            return false;
        }


    }


    public boolean supportsLocale (final Locale locale) {
        return availableRecourceBundles.containsKey(locale);

    }


}
