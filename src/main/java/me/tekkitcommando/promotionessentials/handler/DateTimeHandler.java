package me.tekkitcommando.promotionessentials.handler;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeHandler {

    private DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");

    public DateTime getDateTime() {
        DateTime dateTimeNow = DateTime.now();
        String dateTimeString = dateTimeNow.toString(formatter);

        return formatter.parseDateTime(dateTimeString);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
