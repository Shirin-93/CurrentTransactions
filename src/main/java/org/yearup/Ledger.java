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
        displayLedgerScreen();

    }
    public void displayHomeScreen() throws IOException
    {
        System.out.println(ColorCodes.RED+"\n\t\tHome Screen\t\t\n"+ColorCodes.RESET);
        System.out.println("-------------------------------------------------------------");
        System.out.println("What do you want to do?\n");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make a Payment");
        System.out.println("L) Display Ledger Screen");
        System.out.println("X) Exit\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println(ColorCodes.GREEN+"Enter an option: "+ColorCodes.RESET);

        String choice = scanner.nextLine().toUpperCase();

        switch (choice) {
            case "D":
                addDeposit();
                break;

            case "P":
                makePayment();
                break;

            case "L":
                displayLedgerScreen();
                break;

            case "X":
                System.out.println("\nExit..Saving all changes");
                break;
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

            System.out.println("Please enter the vendor:");
            String vendor = scanner.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] record = {LocalDate.now().toString(), LocalTime.now().format(formatter), description, vendor, String.valueOf(amount)};
            writer.write(String.join("|", record));
            writer.newLine();
            System.out.println(ColorCodes.RED+"Deposit added successfully"+ColorCodes.RESET);

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

            System.out.println("Please enter the vendor:");
            String vendor = scanner.nextLine();

            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] record = {LocalDate.now().toString(), LocalTime.now().format(formatter), description, vendor,String.valueOf(-amount)};
            writer.write(String.join("|", record));
            writer.newLine();
            System.out.println(ColorCodes.RED+"Payment made successfully"+ColorCodes.RESET);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
        } catch (IOException e) {
            System.out.println("An error occurred while making the payment: " + e.getMessage());
        } finally {
            writer.close();
        }
        displayHomeScreen();
    }
    //   public ArrayList<Transaction>loadTransactions()


    public void displayLedgerScreen() throws IOException {
        System.out.println(ColorCodes.RED+"\n\t\tLedger Screen\t\t\n"+ColorCodes.RESET);
        System.out.println("___________________________________________________");
        System.out.println("What do you want to do?");
        System.out.println("A) Display all entries");
        System.out.println("D) Display all deposit amount:");
        System.out.println("P) Display all payments done:");
        System.out.println("R) Display the reports:");
        System.out.println("H) Go back to home screen");
        System.out.println("---------------------------------------------------");
        System.out.println(ColorCodes.GREEN+"Enter your option: "+ColorCodes.RESET);

        String choice = scanner.nextLine().toUpperCase();

        switch(choice)
        {
            case "A":
                displayAllEntries();
                break;
            case "D":
                displayAllDeposit();
                break;
            case "P":
                displayAllPayments();
                break;
            case "R":
                displayTheReports();
                break;
            case "H":
                displayHomeScreen();
                break;
            default:
                System.out.println("Invalid option, Please try again");

        }
    }

    public void displayAllEntries() throws IOException {
        //open the file stream
        FileReader fileReader = new FileReader("target/transactions.csv");
        // create a buffered reader
        BufferedReader reader = new BufferedReader(fileReader);
        System.out.println(ColorCodes.CYAN+"\t\t\tDISPLAYING ALL ENTRIES:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while (line != null) {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);
            //create a new output with the values from the row
            Records record = new Records(date, time, description, vendor, amount);

            //display all entries
            System.out.printf(ColorCodes.RED+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());

            line = reader.readLine();

        }
        reader.close();
        displayLedgerScreen();
    }

    public void displayAllDeposit() throws IOException {
        //open the file stream
        FileReader fileReader = new FileReader("target/transactions.csv");
        // create a buffered reader
        BufferedReader reader = new BufferedReader(fileReader);
        System.out.println(ColorCodes.CYAN+"\t\tDisplaying Your Deposit Reports:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while (line != null) {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            //create a new output with the values from the row
            Records record = new Records(date, time, description, vendor, amount);

            //check if payment is a deposit and display the details
            if (record.getAmount() > 0) {
                System.out.printf(ColorCodes.RED+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
            }

            line = reader.readLine();

        }
        reader.close();
        displayLedgerScreen();
    }

    private void displayAllPayments() throws IOException {   //open the file stream
        FileReader fileReader = new FileReader("target/transactions.csv");
        // create a buffered reader
        BufferedReader reader = new BufferedReader(fileReader);
        //reader.readLine();
        System.out.println(ColorCodes.CYAN+"\t\tYour Payments:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while(line != null)
        {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            //create a new output with the values from the row
            Records record = new Records(date, time, description, vendor, amount);
            //check if it is a payment and display the details
            if (record.getAmount() <= 0) {
                System.out.printf(ColorCodes.RED+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
            }

            line = reader.readLine();
            //use the record
            // System.out.println(record.getDate() +"  "+record.getTime()+"  "+record.getDescription()+"  "+record.getVendor()+"  $"+record.getAmount());

        }
        reader.close();
        displayLedgerScreen();
    }


    private void displayTheReports() throws IOException {
        System.out.println(ColorCodes.PURPLE+"\n\t\tReports\t\t"+ColorCodes.RESET);
        System.out.println("--------------------------------------------");
        System.out.println("1)Display month to date reports:");
        System.out.println("2)Display Previous month report");
        System.out.println("3)Display Year to date reports");
        System.out.println("4)Display previous year reports");
        System.out.println("5)Search by vendor");
        System.out.println("0)Go back to report page");
        System.out.println("---------------------------------------------");
        System.out.println(ColorCodes.GREEN+"Enter your option:"+ColorCodes.RESET);

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                monthToDate();
                break;
            case 2:
                previousMonth();
                break;
            case 3:
                yearToDate();
                break;
            case 4:
                previousYear();
                break;
            case 5:
                searchByVendor();
                break;
            default:
                System.out.println("Invalid option. Please try again");
        }
    }


    private void monthToDate() throws IOException {// Get the current month and year
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        // Open the file stream
        FileReader fileReader = new FileReader("target/transactions.csv");
        // Create a buffered reader
        BufferedReader reader = new BufferedReader(fileReader);
        System.out.println(ColorCodes.PURPLE+"\t\tMonth to Date Transactions:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while (line != null) {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            // Check if the transaction occurred in the current month and year
            if (date.getMonthValue() == currentMonth && date.getYear() == currentYear) {
                // Create a new output with the values from the row
                Records record = new Records(date, time, description, vendor, amount);

                // Display the transaction
                System.out.printf(ColorCodes.CYAN+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
            }

            line = reader.readLine();
        }
        reader.close();
        displayTheReports();
    }

    private void previousMonth() throws IOException
    {   FileReader fileReader = new FileReader("target/transactions.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int previousMonth = currentMonth -1;
        // Subtract one month to get the date for the previous month
        //LocalDate previousMonthDate = currentDate.minusMonths(1);
        System.out.println(ColorCodes.PURPLE+"\t\tPrevious Month Transactions:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while (line != null) 
        {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            //loop through the csv file to check for month
            if (date.getMonthValue() == previousMonth && date.getYear() == currentYear)
            {
                Records record = new Records(date, time, description, vendor, amount);
                // Display the transaction
                System.out.printf(ColorCodes.CYAN+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
            }
            line = reader.readLine();
        }reader.close();
        displayTheReports();
    }

    private void yearToDate() throws IOException
    {
        FileReader fileReader = new FileReader("target/transactions.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        // Get the current year
        int currentYear = LocalDate.now().getYear();

        System.out.println(ColorCodes.PURPLE+"\t\tYear to Date Transactions:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while (line != null)
        {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            //loop through the csv file to check for month
            if (date.getYear() == currentYear && date.getDayOfYear() <=LocalDate.now().getDayOfYear())
            {
                Records record = new Records(date, time, description, vendor, amount);
                // Display the transaction
                System.out.printf(ColorCodes.CYAN+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
            }
            line = reader.readLine();
        }
        reader.close();
        displayTheReports();
    }


    private void previousYear() throws IOException {
        FileReader fileReader = new FileReader("target/transactions.csv");
        BufferedReader reader = new BufferedReader(fileReader);
        // Subtract one year to get the date for the previous year
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int previousYear = currentYear -1;
        System.out.println(ColorCodes.PURPLE+"\t\tPrevious Year Transactions:\n"+ColorCodes.RESET);
        String line = reader.readLine();
        while (line != null)
        {
            String[] columns = line.split("\\|");
            //create variable for each column
            LocalDate date = LocalDate.parse(columns[0]);
            LocalTime time = LocalTime.parse(columns[1]);
            String description = columns[2];
            String vendor = columns[3];
            double amount = Double.parseDouble(columns[4]);

            if(date.getYear()== previousYear)
            {
                Records record = new Records(date, time, description, vendor, amount);
                // Display the transaction

                System.out.printf(ColorCodes.CYAN+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
            }
            line = reader.readLine();
        }reader.close();
        displayTheReports();
    }

    private void searchByVendor() throws IOException {
        System.out.println("Enter the vendor name:");
        String vendorSearch = scanner.nextLine();
        //open the file stream
        FileReader fileReader = new FileReader("target/transactions.csv");
        // create a buffered reader
        BufferedReader reader = new BufferedReader(fileReader);
        System.out.println(ColorCodes.PURPLE+"\t\tTransaction with your vendor:\n"+ColorCodes.RESET);
        String line = null;
        try {
            line = reader.readLine();
            while (line != null) {
                String[] columns = line.split("\\|");
                LocalDate date = LocalDate.parse(columns[0]);
                LocalTime time = LocalTime.parse(columns[1]);
                String description = columns[2];
                String vendor = columns[3];
                double amount = Double.parseDouble(columns[4]);

                if (vendor.equals(vendorSearch)) {
                    Records record = new Records(date, time, description, vendor, amount);
                    System.out.printf(ColorCodes.GREEN+"%-10s %-10s %-20s %-20s %.2f\n"+ColorCodes.RESET, record.getDate(), record.getTime(), record.getDescription(), record.getVendor(), record.getAmount());
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        displayTheReports();

    }


}




