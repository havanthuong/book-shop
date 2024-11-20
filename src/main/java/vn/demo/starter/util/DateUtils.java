package vn.demo.starter.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public final class DateUtils {

    public static final String GMT_7_TIMEZONE = "Asia/Bangkok";
    public static final ZoneId GMT_7_ZONE_ID = ZoneId.of(GMT_7_TIMEZONE);

    private DateUtils() {}

    public static Instant currentInstant() {
        return Instant.now(Clock.system(GMT_7_ZONE_ID));
    }
}
