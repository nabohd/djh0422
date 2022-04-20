package com.company;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    @Test
    public void firstTest(){
        try {
            Checkout checkout = new Checkout();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            LocalDate date = LocalDate.parse("09/03/15", dateTimeFormatter);
            assertThrows(CheckoutException.class, () ->
                    checkout.generateRentalAgreement("JAKR", 5, 101, date));
        }catch (IOException | CsvValidationException e){
            fail("Was unable to read/parse the CSV files" + e);
        }
    }

    @Test
    public void secondTest(){

    }

    @Test
    public void thirdTest(){

    }

    @Test
    public void fourthTest(){

    }

    @Test
    public void fifthTest(){

    }

    @Test
    public void sixthTest(){

    }

}