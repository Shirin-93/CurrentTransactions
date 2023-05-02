package org.yearup;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Scanner;
//
//public class HomeScreen {
//    public void addDeposit() throws IOException {
//        FileReader filereader = new FileReader("transactions.csv");
//        BufferedReader reader = new BufferedReader(filereader);
//
//        reader.readLine();
//
//        //read each line
//        String line = reader.readLine();
//        while (line != null) {
//            String[] columns = line.split("\\|");
//
//            //create variable
//            LocalDate date = LocalDate.parse(columns[0]);
//            LocalTime time = LocalTime.parse(columns[1]);
//            String description = columns[2];
//            String vendor = columns[3];
//            double amount = Double.parseDouble(columns[4]);
//        }
//        try {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Please enter the deposit amount: ");
//            double amount = scanner.nextDouble();
//            scanner.nextLine();
//
//            System.out.println("Please enter the deposit description: ");
//            String description = scanner.nextLine();
//
//            String[] record = {" ", String.valueOf(amount), description};
//            reader.readLine();
//            System.out.println("Deposit added successfully");
//
//        } catch (IOException e) {
//            System.out.println("An error occurred while adding the deposit ");
//        }
//
//    }
//
//    public void makePayment() throws IOException {
//        FileReader filereader = new FileReader("transactions.csv");
//        BufferedReader reader = new BufferedReader(filereader);
//
//        reader.readLine();
//
//        //read each line
//        String line = reader.readLine();
//        while (line != null)
//            try {
//                //Writer writer = new Writer(new FileWriter("transactions.csv"));
//                Scanner scanner = new Scanner(System.in);
//                System.out.println("Please enter the payment amount: ");
//                double amount = scanner.nextDouble();
//                scanner.nextLine();
//
//                System.out.println("Please enter the payment description:");
//                String description = scanner.nextLine();
//
//                String[] record = {" ", String.valueOf(-amount), description};
//                line = reader.readLine();
//                System.out.println("Payment made successfully");
//            } catch (IOException e) {
//                System.out.println("An error occured while making the payment:" + e.getMessage());
//            }
//
//    }


