package helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class FileHelper {
    public static List<File> getFileListFromDirectoryPath(String directoryPath) {
        File directory = new File(directoryPath);
        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isDirectory()) {
                resultList.addAll(getFileListFromDirectoryPath(file.getAbsolutePath()));
            }
        }
        return resultList;
    }

    public static File findFileByName(String folderDirectory, String fileName) {
        try {
            return Files.find(
                                Paths.get(folderDirectory), Integer.MAX_VALUE,
                                (path, basicFileAttributes) -> FilenameUtils.getName(
                                        path.getFileName().toString()).equalsIgnoreCase(fileName))
                        .findFirst().get().toFile();
        } catch (IOException exception) {
            throw new RuntimeException(folderDirectory + " can't be found!");
        }
    }
}
