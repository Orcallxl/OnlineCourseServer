package errortocorrect.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static Timestamp stringToTimestamp(String date)
    {
        return Timestamp.valueOf(date);
    }

    public static Timestamp dateToTimestamp(Date date)
    {
        return new Timestamp(date.getTime());
    }

    public static String TimeStampToString(Timestamp ts) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(ts);
    }
}
