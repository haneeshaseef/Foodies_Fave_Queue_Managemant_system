package com.example.courseWorkSubmission01;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FoodCenterProgram {
    private static final String[] firstQueue = new String[2];
    private static final String[] secondQueue = new String[3];
    private static final String[] thirdQueue = new String[5];
    // Reference for circular queue with arrays :https://www.geeksforgeeks.org/introduction-to-circular-queue/
    private static int firstQueueFront = 0;
    private static int secondQueueFront = 0 ;
    private static int  thirdQueueFront = 0;

    private static int firstQueueRear= -1;
    private static int secondQueueRear =-1;
    private static int thirdQueueRear = -1;

    private static int firstQueueSize = 0;
    private static int secondQueueSize = 0;
    private static int thirdQueueSize = 0;

    private static final int totalBurgerStock = 50;
    private static int currentBurgerStock = 50;

    private static final String[][] CashierQueue = {firstQueue,secondQueue,thirdQueue};

    public static boolean isArrayFull(int actualIndex){
        return switch (actualIndex) {
            case 0 -> firstQueueSize == firstQueue.length;
            case 1 -> secondQueueSize == secondQueue.length;
            default -> thirdQueueSize == thirdQueue.length;
        };
    }

    public static boolean isArrayEmpty(int actualId) {
        return switch (actualId) {
            case 0 -> firstQueueSize == 0;
            case 1 -> secondQueueSize == 0;
            default -> thirdQueueSize == 0;
        };
    }
    public static String removeCustomer(int actualIndex ,int actualCustomerIndex) {
        // check if the actual index is valid
        if (actualIndex >= 0 && actualIndex < CashierQueue.length) {
            // declare a variable to store the customer name
            String customerName;
            // use switch case to handle different cases of actual index
            switch (actualIndex) {
                case 0 -> {
                    // get the customer name from the first queue
                    customerName = firstQueue[actualCustomerIndex];
                    firstQueue[actualCustomerIndex] = null;
                    // update the front index of the queue
                    firstQueueFront = (firstQueueFront + 1) % firstQueue.length;
                    firstQueueSize--;
                }
                case 1 -> {
                    // get the customer name from the second queue
                    customerName = secondQueue[actualCustomerIndex];
                    secondQueue[actualCustomerIndex] = null;
                    // update the front index of the queue
                    secondQueueFront = (secondQueueFront + 1) % secondQueue.length;
                    secondQueueSize--;
                }
                default -> {
                    // get the customer name from the third queue
                    customerName = thirdQueue[actualCustomerIndex];
                    thirdQueue[actualCustomerIndex] = null;
                    // update the front index of the queue
                    thirdQueueFront = (thirdQueueFront + 1) % thirdQueue.length;
                    thirdQueueSize--;
                }
            }
            return customerName;
        }
        // return null if the actual index is invalid
        return null;
    }

    public static String removeServedCustomer(int actualId){
        // Check if the queue at the actual id is not empty
        if(!isArrayEmpty(actualId)){
            String customerName;
            // Use a switch-case statement to handle different cases of actual index
            switch (actualId) {
                case 0 -> {
                    customerName = firstQueue[firstQueueFront];
                    firstQueue[firstQueueFront] = null;
                    // Increment the front index of the first queue by 1 modulo the queue length
                    firstQueueFront = (firstQueueFront + 1) % firstQueue.length;
                    firstQueueSize--;
                }
                case 1 -> {
                    customerName = secondQueue[secondQueueFront];
                    secondQueue[secondQueueFront] = null;
                    // Increment the front index of the first queue by 1 modulo the queue length
                    secondQueueFront = (secondQueueFront + 1) % secondQueue.length;
                    secondQueueSize--;
                }
                default -> {
                    customerName = thirdQueue[thirdQueueFront];
                    thirdQueue[thirdQueueFront] = null;
                    // Increment the front index of the first queue by 1 modulo the queue length
                    thirdQueueFront = (thirdQueueFront + 1) % thirdQueue.length;
                    thirdQueueSize--;
                }
            }
            return customerName;
        }
        // return null if the actual index is invalid
        return null;
    }

    public static boolean addCustomer(int actualIndex, String customerName) {
        // Check if the queue at the actual index is not full
        if (!isArrayFull(actualIndex)){
            // Use a switch-case statement to handle different cases of actual index
            switch (actualIndex) {
                case 0 -> { // If the actual index is 0, add the customer to the first queue
                    // Increment the rear index of the first queue by 1 modulo the queue length
                    firstQueueRear = (firstQueueRear + 1) % firstQueue.length;
                    firstQueue[firstQueueRear] = customerName;
                    firstQueueSize++;
                    return true;
                }
                case 1 -> { // If the actual index is 1, add the customer to the second queue
                    // Increment the rear index of the second queue by 1 modulo the queue length
                    secondQueueRear = (secondQueueRear + 1) % secondQueue.length;
                    secondQueue[secondQueueRear] = customerName;
                    secondQueueSize++;
                    return true;
                }
                case 2 -> { // If the actual index is 2, add the customer to the third queue
                    // Increment the rear index of the third queue by 1 modulo the queue length
                    thirdQueueRear = (thirdQueueRear + 1) % thirdQueue.length;
                    thirdQueue[thirdQueueRear] = customerName;
                    thirdQueueSize++;
                    return true;
                }
                default -> { // If the actual index is invalid, return false to indicate failure
                    return false;
                }
            }
        }
        // If the queue at the actual index is full, return false to indicate failure
        return false;
    }

    private static void viewAllQueues() {
        System.out.println("*".repeat(18) + "\n" + "**   Cashiers   **" + "\n" + "*".repeat(18));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String[] cashier = CashierQueue[j];
                if (cashier.length > i) {
                    if(cashier[i] != null && !cashier[i].isEmpty()) {
                        System.out.print("O" + "\t \t");
                    }
                    else{
                        System.out.print("X" + "\t \t");
                    }
                }else{
                    System.out.print("\t \t");
                }
            }
            System.out.println(" ");
        }
        System.out.println("\n" + "X – Not Occupied    O – Occupied ");
    }

    private static void viewAllEmptyQueues() {
        System.out.println("Empty Queues:");
        int index = 0;
        int nullCount = 0;
        while (index < CashierQueue.length) {
            // Loop through each cashier queue
            for (String[] cashier : CashierQueue) {
                // Loop through each customer in the cashier queue
                for (String queueElement : cashier) {
                    if (queueElement == null) {
                        nullCount++;
                    }
                }
                index++;
                if (nullCount > 0)
                    System.out.println("Cashier " + (index));
            }
        }
    }

    private static void addCustomerToQueue(int cashierIndex,String customerName) {
        // Subtract 1 from the cashier index to get the actual array index
        int actualIndex = cashierIndex -1;
        // Check if the actual index is within the range of the CashierQueue array
        if (actualIndex >= 0 && actualIndex < CashierQueue.length){
            if (addCustomer(actualIndex, customerName)){
                System.out.println("Customer '" + customerName + "' added to Cashier " + (cashierIndex) + " queue.");
            } else {
                System.out.println("Cashier " + (cashierIndex) + " queue is full.");
            }
        } else {
            System.out.println("Invalid cashier index.");
        }
    }

    private static void removeCustomerFromQueue(int cashierIdx, int customerIdx) {
        // Subtract 1 from the cashierIdx to get the actual array index
        int actualIndex = cashierIdx -1;
        // Check if the actual index is within the range of the CashierQueue array
        int actualCustomerIndex = customerIdx -1;
        if (actualIndex >= 0 && actualIndex < CashierQueue.length){
            String customerName = removeCustomer(actualIndex ,actualCustomerIndex);
            if(customerName != null){
                System.out.println("Customer '" + customerName + "' removed from Cashier " + (cashierIdx) + " queue.");
            }else{
                System.out.println("Cashier " + (cashierIdx) + " position is empty.");
            }
        }else{
            System.out.println("Invalid cashier index.");
        }

    }

    private static void removeServedCustomerFromQueue(int cashierId) {
        // Subtract 1 from the cashierId to get the actual array index
        int actualIndex = cashierId -1;
        // Check if the actual index is within the range of the CashierQueue array
        if (actualIndex >= 0 && actualIndex < CashierQueue.length){
            String customerName =removeServedCustomer(actualIndex);
            if(customerName != null ){
                currentBurgerStock -= 5;
                if (currentBurgerStock <= 10) {
                    System.out.println("Warning: Low stock!");
                }
                System.out.println("Customer '" + customerName + "' served and removed from a queue.");
            }else
                System.out.println("No customers to serve.");
        }
    }

    private static void viewCustomersSorted() {
        String[] allCustomers = new String[firstQueueSize + secondQueueSize + thirdQueueSize];
        int index = 0;
        // Loop through each cashier queue
        for (String[] cashier: CashierQueue) {
            // Loop through each customer in the cashier queue
            for (String customer : cashier) {
                if (!(customer == null)) {
                    allCustomers[index] = customer;
                    index++;
                }
            }
        }
        // Sort the array using insertion sort algorithm
        // Reference :https://www.javatpoint.com/insertion-sort-in-java
        for (int i = 1; i < allCustomers.length; i++) {
            String key = allCustomers[i];
            int j = i - 1;

            while (j >= 0 && allCustomers[j].compareTo(key) > 0) {
                allCustomers[j + 1] = allCustomers[j];
                j--;
            }
            allCustomers[j + 1] = key;
        }
        for (String customer :allCustomers) {
            System.out.println(customer);
        }

    }
    // Reference 01 for file handling : https://www.w3schools.com/java/java_files.asp
    // Reference 02 for file handling :https://docs.oracle.com/javase/tutorial/essential/io/file.html
    private static void storeProgramData() {
        try{
            FileWriter fileWriter = new FileWriter("foodCenterProgramData.txt");
            int lineCount = 1;
            // Loop through each cashier queue
            for (String[] cashier: CashierQueue) {
                fileWriter.write("Cashier queue: " + lineCount);
                for (String customer : cashier) {
                    if (!(customer == null)) {
                        fileWriter.write(" "+customer + ", ");
                    }
                }
                lineCount++;
                fileWriter.write("\n");
            }

            fileWriter.close();

            System.out.println("Stored all the data successfully.......");

        } catch (IOException exception){
            System.out.println("Something went wrong with storing program data!");
        }
    }
    private static void loadProgramData() {
        try{
            File fileInput = new File("foodCenterProgramData.txt");
            //Reading file with scanner object
            Scanner readFile = new Scanner(fileInput);

            String fileLine;
            while (readFile.hasNext()){
                fileLine = readFile.nextLine();
                System.out.println( fileLine);

            }
            readFile.close();
        }
        catch (Exception exception){
            System.out.println("Something went wrong with loading program data!");
        }
    }

    private static void viewRemainingBurgerStock() {
        System.out.println("Remaining burgers in stock: " + (currentBurgerStock));
        if (currentBurgerStock <= 10) {
            System.out.println("Warning: Low stock!");
        }
    }

    private static void addBurgersToStock(int quantity) {
        currentBurgerStock += quantity;
        System.out.println(quantity + " burgers added to stock.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("-".repeat(35));
            System.out.println("Welcome to Foodies Fave Food Center");
            System.out.println("-".repeat(35));
            System.out.println("*".repeat(26) + "\n" + "****   Menu Options   ****" + "\n" + "*".repeat(26));
            System.out.println("""
                100 or VAQ: view all Queues.
                101 or VEQ: View all Empty Queues.
                102 or ACQ: Add customer to a Queue.
                103 or RCQ: Remove a customer from a Queue.
                104 or PCQ: Remove a served customer.
                105 or VCS: View Customers Sorted in alphabetical order
                106 or SPD: Store Program Data into file.
                107 or LPD: Load Program Data from file.
                108 or STK: View Remaining burgers Stock.
                109 or AFS: Add burgers to Stock.
                999 or EXT: Exit the Program.
                """);
            System.out.println("*".repeat(26));
            System.out.print("Enter your choice here: ");
            String choice = scanner.next().toUpperCase();
            scanner.nextLine();

            switch (choice) {
                case "100", "VFQ" -> viewAllQueues();
                case "101", "VEQ" -> viewAllEmptyQueues();
                case "102", "ACQ" -> {
                    System.out.println("Enter the cashier queue number (1-3): ");
                    try {
                        int cashierIndex = scanner.nextInt();
                        scanner.nextLine();// Consume the newline character
                        if (cashierIndex >= 0 && cashierIndex <= 4) {
                            System.out.println("Enter your customer name: ");
                            String customerName = scanner.next().toLowerCase();
                            scanner.nextLine();// Consume the newline character
                            // Reference 01 :https://www.geeksforgeeks.org/how-to-validate-a-username-using-regular-expressions-in-java/
                            // Reference 02 : https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters
                            // regular expression to validate the customer name
                            String regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0}$"; // Unicode-based regex
                            // String regex = "^[A-Za-z\\s]+[\\.\\-\\']{0,1}[A-Za-z\\s]*$"; // ASCII-based regex
                            if (customerName.matches(regex)) {  // Check if the actual index is within the range of the CashierQueue array
                                addCustomerToQueue(cashierIndex, customerName);
                            } else {
                                System.out.println("Invalid input. Please enter a valid customer name.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a cashier index between 1 and 3.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a numeric value for the cashier index.");
                        scanner.nextLine(); // clear the buffer
                    }
                }
                case "103", "RCQ" -> {
                    System.out.print("Enter the cashier queue number (1-3): ");
                    try {
                        int cashierIdx = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        if (cashierIdx >= 1 && cashierIdx <= 3) {
                            System.out.print("Enter the customer position in the queue:");
                            try {
                                int customerIdx = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character
                                if (customerIdx >= 0 && customerIdx <= 5) {
                                    removeCustomerFromQueue(cashierIdx, customerIdx);
                                } else {
                                    System.out.println("Invalid input. Please enter a valid customer position in the queue.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a numeric value for the customer index.");
                                scanner.nextLine(); // clear the buffer
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a cashier index between 1 and 3.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a numeric value for the cashier index.");
                        scanner.nextLine(); // clear the buffer
                    }
                }
                case "104", "PCQ" -> {
                    System.out.print("Enter the cashier queue number (1-3): ");
                    try {
                        int cashierId = scanner.nextInt();
                        if (cashierId >= 1 && cashierId <= 3) {
                            removeServedCustomerFromQueue(cashierId);
                        } else {
                            System.out.println("Invalid input. Please enter a cashier index between 1 and 3.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a numeric value for the cashier index.");
                        scanner.nextLine(); // clear the buffer
                    }
                }
                case "105", "VCS" -> viewCustomersSorted();
                case "106", "SPD" -> storeProgramData();
                case "107", "LPD" -> loadProgramData();
                case "108", "STK" -> viewRemainingBurgerStock();
                case "109", "AFS" -> {
                    try {
                        int burgerStock = totalBurgerStock - currentBurgerStock;
                        if (currentBurgerStock < 50) {
                            System.out.println("There are " + (burgerStock) + " empty spaces to store burgers");
                            System.out.print("Enter the quantity of burgers to add: ");
                            int quantity = scanner.nextInt();
                            if (quantity <= burgerStock) {
                                addBurgersToStock(quantity);
                            } else {
                                System.out.println("Enter a valid burger count between 0 to " + burgerStock);
                            }

                        } else {
                            System.out.println("The burgers stock is full !");
                        }
                    } catch (Exception exception) {
                        System.out.println("Something went Wrong with adding burgers");
                    }
                }
                case "999", "EXT" -> {
                    scanner.close();
                    System.out.println("Exiting the program...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

}
