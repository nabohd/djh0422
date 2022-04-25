package com.djh;

import com.djh.checkout.Checkout;
import com.djh.checkout.CheckoutException;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {
    private Checkout checkout;
    private DateTimeFormatter dateTimeFormatter;

    @BeforeEach
    void setUp() {
        try {
            checkout = new Checkout();
            dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        } catch (IOException | CsvValidationException e){
            fail("Was unable to read/parse the CSV files " + e);
        }

    }


    @Test
    public void firstTest() {
        assertThrows(CheckoutException.class, () ->
                checkout.generateRentalAgreement("JAKR", 5, 101, getLocalDate("09/03/15")));
    }


    @Test
    public void secondTest(){
        try {
            assertEquals("""
                            Tool code: LADW
                            Tool type: Ladder
                            Tool brand: Werner
                            Rental Days: 3
                            Check out date: 2020-07-02
                            Due date: 2020-07-05
                            Daily rental charge: $1.99
                            Charge days: 2
                            Pre-discount charge: $3.98
                            Discount percent: 10%
                            Discount amount: $0.40
                            Final Charge: $3.58""",
                    checkout.generateRentalAgreement("LADW", 3, 10,
                            getLocalDate("07/02/20")).toString());

        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void thirdTest(){
        try {
            assertEquals("""
                            Tool code: CHNS
                            Tool type: Chainsaw
                            Tool brand: Stihl
                            Rental Days: 5
                            Check out date: 2015-07-02
                            Due date: 2015-07-07
                            Daily rental charge: $1.49
                            Charge days: 3
                            Pre-discount charge: $4.47
                            Discount percent: 25%
                            Discount amount: $1.12
                            Final Charge: $3.35""",
                    checkout.generateRentalAgreement("CHNS", 5, 25,
                            getLocalDate("07/02/15")).toString());
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void fourthTest(){
        try {
            assertEquals("""
                            Tool code: JAKD
                            Tool type: Jackhammer
                            Tool brand: DeWalt
                            Rental Days: 6
                            Check out date: 2015-09-03
                            Due date: 2015-09-09
                            Daily rental charge: $2.99
                            Charge days: 3
                            Pre-discount charge: $8.97
                            Discount percent: 0%
                            Discount amount: $0.00
                            Final Charge: $8.97""",
                    checkout.generateRentalAgreement("JAKD",
                    6, 0, getLocalDate("09/03/15")).toString());
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void fifthTest(){
        try {
            assertEquals("""
                            Tool code: JAKR
                            Tool type: Jackhammer
                            Tool brand: Ridgid
                            Rental Days: 9
                            Check out date: 2015-07-02
                            Due date: 2015-07-11
                            Daily rental charge: $2.99
                            Charge days: 6
                            Pre-discount charge: $17.94
                            Discount percent: 0%
                            Discount amount: $0.00
                            Final Charge: $17.94""",
                    checkout.generateRentalAgreement("JAKR", 9, 0,
                            getLocalDate("07/02/15")).toString());
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void sixthTest(){
        try {
            assertEquals("""
                            Tool code: JAKR
                            Tool type: Jackhammer
                            Tool brand: Ridgid
                            Rental Days: 4
                            Check out date: 2020-07-02
                            Due date: 2020-07-06
                            Daily rental charge: $2.99
                            Charge days: 2
                            Pre-discount charge: $5.98
                            Discount percent: 50%
                            Discount amount: $2.99
                            Final Charge: $2.99""",
                    checkout.generateRentalAgreement("JAKR", 4, 50,
                        getLocalDate("07/02/20")).toString());
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void formattingTest(){
        try {
            assertEquals("""
                            Tool code: JAKR
                            Tool type: Jackhammer
                            Tool brand: Ridgid
                            Rental Days: 500
                            Check out date: 2020-07-02
                            Due date: 2021-11-14
                            Daily rental charge: $2.99
                            Charge days: 354
                            Pre-discount charge: $1,058.46
                            Discount percent: 50%
                            Discount amount: $529.23
                            Final Charge: $529.23""",
                    checkout.generateRentalAgreement("JAKR", 500, 50,
                            getLocalDate("07/02/20")).toString());
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void zeroDaysCheckoutExceptionTest() {
        assertThrows(CheckoutException.class, () ->
                checkout.generateRentalAgreement("JAKR", 0, 50, getLocalDate("09/03/15")));
    }

    @Test
    public void negativeDiscountCheckoutExceptionTest() {
        assertThrows(CheckoutException.class, () ->
                checkout.generateRentalAgreement("JAKR", 5, -50, getLocalDate("09/03/15")));
    }

    @Test
    public void lessThanZeroDaysCheckoutExceptionTest() {
        assertThrows(CheckoutException.class, () ->
                checkout.generateRentalAgreement("JAKR", -50, 50, getLocalDate("09/03/15")));
    }



    /**
     * Formats date string input to a LocalDate object.
     * @param date - string representation (MM/dd/yy)
     * @return LocalDate object
     */
    private LocalDate getLocalDate(String date){
        return LocalDate.parse(date, dateTimeFormatter);
    }

}