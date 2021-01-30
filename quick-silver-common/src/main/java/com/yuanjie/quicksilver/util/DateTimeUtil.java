package com.yuanjie.quicksilver.util;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * @author yuanjie(qinhua)
 * @date 2020/12/08
 */
public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER;

    private static final ZoneOffset DEFAULT_ZONE_OFFSET;

    static {
        FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        DEFAULT_ZONE_OFFSET = ZoneOffset.of("+8");
    }


    private DateTimeUtil() {
    }

    public static Optional<String> formatDateToString(Date date) {
        return formatTimestampToString(date.getTime());
    }

    public static Optional<String> formatTimestampToString(Long timestampMillSeconds) {
        Instant instant = Instant.ofEpochMilli(timestampMillSeconds);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Shanghai"));
        return formatDateToString(zonedDateTime.toLocalDateTime());
    }

    public static Optional<String> formatDateToString(LocalDateTime localDateTime) {
        return Optional.ofNullable(FORMATTER.format(localDateTime));
    }

    public static Optional<LocalDateTime> parseTextIntoDateTime(String dateTime) {
        try {
            return Optional.ofNullable(dateTime)
                .filter(StringUtils::isNotBlank)
                .map(text -> LocalDateTime.parse(text, FORMATTER));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<Long> parseTextIntoMillSeconds(String dateTime) {
        return parseTextIntoDateTime(dateTime)
            .map(localDateTime -> localDateTime.toInstant(DEFAULT_ZONE_OFFSET))
            .map(Instant::toEpochMilli);
    }

    public static Optional<Long> parseTextIntoSeconds(String dateTime) {
        return parseTextIntoDateTime(dateTime)
            .map(localDateTime -> localDateTime.toEpochSecond(DEFAULT_ZONE_OFFSET));
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
