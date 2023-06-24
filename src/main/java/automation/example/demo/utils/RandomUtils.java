package automation.example.demo.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {
    public static String generateRandomNumber(int i) {
        return RandomStringUtils.randomNumeric(10);
    }

    public static String generateRandomAlphabet() {
        return RandomStringUtils.randomAlphanumeric(10).toUpperCase();
    }
}
