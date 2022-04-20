package com.company;


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

public class Checkout {

    private HashMap<String, Tool> tools = new HashMap<>();

    public Checkout() throws CsvValidationException, IOException {
        this.readTools();
    }

    /**
     * Gathers user input
     *
     * @throws CheckoutException -- this needs to be the actual exception
     */
    public void gatherUserInput() throws CheckoutException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the code: ");
        String code = scan.next();
        System.out.println("Enter rental days: ");
        Integer rentalDays = scan.nextInt(); //non-int error -- catch exception, print help, and then restart loop
        System.out.println("Discount percent :");
        Integer discountPercent = scan.nextInt(); //non-int error
        System.out.println("Checkout date (mm/dd/yy): ");
        String checkoutDate = scan.next(); //date wrong, error
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate date = LocalDate.parse(checkoutDate, dateTimeFormatter);
        System.out.println(this.generateRentalAgreement(code, rentalDays, discountPercent, date));

    }

    /**
     * Generates a rental agreement based on input from the UI
     *
     * @param code            - Tool Code
     * @param days            - Number of rental dayas
     * @param discountPercent - Total discount percent
     * @param date            - Date of checkout
     */
    public RentalAgreement generateRentalAgreement(String code, Integer days,
                                                   Integer discountPercent, LocalDate date) throws CheckoutException {
        if (days <= 1) {
            throw new CheckoutException("Rental days must be greater than 1");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new CheckoutException("Discount percent must be from 0 to 100");
        }

        return new RentalAgreement(this.tools.get(code), date, discountPercent, days);
    }

    /**
     * Reads in tools from CSV on startup to initialize the tools HashMap
     * <p>
     * instead of csv, items could be stored in db, and just cached in the hashmap.
     * <p>
     * There is a foreign key relationship between Tool and ToolType
     */
    private void readTools() throws CsvValidationException, IOException {
        HashMap<String, ToolType> toolTypes = new HashMap<>();
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
