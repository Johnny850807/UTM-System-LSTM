package app.utility.converter;

import java.time.LocalDateTime;

public class DateTimeManager {

    public static LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    }

    /**
     * @param time to parse such as "2007-12-03T10:15:30", not null
     * @return the parsed local date-time, not null
     */
    public static LocalDateTime convertToDateTime(String time) {
        return LocalDateTime.parse(time);
    }
}
