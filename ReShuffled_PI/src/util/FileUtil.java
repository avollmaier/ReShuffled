/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileFilter;


/**
 * @author alois
 */
public class FileUtil {

    public static File[] getMatchingFiles(final File path, final FileFilter fileFilter) {
        final File[] files = path.listFiles(fileFilter);
        return files != null ? files : new File[0];

    }
}
