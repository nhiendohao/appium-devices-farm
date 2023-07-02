package helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelpers {

    public static String getDateTime() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        final Date date = new Date();

        return "_" + dateFormat.format(date);
    }
}