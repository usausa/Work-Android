package usausa.github.io.work.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateHelper {

    public static String format(final String format, final Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    private DateHelper() {
    }
}
