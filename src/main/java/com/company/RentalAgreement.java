package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;

/**
 * This information would probably be stored in a database.
 */
public class RentalAgreement {


    private final Tool tool;
    private final Integer rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private Integer chargeDays;
    private BigDecimal preDiscountCharge;
    private final Integer discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public RentalAgreement(Tool tool, LocalDate checkoutDate,
                           Integer discountPercent, Integer rentalDays) {
        this.tool = tool;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
        this.dueDate = checkoutDate.plusDays(rentalDays); //due date calculated from rentalDays.
        this.chargeDays = 0;
        this.rentalDays = rentalDays;

        this.setChargeDays(); //calculate the charge days removing holidays or anything not charged.
        this.setPreDiscountCharge(); //calculates the charge prior to the discount
        this.setFinalCharge(); //sets the final charge
    }

    /**
     * Calculates the number of charge days based on the toolType information.
     */
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
        preDiscountCharge = this.tool.getToolType().getDailyCharge().multiply(BigDecimal.valueOf(chargeDays));
        preDiscountCharge = preDiscountCharge.setScale(2, RoundingMode.HALF_UP);


    }

    /**
     * Calculate the final charge
     */
    private void setFinalCharge() {
        discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf ((double) discountPercent/100));
        finalCharge = preDiscountCharge.subtract(discountAmount); //may need to round this as well.
        discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);
        finalCharge = finalCharge.setScale(2, RoundingMode.HALF_UP);

    }

//    /**
//     * Rounds double input number to two decimal places
//     *
//     * @param round - Number to be rounded to two decimal places
//     * @return input number rounded to two decimal places
//     */
////    private BigDecimal twoDecimalPlaces(Double round){
////        BigDecimal bigDecimal = BigDecimal.valueOf(round);
////        bigDecimal.setScale(2, RoundingMode.HALF_UP);
////        return bigDecimal.doubleValue();
////    }

    /**
     * Override toString to easily print this object.
     *
     * @return String representation of RentalAgreement
     */
    @Override
    public String toString() {
        return "Tool code: " + tool.getCode() +
                "\nTool type: " + tool.getToolType().getStringToolType() +
                "\nTool brand: " + tool.getBrand() +
                "\nRental Days: " + this.rentalDays +
                "\nCheck out date: " + this.checkoutDate.toString() +
                "\nDue date: " + this.dueDate.toString() +
                "\nDaily rental charge: " + this.tool.getToolType().getDailyCharge() +
                "\nCharge days: " + this.chargeDays +
                "\nPre-discount charge: " + this.preDiscountCharge +
                "\nDiscount percent: " + this.discountPercent +
                "\nDiscount amount: " + this.discountAmount +
                "\nFinal Charge: " + this.finalCharge;
    }
}
