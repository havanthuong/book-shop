package vn.demo.starter.constant;

import java.math.BigDecimal;

public final class AppConstants {
    private AppConstants() {}

    public static final int MAX_LOGIN_FAILED = 5;
    public static final String QR_SEPARATE_CHAR = "|";
    public static final int DELAY_SECONDS_FAILED_QUEUE = 180;
    public static final String EMPTY_STR = "";
    public static final BigDecimal BET_MIN_AMOUNT = BigDecimal.valueOf(25);
    public static final BigDecimal BET_MAX_WIN_AMOUNT = BigDecimal.valueOf(2000);
    public static final BigDecimal FUTURE_BET_MAX_WIN_AMOUNT = BigDecimal.valueOf(10000);
    public static final BigDecimal LIVE_BET_MAX_WIN_AMOUNT = BigDecimal.valueOf(300);
    public static final String USD_SYMBOL = "USD";
    public static final String TBET_SYMBOL = "$TBET";
}
