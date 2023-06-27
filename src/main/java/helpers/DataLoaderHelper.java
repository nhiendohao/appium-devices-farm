package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import automation.example.demo.constants.Constants;

public class DataLoaderHelper {
    public static <T> T loadDataFromSource(String resourceName, Class<T> objectClass) {
        // sourceName = "path/to/file" under resources folder, for example: "./testdata/animal.json"
        InputStream inputStream = getResourceAsStream(resourceName, objectClass);
        if (inputStream == null) {
            throw new RuntimeException(resourceName + "can't be found!");
        }
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new Gson().fromJson(reader, objectClass);
    }

    public static <T> List<T> loadDataListProvider(Class<T> objectClass, String... fileNames) {
        final List<T> objects = new ArrayList<>();
        Arrays.asList(fileNames).forEach(fileName -> objects.add(loadTestData(fileName, objectClass)));
        return objects;
    }

    public static <T> T[] loadDataArrayProvider(Class<T> objectClass, String... fileNames) {
        final T[] objects = (T[]) Array.newInstance(objectClass, fileNames.length);

        for (int i = 0; i < fileNames.length; i++) {
            objects[i] = loadTestData(fileNames[i], objectClass);
        }
        return objects;
    }

    public static <T> List<T> loadDataProvider(String fileName, Class<T> objectClass) {
        final List<T> objects = new ArrayList<>();

        try {
            final File dataFile = FileHelper.findFileByName(Constants.JSON_DATA_DIRECTORY_PATH, fileName);
            final InputStream inputStream = new FileInputStream(dataFile);
            final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            final JsonArray jsonArray = new Gson().fromJson(reader, JsonArray.class);

            for (int i = 0; i < jsonArray.size(); i++) {
                T object = new Gson().fromJson(jsonArray.get(i), objectClass);
                objects.add(object);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Error("File " + fileName + " not found.");
        }
        return objects;
    }

    public static <T> Collection<Object[]> loadTestData(Class<T> objectClass, String... fileNames) {
        final List<Object> objects = new ArrayList<>();
        Arrays.asList(fileNames).forEach(fileName -> objects.add(loadTestData(fileName, objectClass)));

        return objects.stream()
                .map(obj -> new Object[]{obj})
                .collect(Collectors.toList());
    }

    public static <T> T loadTestData(String fileName, Class<T> objectClass) {
        T object;
        try {
            final File dataFile = FileHelper.findFileByName(Constants.JSON_DATA_DIRECTORY_PATH, fileName);
            final InputStream inputStream = new FileInputStream(dataFile);;
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            object = new Gson().fromJson(reader, objectClass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Error("File " + fileName + " not found.");
        }
        return object;
    }

    public static Collection<Object[]> loadFileData(String... fileNames) {
        List<Object> fileList = new ArrayList<>();
        Arrays.asList(fileNames)
                .forEach(fileName -> fileList.add(
                        FileHelper.findFileByName(Constants.JSON_DATA_DIRECTORY_PATH, fileName)));
        return fileList.stream()
                .map(obj -> new Object[]{obj})
                .collect(Collectors.toList());
    }

    private static <T extends Object> InputStream getResourceAsStream(
            String resourceFile,
            Class<T> objectClass) {
        return objectClass
                .getClassLoader()
                .getResourceAsStream(resourceFile);
    }

}
