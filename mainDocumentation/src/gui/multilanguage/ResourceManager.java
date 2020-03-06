/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.multilanguage;

import data.config.Config;
import logging.Logger;
import util.FileUtil;
import util.ResourceBundleUtils;

import java.nio.charset.StandardCharsets;


/**
 * @author alois
 */
public class ResourceManager {

    private static final Logger LOG = Logger.getLogger(ResourceManager.class.getName());

    private static final String BUNDLE_PREFIX = Config.getInstance().getInternationalization().getBundlePrefix();
    private static ResourceManager instance;
    private final Map<Locale, ResourceBundle> availableRecourceBundles = new HashMap<>();
    private ResourceBundle currentRecourceBundle = null;
    private Locale currentLocale = null;


    public ResourceManager() {
        init();

    }

    public static ResourceManager createInstance() {
        if (instance != null) {
            throw new IllegalStateException("instance already created");
        }
        instance = new ResourceManager();
        return instance;
    }

    // *********************************************************

    public static ResourceManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("instance not created yet");
        }
        return instance;
    }

    private void init() {


        final FileFilter fileFilter = ResourceBundleUtils.createResourceBundleFileFilter(BUNDLE_PREFIX);

        final File[] propertyFiles = FileUtil.getMatchingFiles(new File(Config.getInstance().getInternationalization().getBundlePath()), fileFilter);


        for (final File propertyFile : propertyFiles) {
            try (final InputStream is = new BufferedInputStream(new FileInputStream(propertyFile))) {
                final PropertyResourceBundle resourceBundle = new PropertyResourceBundle(new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)));

                final Locale locale = ResourceBundleUtils.createLocaleFromBundleName(propertyFile.getName());
                addResourceBundle(locale, resourceBundle);


            } catch (IOException ex) {
                LOG.warning("Failed to language bundle frome file " + propertyFile.getAbsolutePath());

            }

        }

    }


    private void addResourceBundle(final Locale locale, ResourceBundle bundle) {
        availableRecourceBundles.put(locale, bundle);

        LOG.info("Loaded language bundle for locale: " + locale);
    }


    public String getLangString(final ResourceKeyEnum keys) {
        return ResourceBundleUtils.getLangString(currentRecourceBundle, keys);
    }


    public boolean activateLocale(final Locale locale) {
        if (supportsLocale(locale)) {
            LOG.info("Activated language bundle for locale: " + locale);
            currentRecourceBundle = availableRecourceBundles.get(locale);
            currentLocale = locale;
            Config.getInstance().getInternationalization().setLanguage(
                    locale.getLanguage());
            Config.getInstance().getInternationalization().setCountry(
                    locale.getCountry());
            Config.getInstance().save();
            return true;
        } else {
            return false;
        }
    }


    public boolean supportsLocale(final Locale locale) {
        return availableRecourceBundles.containsKey(locale);

    }


    public List<Locale> getAvailableLocales() {

        return new ArrayList<>(availableRecourceBundles.keySet());
    }


    public Locale getCurrentLocale() {
        return currentLocale;
    }


    public ResourceBundle getCurrentRecourceBundle() {
        return currentRecourceBundle;
    }


}
