package automation.example.demo.config;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import automation.example.demo.constants.Constants;
import helpers.DataLoaderHelper;
import lombok.Data;

public class BaseConfig {
    private static BaseConfig baseConfig;
    private Map<String, Environment> environments;
    private final String testEnvironment = StringUtils.defaultString(
            System.getProperty("test.environment"), "beta");

    public static BaseConfig getBaseConfig() {
        if (baseConfig == null) {
            synchronized (BaseConfig.class) {
                baseConfig = DataLoaderHelper.loadDataFromSource(Constants.RESOURCE_BASE_CONFIG_PATH, BaseConfig.class);
            }
        }
        return baseConfig;
    }

    public Map<String, Environment> getEnvironments() {
        return this.environments;
    }

    public Environment getEnvironment() {
        return baseConfig.getEnvironments().get(this.testEnvironment);
    }

    @Data
    public static class Environment {
        private String baseUrl;
        private String baseApiUrl;
        private String apiToken;
    }
}
