package com.company;

import java.math.BigDecimal;

/**
 * Normally this would be pulled from the database
 */
public class ToolType {
    private String stringToolType;
    private BigDecimal dailyCharge;
    private boolean weekDayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public ToolType(String stringToolType, BigDecimal dailyCharge, boolean weekDayCharge, boolean weekendCharge,
                    boolean holidayCharge) {
        this.stringToolType = stringToolType;
        this.dailyCharge = dailyCharge;
        this.weekDayCharge = weekDayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getStringToolType() {
        return stringToolType;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekDayCharge() {
        return weekDayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }
}
