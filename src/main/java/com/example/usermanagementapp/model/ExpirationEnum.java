package com.example.usermanagementapp.model;

import java.time.LocalDateTime;

public enum ExpirationEnum {

    FIVE_MINUTES("5m", "5 minutes", 5),
    FIFTEEN_MINUTES("15m", "15 minutes", 15),
    ONE_HOUR("1h", "1 hour", 60),
    ONE_DAY("1d", "1 day", 1440),
    ONE_MONTH("1mo", "1 month", 43200),
    SIX_MONTHS("6mo", "6 months", 259200),
    ONE_YEAR("1y", "1 year", 525600),
    UNLIMITED("unlimited", "Unlimited", -1);

    private String id;
    private String label;
    private int minutes;

    ExpirationEnum(String id, String label, int minutes) {
        this.id = id;
        this.label = label;
        this.minutes = minutes;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getMinutes() {
        return minutes;
    }

    public LocalDateTime calculateExpirationTime() {
        LocalDateTime now = LocalDateTime.now();
        return this == UNLIMITED ? now.plusYears(100) : now.plusMinutes(minutes);
    }

    public static ExpirationEnum fromId(String id) {
        for (ExpirationEnum option : values()) {
            if (option.id.equals(id)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Unknown expiration option: " + id);
    }
}
