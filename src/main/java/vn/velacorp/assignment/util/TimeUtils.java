package vn.velacorp.assignment.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static Logger logger = LogManager.getLogger(TimeUtils.class);
    public static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String CMS_DATE_FORMAT = "dd-MM-yyyy";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);
    public static final SimpleDateFormat CMS_SIMPLE_DATE_FORMAT = new SimpleDateFormat(CMS_DATE_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(CMS_DATE_FORMAT);
    public static final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat(ISO_DATE_PATTERN);


    private static final String EXCEL_FORMAT = "dd/MM/yyyy";
    public static final SimpleDateFormat EXCEL_SIMPLE_FORMAT = new SimpleDateFormat(EXCEL_FORMAT);

    private static final String NUMBER_FORMAT = "yyyyMMdd";
    public static final SimpleDateFormat NUMBER_SIMPLE_DATE_FORMAT = new SimpleDateFormat(NUMBER_FORMAT);

    static {
        CMS_SIMPLE_DATE_FORMAT.setLenient(false);
    }

    public static LocalTime stringToTime(String time) {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertISO(Date date) {
        if (date == null) return null;
        try {
            return ISO_DATE_FORMAT.format(date);

        } catch (Exception e) {
            logger.error(e);

        }

        return null;
    }

    public static String dateToStringUTC(Date date) {
        return convertISO(date);
    }

    public static String localDateTimeToStringUTC(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) return "";
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return dateTimeFormatter.format(localDateTime);
        } catch (Exception ex) {
            logger.error(ex);
            return "";
        }
    }

    public static String timeToString(LocalTime time) {
        try {
            return time.format(TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalTime secondOfDayToLocalTime(int second) {
        try {
            return LocalTime.ofSecondOfDay(second);
        } catch (Exception e) {
            return null;
        }
    }

    public static synchronized Date convertExcelFormat(String time) {
        try {
            return EXCEL_SIMPLE_FORMAT.parse(time);
        } catch (Exception e) {
            return null;
        }

    }

    public static String dateToString(Date date) {
        if (date == null) return null;
        try {
            return SIMPLE_DATE_FORMAT.format(date);

        } catch (Exception e) {
            logger.error(e);

        }
        return null;
    }

    public static Date stringToDateCMs(String source) {
        if (StringUtils.isEmpty(source)) return null;
        try {
            return CMS_SIMPLE_DATE_FORMAT.parse(source);

        } catch (Exception e) {
            logger.error(e);

        }
        return null;
    }

    public static LocalDate stringToLocalDateCMs(String source) {
        if (StringUtils.isEmpty(source)) return null;
        try {
            return LocalDate.parse(source, DATE_FORMATTER);

        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public static String localDateTimeToString(LocalDateTime source) {
        if (source == null) return null;
        try {
            return SIMPLE_DATE_FORMAT.format(source);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public static String dateToStringCMS(Date date) {
        if (date == null) return null;
        try {
            return CMS_SIMPLE_DATE_FORMAT.format(date);

        } catch (Exception e) {
            logger.error(e);

        }
        return null;
    }


    public static Date stringToDate(String date) {
        if (StringUtils.isEmpty(date)) return null;
        try {
            return SIMPLE_DATE_FORMAT.parse(date);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    public static DateRemain getRemain(Date begin, Date expired) {

        long dateIff = expired.getTime() - begin.getTime();

        if (dateIff <= 0) {
            return new DateRemain(0, TimeUnit.MILLISECONDS);
        }

        if (dateIff >= 86400000) {
            return new DateRemain(TimeUnit.DAYS.convert(dateIff, TimeUnit.MILLISECONDS), TimeUnit.DAYS);
        } else if (dateIff >= 3600000) {
            return new DateRemain(TimeUnit.HOURS.convert(dateIff, TimeUnit.MILLISECONDS), TimeUnit.HOURS);
        } else if (dateIff >= 60000) {
            return new DateRemain(TimeUnit.MINUTES.convert(dateIff, TimeUnit.MILLISECONDS), TimeUnit.MINUTES);
        } else {
            return new DateRemain(0, TimeUnit.MILLISECONDS);
        }


    }

    public static synchronized Date safeAddSecond(Date date, int second) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();

    }

    public static synchronized Date safeAddBeginDay(Date date, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.DAY_OF_MONTH, day);

        return cal.getTime();

    }

    public static DateRange getFirstAndEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date start = new Date(calendar.getTimeInMillis());
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        Date end = new Date(calendar.getTimeInMillis());

        return new DateRange(start, end);

    }

    public static Date getCurrentDateTime() {

        return new Date();
    }

    public static String getRemainStr(Date begin, Date expired) {
        DateRemain d = getRemain(begin, expired);


        return d.getValue() + " " + getTimeLabel(d.getTimeUnit());
    }

    public static String getTimeLabel(TimeUnit timeUnit) {
        return switch (timeUnit) {
            case DAYS -> "Ngày";
            case HOURS -> "Giờ";
            case MINUTES -> "Phút";
            case SECONDS -> "Giây";
            default -> "";
        };
    }


    @Data
    public static class DateRemain {

        private long value;
        private TimeUnit timeUnit;

        public DateRemain(long value, TimeUnit timeUnit) {
            this.value = value;
            this.timeUnit = timeUnit;
        }

        public DateRemain() {

        }
    }

    @Data
    public static class DateRange {
        private Date first;
        private Date end;

        public DateRange(Date first, Date end) {
            this.first = first;
            this.end = end;
        }
    }

    public static String millisecondsToString(long milliseconds) {
        try {
            Date date = new Date(milliseconds);
            return SIMPLE_DATE_FORMAT.format(date);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public static boolean isExpire(Date expireDate) {

        return expireDate != null && getCurrentDateTime().compareTo(expireDate) >= 0;
    }

    public static String convertDateToString(Date date, String pattern) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        return dateFormat.format(date);
    }

    public static List<String> getStartEndDateStringUTC(Date date, String pattern) {
        Instant instant = date.toInstant();

        // Chuyển đổi Instant sang LocalDate theo UTC
        LocalDate localDate = instant.atZone(ZoneId.of("UTC")).toLocalDate();

        // Định nghĩa thời điểm bắt đầu và kết thúc trong ngày
        ZonedDateTime startOfDay = localDate.atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime endOfDay = localDate.atTime(23, 59, 59).atZone(ZoneId.of("UTC"));

        // Định dạng chuỗi đầu ra
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        // Chuyển đổi thành chuỗi
        String startOfDayStr = startOfDay.format(formatter);
        String endOfDayStr = endOfDay.format(formatter);
        return List.of(startOfDayStr, endOfDayStr);
    }
}
