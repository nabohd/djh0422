package com.company;

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
            fail("Was unable to read/parse the CSV files" + e);
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
            assertEquals("Tool code: LADW\n" +
                    "Tool type: Ladder\n" +
                    "Tool brand: Werner\n" +
                    "Rental Days: 3\n" +
                    "Check out date: 2020-07-02\n" +
                    "Due date: 2020-07-05\n" +
                    "Daily rental charge: 1.99\n" +
                    "Charge days: 2\n" +
                    "Pre-discount charge: 3.98\n" +
                    "Discount percent: 10\n" +
                    "Discount amount: 0.40\n" +
                    "Final Charge: 3.58",
                    checkout.generateRentalAgreement("LADW", 3, 10,
                            getLocalDate("07/02/20")).toString());

        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void thirdTest(){
        try {
            assertEquals("Tool code: CHNS\n" +
                    "Tool type: Chainsaw\n" +
                    "Tool brand: Stihl\n" +
                    "Rental Days: 5\n" +
                    "Check out date: 2015-07-02\n" +
                    "Due date: 2015-07-07\n" +
                    "Daily rental charge: 1.49\n" +
                    "Charge days: 3\n" +
                    "Pre-discount charge: 4.47\n" +
                    "Discount percent: 25\n" +
                    "Discount amount: 1.12\n" +
                    "Final Charge: 3.35",
                    checkout.generateRentalAgreement("CHNS", 5, 25,
                            getLocalDate("07/02/15")).toString());
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void fourthTest(){
        try {
            checkout.generateRentalAgreement("JAKD", 6, 0, getLocalDate("09/03/15"));
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void fifthTest(){
        try {
            checkout.generateRentalAgreement("JAKR", 9, 0, getLocalDate("07/02/15"));
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

    }

    @Test
    public void sixthTest(){
        try {
            checkout.generateRentalAgreement("JAKR", 4, 50, getLocalDate("07/02/20"));
        }catch (CheckoutException e){
            fail("Unexpected checkout exception thrown: " + e.getMessage());
        }

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