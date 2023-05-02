package org.yearup;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileReader;


public class Ledger {
    Scanner scanner = new Scanner(System.in);

    public void run() throws IOException
    {
        displayHomeScreen();
        //loadTransactions();
        displayLedgerScreen();

    }

    public void displayHomeScreen() throws IOException
    {
        System.out.println(" \n\t\tHome Screen\t\t\n");
        System.out.println("-------------------------------------------------------------");
        System.out.println("What do you want to do?\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make a Payment");
        System.out.println("L) Display Ledger Screen");
        System.out.println("X) Exit\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("Enter an option: ");

        String choice = scanner.nextLine().toUpperCase();

        switch (choice) {
            case "D":
                addDeposit();

            case "P":
                makePayment();

            case "L":
                displayLedgerScreen();

            case "X":
                System.out.println("\nExit..Saving all changes");
                return;
            default:
                System.out.println("Invalid option, Please try again");

        }
    }
    public void addDeposit() throws IOException {
        FileWriter fileWriter = new FileWriter("target/transactions.csv", true); // append to file instead of overwriting
        BufferedWriter writer = new BufferedWriter(fileWriter);

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the deposit amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Please enter the deposit description: ");
            String description = scanner.nextLine();

            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] record = {LocalDate.now().toString(), LocalTime.now().format(formatter), description, String.valueOf(amount)};
            writer.write(String.join("|", record));
            writer.newLine();
            System.out.println("Deposit added successfully");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        } catch (IOException e) {
            System.out.println("An error occurred while adding the deposit");
        } finally {
            writer.close();
        }
        displayHomeScreen();
    }
    public void makePayment() throws IOException {
        FileWriter fileWriter = new FileWriter("target/transactions.csv",true);                       // append to file instead of overwriting
        BufferedWriter writer = new BufferedWriter(fileWriter);
        double amount = 0;

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the payment amount: ");
            amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Please enter the payment description:");
            String description = scanner.nextLine();

            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] record = {LocalDate.now().toString(), LocalTime.now().format(formatter), description, String.valueOf(-amount)};
            writer.write(String.join("|", record));
            writer.newLine();
            System.out.println("Payment made successfully");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        } catch (IOException e) {
            System.out.println("An error occurred while making the payment: " + e.getMessage());
        } finally {
            writer.close();
        }
        displayHomeScreen();
    }
 //   public ArrayList<Trasaction>loadTransactions()


    public void displayLedgerScreen() throws IOException {
        System.out.println("\n\t\tLedger Screen\t\t\n");
        System.out.println("___________________________________________________");
        System.out.println("What do you want to do?");
        System.out.println("A) Display all entries");
        System.out.println("D) Display all deposit amount:");
        System.out.println("P) Display all payments done:");
        System.out.println("R) Display the reports:");
        System.out.println("H) Go back to home screen");
        System.out.println("---------------------------------------------------");
        System.out.println("Enter your option: ");

        String choice = scanner.nextLine().toUpperCase();

        switch(choice)
        {
            case "A":
                displayAllEntries();
            case "D":
                displayAllDeposit();
            case "P":
                displayAllPayments();
            case "R":
                displayTheReports();
            case "H":
                displayHomeScreen();
            default:
                System.out.println("Invalid option, Please try again");

        }
    }

    private void displayAllPayments() throws IOException
    {   //open the file stream
        FileReader fileReader = new FileReader("target/transactions.csv");
        // create a buffered reader
        BufferedReader reader = new BufferedReader(fileReader);
        //reader.readLine();

        String line = reader.readLine();
        while(line != null)
        {
            String[]columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            //create a new output with the values from the row
            Records record = new Records(date,time,description,vendor,amount);

            //use the record
            System.out.println(record.getDate() +"  "+record.getTime()+"  "+record.getDescription()+"  "+record.getVendor()+"  $"+record.getAmount());
            break;
        }
        reader.close();
    }


    public void displayAllDeposit()
    {
        ArrayList<Records> records = new ArrayList<>();
        // load the array
        FileInputStream stream;
        Scanner fileScanner = null;
        try
        {
            stream = new FileInputStream("target/transactions.csv");
            fileScanner = new Scanner(stream);
            fileScanner.nextLine();

            while(fileScanner.hasNextLine())
            {
                 String line = fileScanner.nextLine();
             //split line into fields
                String[] columns = line.split("\\|");
                LocalDate date = LocalDate.parse(columns[0]);
                LocalTime time = LocalTime.parse(columns[1]);
                String description = columns[2];
                String vendor = columns[3];
                double amount = Double.parseDouble(columns[4]);

                //create ArrayList
                Records record = new Records(date,time,description,vendor,amount);
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("There was an error loading the file");
        }
        finally
        {
            if(fileScanner !=null)
            {
                fileScanner.close();
            }
        }
    }

    private void displayAllEntries()
    {
    }
    private void displayTheReports()
    {
    }


}




