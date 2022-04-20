package com.company;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        Checkout checkout = null;
        try {
            checkout = new Checkout();
        } catch (IOException e) { //fix / move exception handling.
            System.out.println("Unable to open configuration file");
        } catch (CsvValidationException e) {
            System.out.println("Invalid csv configuration file");
        }

        while(checkout != null) {
            try {
                checkout.gatherUserInput();
            } catch (Exception e) {
                System.out.println(e); //temp
            }
        }


    }




}
