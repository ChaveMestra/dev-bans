package com.devroom.utils.time;

import org.apache.commons.lang.Validate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {
  private static final Pattern TIME_INPUT_PATTERN = Pattern.compile("^(?i)(t:)*(\\d+)(s|m|h|d|w)*$");
  private static final Map<String, Integer> FACTOR_MAP = new HashMap<>();

  static {
    FACTOR_MAP.put("s", 1);
    FACTOR_MAP.put("m", 60);
    FACTOR_MAP.put("h", 3600);
    FACTOR_MAP.put("d", 86400);
    FACTOR_MAP.put("w", 604800);
  }

  public static long getSecondsLengthFromString(String stringFormat) {
    Validate.notEmpty(stringFormat);
    Matcher matcher = TIME_INPUT_PATTERN.matcher(stringFormat);
    if (matcher.matches()) {
      long time = Integer.parseInt(matcher.group(2));
      String matched3 = matcher.group(3);
      if (matched3 != null) {
        Integer factor = FACTOR_MAP.get(matched3.toLowerCase());
        if (factor != null)
          time *= factor;
      }
      return time;
    }
    return -1L;
  }

  public static String dateStringFromTime(long millis) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(millis);
    DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
    return dateFormat.format(calendar.getTime());
  }

  public static String rawTimeLeft(Date start, Date end) {
    long different = end.getTime() - start.getTime();
    long secondsInMilli = 1000;
    long minutesInMilli = secondsInMilli * 60;
    long hoursInMilli = minutesInMilli * 60;
    long daysInMilli = hoursInMilli * 24;

    long elapsedDays = different / daysInMilli;
    different = different % daysInMilli;

    long elapsedHours = different / hoursInMilli;
    different = different % hoursInMilli;

    long elapsedMinutes = different / minutesInMilli;
    different = different % minutesInMilli;

    long elapsedSeconds = different / secondsInMilli;


    if (elapsedDays > 0) {
      return elapsedDays + "d " + elapsedHours + "h " + elapsedMinutes + "m";
    } else if (elapsedHours > 0) {
      return elapsedHours + "h " + elapsedMinutes + "m";
    } else if (elapsedMinutes > 0) {
      return elapsedMinutes + "m " + elapsedSeconds + "s";
    } else if (elapsedSeconds > 0) {
      return elapsedSeconds + "s";

    }


    return elapsedDays + "d " + elapsedHours + "h " + elapsedMinutes + "m";
  }
}
