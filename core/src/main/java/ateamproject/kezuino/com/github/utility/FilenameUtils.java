package ateamproject.kezuino.com.github.utility;

public class FilenameUtils {
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) return fileName;
        return fileName.replaceFirst("[.][^.]+$", "");
    }
}
