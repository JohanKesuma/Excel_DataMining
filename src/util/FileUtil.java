/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;

/**
 *
 * @author johan
 */
public class FileUtil {

    public static String getFileExtension(File file) {
        String ext = "";
        if (file != null && file.exists()) {
            String fileName = file.getName();
            ext = fileName.substring(fileName.lastIndexOf("."));
        }
        
        return ext;
    }
}
