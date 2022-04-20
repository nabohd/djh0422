package com.djh;


import com.djh.checkout.Checkout;
import com.djh.checkout.CheckoutException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 *  Main class that serves as an entry point to use / test the checkout object.
 */
public class Main {

    public static void main(String[] args) {
        Checkout checkout = null;
        try {
            checkout = new Checkout();
        } catch (IOException e) {
            System.out.println("Unable to open/read csv configuration file");
        } catch (CsvValidationException e) {
            System.out.println("Invalid csv configuration file");
        }

        //main input loop
        Scanner scan = new Scanner(System.in);
        boolean continueInput = true;
        while(checkout != null && continueInput) {
            try {
                System.out.println(checkout.commandLineInputRentalAgreement()); //print rental agreement
                System.out.println("Continue? y/n");
                String userContinue = scan.next();
                continueInput = userContinue.contains("y") || userContinue.contains("Y");
            } catch (InputMismatchException e) { //catch / handle certain exceptions
                System.out.println("Expected a whole number");
            } catch (CheckoutException e){
                System.out.println(e.getMessage());
            } catch (DateTimeParseException e){
                System.out.println("Input the date in MM/dd/yy format (Example: 04/03/22)");
            }
        }


    }




}
