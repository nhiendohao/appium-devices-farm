package helpers;

import java.io.File;

import org.apache.commons.lang3.RandomStringUtils;

public class StringHelpers {
    public static String generateRandomNumber(int i) {
        return RandomStringUtils.randomNumeric(10);
    }

    public static String generateRandomAlphabet() {
        return RandomStringUtils.randomAlphanumeric(10).toUpperCase();
    }

    public static String getFileName(String filePath) {
        String[] pathSegments = filePath.split("/");
        String fileName = pathSegments[pathSegments.length - 1];
        return fileName;
    }

    public static String getFileDirectory(String filePath) {
        File file = new File(filePath);
        return file.getParent();
    }

    public static String getFileExtension(String fileName) {
        String[] fileSegments = fileName.split("\\.");
        String extension = fileSegments[fileSegments.length - 1];
        return extension;
    }

    public static String replaceFileNameWithNewExtension(String fileName, String newExtension) {
        String fileExtension = getFileExtension(fileName);
        return fileName.replace("."  + fileExtension, "." + newExtension);
    }

    public static String concatenateFileNameWithString(String fileName, String string) {
        String extension = getFileExtension(fileName);
        return fileName.replace("."  + extension, string + "." + extension);
    }

    public static String concatenateFileWithString(String filePath, String string) {
        String fileDirectory = getFileDirectory(filePath);
        String fileName = getFileName(filePath);
        String newFileName = concatenateFileNameWithString(fileName, string);
        return fileDirectory + "/" + newFileName;
    }

}