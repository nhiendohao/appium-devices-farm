package helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHelpers {
        private static final Logger LOGGER = LoggerFactory.getLogger(LoggerHelpers.class);

        public void logDebug(String logging) {
            LOGGER.debug("\u001B[34m" + logging + "\u001B[0m");
        }

        public void logInfo(String logging) {
            LOGGER.info("\u001B[32m" + logging + "\u001B[0m");
        }

        public void logError(String logging) {
            LOGGER.error("\u001B[31m" + logging + "\u001B[0m");
        }
}
