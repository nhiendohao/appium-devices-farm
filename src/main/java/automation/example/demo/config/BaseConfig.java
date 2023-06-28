package automation.example.demo.config;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import automation.example.demo.constants.Constants;
import helpers.DataLoaderHelper;

public class BaseConfig {
    private static BaseConfig instance;
    private Map<String, Environment> environments;
    private final String testEnvironment = StringUtils.defaultString(
            System.getProperty("test.environment"), "beta");

    public static BaseConfig getInstance() {
        if (instance == null) {
            synchronized (BaseConfig.class) {
                instance = DataLoaderHelper.loadDataFromSource(Constants.RESOURCE_BASE_CONFIG_PATH, BaseConfig.class);
            }
        }
        return instance;
    }

    public Map<String, Environment> getEnvironments() {
        return this.environments;
    }

    public Environment getEnvironment() {
        return instance.getEnvironments().get(this.testEnvironment);
    }

    public static class Environment {
        private String baseUrl;
        private String baseApiUrl;
        private String apiToken;
    }
}
