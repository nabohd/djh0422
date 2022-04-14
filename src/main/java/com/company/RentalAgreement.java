package com.company;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;

/**
 * This information would probably be stored somewhere.
 */
public class RentalAgreement {


    private Tool tool;
    private Integer rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private Integer chargeDays;
    private double preDiscountCharge;
    private Integer discountPercent;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement(Tool tool, LocalDate checkoutDate, double discountAmount, double dailyRentalCharge,
                           Integer discountPercent, Integer rentalDays) {
        this.tool = tool;
        this.checkoutDate = checkoutDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.discountPercent = discountPercent;
        this.dueDate = checkoutDate.plusDays(rentalDays); //duedate calculated from rentalDays.
        this.discountAmount = discountAmount;
        this.chargeDays = 0;
        this.rentalDays = rentalDays;

        this.setChargeDays();
        this.setPreDiscountCharge();
        this.setFinalCharge();

    }

    private void setChargeDays() {
        //enumset containing all weekdays.
        EnumSet<DayOfWeek> weekDaySet = EnumSet.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        );

        //enumset containing all weekend days.
        EnumSet<DayOfWeek> weekendSet = EnumSet.of(
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
        );
        boolean weekend = this.tool.getToolType().isWeekendCharge();
        boolean weekDay = this.tool.getToolType().isWeekDayCharge();
        boolean holiday = this.tool.getToolType().isHolidayCharge();

        //if no daily charge, then charge days is 0?
        for (LocalDate date = this.checkoutDate.plusDays(1); date.isBefore(this.dueDate.plusDays(1));
             date = date.plusDays(1)) {

            //if holidays aren't billed, skip them.
            if (!holiday) {
                if (date.getMonthValue() == 7 && date.getDayOfMonth() == 4) { //check for 4th of July
                    continue;
                } else if (date.getMonthValue() == 9 && date.getDayOfMonth() <= 7 //check for labor day
                        && date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                    continue;
                }
            }
            if (weekDay && weekDaySet.contains(date.getDayOfWeek())) {
                ++this.chargeDays;
            } else if (weekend && weekendSet.contains(date.getDayOfWeek())) {
                ++this.chargeDays;
            }
        }


    }

    /**
     * Calculate the charge after charge days is determined.
     * Exclude july4th, first monday in Sept.
     * Sept 1 - Sept 7 (7th latest)
     * <p>
     * Check for sat, sun, july 4th, sept 1 - 7
     * depending on the ToolType.
     */
    private void setPreDiscountCharge() {
        preDiscountCharge *= chargeDays;

    }

    /**
     * Calculate the final charge
     */
    private void setFinalCharge() {
        discountAmount = preDiscountCharge * ((double)discountPercent/100);
        finalCharge = preDiscountCharge - discountAmount;

    }

    /**
     * Override toString to easily print this object.
     *
     * @return String representation of RentalAgreement
     */
    @Override
    public String toString() {
        return "Tool code - " + tool.getCode() +
                "\nTool type - " + tool.getToolType() +
                "\nTool brand - " + tool.getBrand() +
                "\nRental Days - " + this.rentalDays +
                "\nCheck out date - " + this.checkoutDate.toString() +
                "\nDue date - " + this.dueDate.toString() +
                "\nDaily rental charge - " + this.dailyRentalCharge +
                "\nCharge days - " + this.chargeDays +
                "\nPre-discount charge - " + this.preDiscountCharge +
                "\nDiscount percent - " + this.discountPercent +
                "\nDiscount amount - " + this.discountAmount +
                "\nFinal Charge - " + this.finalCharge;
    }
}
