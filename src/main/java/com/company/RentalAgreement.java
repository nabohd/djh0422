package com.company;

import java.util.Date;

public class RentalAgreement {
    Tool tool;
    Integer rentalDays;
    Date checkoutDate;
    Date dueDate;
    double dailyRentalCharge;
    Integer chargeDays;
    double preDiscountCharge;
    Integer discountPercent;
    double discountAmount;
    double finalCharge;

    public RentalAgreement(Tool tool, Date checkoutDate, double dailyRentalCharge, Integer discountPercent) {
        this.tool = tool;
        this.checkoutDate = checkoutDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.discountPercent = discountPercent;

        //logic to generate the rest of the variables.
    }

    @Override
    public String toString(){
        return "";
    }
}
