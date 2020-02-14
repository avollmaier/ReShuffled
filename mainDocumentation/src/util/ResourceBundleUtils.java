/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gui.multilanguage.ResourceKeyEnum;
import java.io.FileFilter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class ResourceBundleUtils {

    private static final String INDICATOR_MISSING_RESOURCE = "?";
    private static final String INDICATOR_MISSING_KEY = "??";


    public static String getLangString (final ResourceBundle resourceBundle, final ResourceKeyEnum key) {
        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key.name());
            }
            catch (final MissingResourceException ex) {
                return INDICATOR_MISSING_KEY + key;
            }
        }
        return INDICATOR_MISSING_RESOURCE + key;
    }


    public static FileFilter createResourceBundleFileFilter (final String bundlename) {
        return (pathname) -> {
            return pathname.getName().startsWith(bundlename)
                && pathname.getName().toLowerCase().endsWith(".properties");
        };

    }


    public static Locale createLocaleFromBundleName (final String name) {
        final int languageIndex = name.indexOf('_');
        final int countryIndex = name.indexOf('_', languageIndex + 1);

        String language = null;
        String country = null;

        if (languageIndex > 0 && name.length() > languageIndex + 3) {

            language = name.substring(languageIndex + 1, languageIndex + 3);

        }
        if (countryIndex > 0 && name.length() > countryIndex + 3) {
            country = name.substring(countryIndex + 1, countryIndex + 3);

        }
        if (language == null) {
            return Locale.getDefault();
        }

        return new Locale(language, country);
    }
    
    
    
}
