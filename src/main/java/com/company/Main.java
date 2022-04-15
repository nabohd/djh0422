package com.company;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    //need to ask if the tool instance is unique for 1 tool or multiple tools.

    //store tools for easy lookup by code. HashMaps act as cache.
    //I think things here should go into a cache object
    private HashMap<String, Tool> checkedInTools = new HashMap<>();
    //checked out tools with rental agreement, lookup by code.
    private HashMap<String, RentalAgreement> checkedOutTools = new HashMap<>();
    //store toolType
    private HashMap<String, ToolType> toolTypes = new HashMap<>();

    public static void main(String[] args) {

        //move most of this logic to Checkout.

        try {
            Main main = new Main();
            main.initialize();
            Scanner scan = new Scanner(System.in);
            while(true){ //add input validation here
                System.out.println("Enter the code: ");
                String code = scan.next();
                System.out.println("Enter rental days: ");
                Integer rentalDays = scan.nextInt(); //non-int error -- catch exception, print help, and then restart loop
                System.out.println("Discount percent :" );
                Integer discountPercent = scan.nextInt(); //non-int error
                System.out.println("Checkout date: ");
                String checkoutDate = scan.next(); //date wrong, error
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate.parse(checkoutDate, dateTimeFormatter);
            }


        }  catch (IOException e) { //fix / move exception handling.
            System.out.println("Unable to open configuration file");


        } catch (CsvValidationException e){
            System.out.println("Invalid csv configuration file");

        }


    }

    /**
     * Read in tools from CSV on startup to initialize the HashMap
     *
     * instead of csv, items could be stored in db, and just cached in the hashmap or something, depending on
     * item id the user requests.
     */
    private void initialize() throws CsvValidationException, IOException {
        File f = new File("src/main/resources/toolType.csv");
        Reader reader = new FileReader(f); //close these

        //try with resources
        try(CSVReader csvReader = new CSVReader(reader)){
            String[] readValues;
            while((readValues = csvReader.readNext())!=null){
                //Tool tool = new Tool()
                System.out.println(readValues[0]); //remove $ convert to double
                //convert yes/no to boolean.
            }

        }


//        File f = new File("src/main/resources/tools.csv");
//        Reader reader = new FileReader(f); //close these
//
//        //try with resources
//        try(CSVReader csvReader = new CSVReader(reader)){
//            String[] readValues = null;
//            while((readValues = csvReader.readNext())!=null){
//                //Tool tool = new Tool()
//                //System.out.println(readValues[0]);
//            }
//
//        }

    }


}
