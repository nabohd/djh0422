package com.company;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


public class Main {

    //need to ask if the tool instance is unique for 1 tool or multiple tools.

    //store tools for easy lookup by code.
    private HashMap<String, Tool> checkedInTools = new HashMap<>();
    //checked out tools with rental agreement, lookup by code.
    private HashMap<String, RentalAgreement> checkedOutTools = new HashMap<>();
    //store toolType
    private HashMap<String, ToolType> toolTypes = new HashMap<>();

    public static void main(String[] args) {

        try {
            Main main = new Main();
            main.initialize();


        } catch (URISyntaxException e) {

        } catch (IOException e) {

        }


    }

    /**
     * Read in tools from CSV on startup to initialize the HashMap
     */
    private void initialize() throws URISyntaxException, IOException {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("csv/twoColumn.csv").toURI()));
    }


}
