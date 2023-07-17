package com.example.courseWorkSubmission02;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FoodCenter {
    private static List<FoodQueue> queues;
    private static final FoodQueue waitingQueue = new FoodQueue(10);
    public int totalBurgerStock = 50;
    public int currentBurgerStock;
    private int firstQueueIncome;
    private int secondQueueIncome;
    private int thirdQueueIncome;

    public FoodCenter() {
        // Create an ArrayList that contains three FoodQueues of different sizes
        queues = new ArrayList<>(Arrays.asList(new FoodQueue(2), new FoodQueue(3), new FoodQueue(5)));
        this.currentBurgerStock = 50;
    }

    public void viewAllQueues() {
        System.out.println("*".repeat(18) + "\n" + "**   Cashiers   **" + "\n" + "*".repeat(18));
        // Loop through each row of seats
        for (int i = 0; i < 5; i++) {
            StringBuilder row = new StringBuilder();
            // Loop through each queue
            for (FoodQueue queue : queues) {
                int occupiedSlots = queue.getCustomers().size();
                // Check if the current seat is occupied or empty or out of bounds
                if (i < occupiedSlots) {
                    row.append("O \t\t");
                } else if (i < queue.getMaxCapacity()) {
                    row.append("X \t\t");
                } else {
                    row.append("  \t\t");
                }
            }
            if (i == 2)
                System.out.println(" \t\t".repeat(i - 1) + row.toString().trim());
            else if (i == 3 || i == 4) {
                System.out.println(" \t\t".repeat(2) + row.toString().trim());
            } else {
                System.out.println(row.toString().trim());
            }
        }
        System.out.println("\n" + "X – Not Occupied    O – Occupied ");
    }

    public void viewAllEmptyQueues() {
        System.out.println("\n"+"*".repeat(22) + "\n" + "**   Empty Queues   **" + "\n" + "*".repeat(22)+"\n");
        for (int i = 0; i < queues.size(); i++) {
            FoodQueue queue = queues.get(i);
            if (queue.getCustomers().size() < queue.getMaxCapacity()) {
                System.out.println("Queue " + (i + 1) + " has " +(queue.getMaxCapacity() - queue.getCustomers().size()) +" empty slots.");
            }
        }
    }

    public void addCustomerToQueue(String firstName, String lastName, int burgersRequired) {
        Customer customer = new Customer(firstName, lastName, burgersRequired);
        // Declare a variable to store the index of the shortest queue
        int minQueueIndex = -1;
        // Declare a variable to store the length of the shortest queue
        // Initialize it with the maximum possible integer value
        int minQueueLength = Integer.MAX_VALUE;

        // Loop through the list of queues
        for (int i = 0; i < queues.size(); i++) {
            // Get the current queue from the list
            FoodQueue queue = queues.get(i);
            // Get the length of the current queue
            int queueLength = queue.getQueueLength();
            // Check if the current queue is shorter than the previous shortest queue
            if (queueLength < minQueueLength) {
                // Update the shortest queue length with the current queue length
                minQueueLength = queueLength;
                // Update the shortest queue index with the current index
                minQueueIndex = i;
            }
        }


        boolean added = false; // a flag to indicate if the customer is added
        int count = 0; // a counter to keep track of how many queues are checked
        while (!added && count < queues.size()) {
            FoodQueue selectedQueue = queues.get(minQueueIndex);
            if (selectedQueue.addCustomer(customer)) {
                System.out.println("Customer '" + customer.getFirstName() + " " + customer.getLastName() + " " + "'added to Queue " + (minQueueIndex + 1) + ".");
                added = true;
            } else {
                //Reference : https://www.geeksforgeeks.org/introduction-to-circular-queue/
                minQueueIndex = (minQueueIndex + 1) % queues.size(); // move to the next queue in circular order
                count++; // increment the counter
            }
        }

        if (!added) {
            if (!(waitingQueue.isQueueFull())) {
                waitingQueue.addCustomer(customer);
                System.out.println("All the cashier queues are full. Customer '" + customer.getFirstName() + " " + customer.getLastName() + " " + "'added to Waiting List.");
            } else {
                System.out.println("Waiting List is full.");
            }
        }

    }

    public void removeCustomerFromQueue(int cashierIdx, int customerIdx) {
        int actualCashierIdx = cashierIdx - 1;
        int actualCustomerIdx = customerIdx - 1;
        if (actualCashierIdx >= 0 && actualCashierIdx < queues.size()) {
            FoodQueue queue = queues.get(actualCashierIdx);
            Customer customer = queue.removeCustomer(actualCustomerIdx);
            if (customer != null) {
                System.out.println("Customer '" + customer.getFirstName() + " " + customer.getLastName() + "' removed from Queue " + (cashierIdx) + ".");
                if (!waitingQueue.isQueueEmpty()) {
                    Customer nextCustomer = waitingQueue.removeCustomer(0);
                    if (nextCustomer != null) {
                        queues.get(actualCashierIdx).addCustomer(nextCustomer);
                        System.out.println("Customer '" + nextCustomer.getFirstName() + " " + nextCustomer.getLastName() + " " + "'moved from Waiting List to Queue " + cashierIdx + ".");
                    }
                }
            } else {
                System.out.println("Invalid customer index in Queue " + (cashierIdx) + ".");
            }
        } else {
            System.out.println("Invalid queue index.");
        }

    }

    public void removeServedCustomerFromQueue(int cashierId) {
        int actualCashierIndex = cashierId - 1;
        if (actualCashierIndex >= 0 && actualCashierIndex < queues.size()) {

            FoodQueue queue = queues.get(actualCashierIndex);
            Customer customer = queue.removeServedCustomer(0);
            if (customer != null) {
                if (!(currentBurgerStock < customer.burgersRequired)) {
                    currentBurgerStock -= customer.getBurgersRequired();
                    if (currentBurgerStock <= 10) {
                        System.out.println("Warning: Low stock!");
                    }

                    switch (cashierId) {
                        case 1 -> firstQueueIncome += customer.getBurgersRequired() * 650;
                        case 2 -> secondQueueIncome += customer.getBurgersRequired() * 650;
                        default -> thirdQueueIncome += customer.getBurgersRequired() * 650;
                    }
                    System.out.println("Customer '" + customer.getFirstName() + " " + customer.getLastName() + "' served and removed from the queue " + cashierId);
                    if (!waitingQueue.isQueueEmpty()) {
                        Customer nextCustomer = waitingQueue.removeCustomer(0);
                        if (nextCustomer != null) {
                            queues.get(actualCashierIndex).addCustomer(nextCustomer);
                            System.out.println("Customer '" + nextCustomer.getFirstName() + " " + nextCustomer.getLastName() + " " + "'moved from Waiting List to Queue " + cashierId + ".");
                        } else {
                            System.out.println("No customers in Queue " + cashierId + ".");
                        }
                    }
                } else {
                    System.out.println("burger stock is not sufficient. add burgers to serve customers.");
                }
            } else {
                System.out.println("No customers to serve.");
            }
        }
    }

    public void viewCustomersSortedAlphabetically() {
        List<Customer> allCustomers = new ArrayList<>();
        for (FoodQueue queue : queues) {
            allCustomers.addAll(queue.getCustomers());
        }
        // Insertion sort algorithm
        // Reference :https://www.javatpoint.com/insertion-sort-in-java
        for (int i = 1; i < allCustomers.size(); i++) {
            Customer key = allCustomers.get(i);
            int j = i - 1;
            // Compare the first and last names of the customers
            while (j >= 0 && (allCustomers.get(j).getFirstName().compareTo(key.getFirstName()) > 0 ||
                    (allCustomers.get(j).getFirstName().equals(key.getFirstName()) &&
                            allCustomers.get(j).getLastName().compareTo(key.getLastName()) > 0))) {
                // Swap the customers if they are not in alphabetical order
                allCustomers.set(j + 1, allCustomers.get(j));
                j = j - 1;
            }
            allCustomers.set(j + 1, key);
        }
        System.out.println("Customers Sorted in alphabetical order:");
        for (Customer customer : allCustomers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
        }
    }
    // Reference for file handling : https://www.w3schools.com/java/java_files.asp
    // Reference  for file handling :https://docs.oracle.com/javase/tutorial/essential/io/file.html
    public void storeProgramData() {
        try {
            FileWriter fileWriter = new FileWriter("foodCenterProgramData.txt");
            int lineCount = 1;
            // Loop through each cashier queue
            for (FoodQueue queue : queues) {
                int index = 1;
                fileWriter.write("Cashier queue: " + lineCount);
                for (Customer customer : queue.getCustomers()) {
                    if (!(customer == null)) {
                        fileWriter.write("\n " + "\t Queue -> position " + index + " " + customer.getFirstName() + "  " + (customer.getLastName()) + " has ordered " + (customer.getBurgersRequired()) + " burgers.");
                        index++;
                    }
                }
                lineCount++;
                fileWriter.write("\n");
            }


            fileWriter.close();

            System.out.println("\nProgram data has been stored in the file 'foodCenterProgramData.txt' successfully.");

        } catch (IOException exception) {
            System.out.println("Something went wrong with storing program data!");
        }

    }

    public void loadProgramData() {
        try {
            File fileInput = new File("foodCenterProgramData.txt");
            //Reading file with scanner object
            Scanner readFile = new Scanner(fileInput);

            String fileLine;
            while (readFile.hasNext()) {
                fileLine = readFile.nextLine();
                System.out.println(fileLine);

            }
            readFile.close();
            System.out.println("\nProgram data has been loaded from the file 'foodCenterProgramData.txt' successfully.");
        } catch (Exception exception) {
            System.out.println("Something went wrong with loading program data!");
        }
    }

    public void viewRemainingBurgerStock() {
        System.out.println("Remaining burgers in stock: " + (currentBurgerStock));
        if (currentBurgerStock <= 10) {
            System.out.println("Warning: Low stock!");
        }
    }

    public void addBurgersToStock(int quantity) {
        currentBurgerStock += quantity;
        System.out.println(quantity + " burgers added to stock.");
    }

    public void printBurgerIncome() {
        int[] income = {firstQueueIncome, secondQueueIncome, thirdQueueIncome};
        for (int i = 0; i < income.length; i++) System.out.println("Income from the Queue " + (i + 1) + " : Rs." + income[i]);
        System.out.println("\n total income from all the queues : Rs." + (firstQueueIncome + secondQueueIncome + thirdQueueIncome));
    }

    public static Customer searchCustomerByName(String customerName) {
        for (FoodQueue queue : queues) {
            for (Customer customer : queue.getCustomers()) {
                if (!(customer == null)) {
                    if (customer.getFirstName().equalsIgnoreCase(customerName) || customer.getLastName().equalsIgnoreCase(customerName)) {
                        return customer;
                    }
                }
            }
            for (Customer customer : waitingQueue.getCustomers()) {
                if (!(customer == null)) {
                    if (customer.getFirstName().equalsIgnoreCase(customerName) || customer.getLastName().equalsIgnoreCase(customerName)) {
                        return customer;
                    }
                }
            }
        }
        return null;
    }

    public static boolean checkFoodQueues() {
        return queues == null;
    }

    public static List<Customer> getQueueCustomers(int queueNumber){
        if (queueNumber == 0) {
            return waitingQueue.getCustomers();
        } else {
            return queues.get(queueNumber - 1).getCustomers();
        }
    }

    public static List<Customer> getAllCustomers(){
        List<Customer> allCustomers = new ArrayList<>();
        for (FoodQueue queue:queues) {
                allCustomers.addAll(queue.getCustomers());
            }
        return allCustomers;
        }
}
