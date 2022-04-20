package com.djh.checkout;

import com.djh.models.RentalAgreement;
import com.djh.models.Tool;
import com.djh.models.ToolType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The Checkout class reads the configuration CSV files, builds the Tool and ToolType models,
 * and stores the tools in a hashmap for quick lookup to build RentalAgreement models.
 *
 * Provides a method to collect simple textual input from a user.
 */
public class Checkout {

    //if this was being modified on the fly & multi-threaded singleton, use ConcurrentHashmap
    private HashMap<String, Tool> tools = new HashMap<>();

    public Checkout() throws CsvValidationException, IOException {
        this.readTools(); //populate the hashmap with values read from the CSV configuration files.
    }

    /**
     * Gathers user input from the command line and builds a RentalAgreement object.
     *
     * @throws CheckoutException -- this needs to be the actual exception
     * @return RentalAgreement generated from user input.
     */
    public RentalAgreement commandLineInputRentalAgreement() throws CheckoutException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the code: ");
        String code = scan.next();
        if(!this.tools.containsKey(code)){
            throw new CheckoutException("Code appears to be invalid. Try again");
        }
        System.out.println("Enter rental days: ");
        int rentalDays = scan.nextInt();
        if (rentalDays <= 1) {
            throw new CheckoutException("Rental days must be greater than 1");
        }
        System.out.println("Discount percent :");
        int discountPercent = scan.nextInt(); //non-int error
        if (discountPercent < 0 || discountPercent > 100) {
            throw new CheckoutException("Discount percent must be from 0 to 100");
        }
        System.out.println("Checkout date (mm/dd/yy): ");
        String checkoutDate = scan.next(); //date wrong, error
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate date = LocalDate.parse(checkoutDate, dateTimeFormatter);
        return new RentalAgreement(this.tools.get(code), date, discountPercent, rentalDays);
    }

    /**
     * Generates a rental agreement based on input.
     *
     * @param code            - Tool Code
     * @param days            - Number of rental dayas
     * @param discountPercent - Total discount percent
     * @param date            - Date of checkout
     * @return RentalAgreement containing the calculated values.
     */
    public RentalAgreement generateRentalAgreement(String code, Integer days,
                                                   Integer discountPercent, LocalDate date) throws CheckoutException {
        if(!this.tools.containsKey(code)){
            throw new CheckoutException("Code appears to be invalid. Try again");
        }
        if (days <= 1) {
            throw new CheckoutException("Rental days must be greater than 1");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new CheckoutException("Discount percent must be from 0 to 100");
        }
        return new RentalAgreement(this.tools.get(code), date, discountPercent, days);
    }

    /**
     * Reads in tools from tools.csv and toolType.csv on startup to initialize the tools HashMap
     *
     * instead of csv, items could be stored in db, and just cached in the hashmap.
     *
     * There is a foreign key relationship between Tool and ToolType
     */
    private void readTools() throws CsvValidationException, IOException {
        HashMap<String, ToolType> toolTypes = new HashMap<>(); //temporary lookup variable used for tools.
        File f = new File("src/main/resources/toolType.csv");
        //try with resources
        try (Reader reader = new FileReader(f); CSVReader csvReader = new CSVReader(reader)) {
            String[] readValues;
            while ((readValues = csvReader.readNext()) != null) {
                if (readValues[0].equalsIgnoreCase("toolType")) {
                    continue; //skip header
                }
                //  toolType,Daily Charge,Weekday Charge,Weekend Charge, Holiday Charge
                String type = readValues[0];
                BigDecimal dailyCharge = BigDecimal.valueOf(Double.parseDouble(readValues[1].replace("$", "")));
                boolean weekDayCharge = readValues[2].equals("Yes");
                boolean weekendCharge = readValues[3].equals("Yes");
                boolean holidayCharge = readValues[4].equals("Yes");
                ToolType toolType = new ToolType(type, dailyCharge, weekDayCharge, weekendCharge, holidayCharge);
                toolTypes.put(type, toolType);

            }

        }

        //Consider creating index identifiers
        //ie int CODE = 0, int BRAND = 2
        f = new File("src/main/resources/tools.csv");
        //try with resources
        try (Reader reader = new FileReader(f); CSVReader csvReader = new CSVReader(reader)) {
            String[] readValues;
            while ((readValues = csvReader.readNext()) != null) {
                //Tool Code,Tool Type,Brand
                if (readValues[0].equalsIgnoreCase("Tool Code")) {
                    continue; //skip header
                }
                String code = readValues[0];
                String brand = readValues[2];
                ToolType toolType = toolTypes.get(readValues[1]); //error if not in hashmap.
                Tool tool = new Tool(code, brand, toolType);
                this.tools.put(code, tool);
            }

        }

    }
}
