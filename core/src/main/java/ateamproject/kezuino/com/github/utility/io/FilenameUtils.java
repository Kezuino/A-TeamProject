package ateamproject.kezuino.com.github.utility.io;

public class FilenameUtils {
    /**
     * Removes the .extension from a copy of {@code fileName} parameter's value.
     *
     * @param fileName fileName to remove the .extension from.
     * @return A new path without the extension on the end.
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) return fileName;
        return fileName.replaceFirst("[.][^.]+$", "");
    }
}
