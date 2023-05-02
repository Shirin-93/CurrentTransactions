package org.yearup;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Ledger {
    Scanner scanner = new Scanner(System.in);

    public void run() throws IOException
    {
        displayHomeScreen();
        loadTransactions();
        displayLedgerScreen();

    }

    public void displayHomeScreen() throws IOException {
        System.out.println(" \n\t\tHome Screen\t\t\n");
        System.out.println("What do you want to do?\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make a Payment");
        System.out.println("L) Display Ledger Screen");
        System.out.println("X) Exit\n");
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
            String[] record = {LocalDate.now().toString(), LocalTime.now().toString(), description, String.valueOf(amount)};
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
    }

    public void makePayment() throws IOException {
        FileWriter fileWriter = new FileWriter("target/transactions.csv", true); // append to file instead of overwriting
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
    }


    public void loadTransactions() {
        try {
            Scanner scanner = new Scanner(new File("target/transactions.csv"));


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void displayLedgerScreen() {

    }



}




