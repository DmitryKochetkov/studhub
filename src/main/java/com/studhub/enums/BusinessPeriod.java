package com.studhub.enums;

import java.time.LocalDateTime;

public enum BusinessPeriod {
    WEEK,
    MONTH,
    THREE_MONTH,
    SIX_MONTH,
    YEAR;

    public LocalDateTime getFirstDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        switch (this) {
            case WEEK:
                return localDateTime.minusWeeks(1);

            case MONTH:
                return localDateTime.minusMonths(1);

            case THREE_MONTH:
                return localDateTime.minusMonths(3);

            case SIX_MONTH:
                return localDateTime.minusMonths(6);

            case YEAR:
                return localDateTime.minusYears(1);
        }
        return localDateTime;
    }
}
