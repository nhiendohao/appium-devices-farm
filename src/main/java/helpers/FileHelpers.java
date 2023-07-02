package helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import io.restassured.response.Response;

public class FileHelpers {
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

    public static File saveResponseAsFile(Response response, String filePath) {
        String newFilePath = StringHelpers.concatenateFileWithString(filePath, DateTimeHelpers.getDateTime());
        final File file = new File(newFilePath);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(response.body().asByteArray());
        } catch (IOException e) {
            System.out.println("Unable to save to " + newFilePath);
            e.printStackTrace();
        }

        return file;
    }
}
