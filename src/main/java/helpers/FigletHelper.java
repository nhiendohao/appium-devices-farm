package helpers;

import java.io.IOException;
import java.util.logging.Logger;
import com.github.lalyos.jfiglet.FigletFont;

public class FigletHelper {
    private static final Logger LOGGER = Logger.getLogger(FigletHelper.class.getName());

    public static void figlet(String text) {
        String asciiArt1 = null;
        try {
            asciiArt1 = FigletFont.convertOneLine(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(asciiArt1);
    }
}