package com.djh.models;

import java.math.BigDecimal;

/**
 * The ToolType model is populated from the ToolType.csv configuration file.
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
