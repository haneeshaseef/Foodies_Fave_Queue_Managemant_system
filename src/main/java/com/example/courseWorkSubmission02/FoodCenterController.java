package com.example.courseWorkSubmission02;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FoodCenterController {

    @FXML
    private TextField searchField;

    @FXML
    private Button waitingQueueView, foodQueueView, allQueuesView;

    @FXML
    private TableView<Customer> firstQueueView , secondQueueView, thirdQueueView,waitingQueueCustomerStatus,viewAllQueues,waitingQueueStatus;

    @FXML
    public void handleSearchButton() {
         String customerName = searchField.getText();
         if(customerName.isEmpty()){
             Alert alert = new Alert(Alert.AlertType.ERROR); // Create an error alert
             alert.setContentText("Please enter a customer name to search."); // Set the content of the alert
             alert.showAndWait();
         }else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             if (!isAllQueuesEmpty()) {
                 Customer customer = FoodCenter.searchCustomerByName(customerName);
                 if (customer == null) {
                     alert.setContentText("Customer not found in any queue with this name."); // Set the content of the alert
                 } else {
                     alert.setContentText(customer.getFirstName() + " " + customer.getLastName() + " has ordered " + customer.burgersRequired); // Set the content of the alert to show the customer details
                 }
                 alert.showAndWait();
             }else{
                 alert.setContentText("All the food Queues are empty");
                 alert.showAndWait();
             }
             searchField.clear();
        }
    }

    @FXML
    public void handleFoodQueueView() throws IOException {
        foodQueueView.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("foodQueue-view.fxml")));
        primaryStage.setTitle("View Food queue status");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void handleWaitingQueueView() throws IOException {
        waitingQueueView.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("waitingQueue-view.fxml")));
        primaryStage.setTitle("View Waiting Queue status");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void handleAllQueuesView() throws IOException {
        allQueuesView.getScene().getWindow();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("allQueues-view.fxml")));
        primaryStage.setTitle("View All Queues status");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private void setQueueView(TableView<Customer> queueView, List<Customer> queueCustomers, String queueName) {
        // Create three TableColumns for the first name, last name, and required burgers
        TableColumn <Customer , String> firstCol = new TableColumn<>("First Name");
        TableColumn <Customer , String> secondCol = new TableColumn<>("Last Name");
        TableColumn <Customer , Integer> thirdCol = new TableColumn<>("Required Burgers");

        // Set the cell value factories for each column using the Customer properties
        firstCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        secondCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("BurgersRequired"));

        // Add the columns to the TableView
        queueView.getColumns().addAll(firstCol,secondCol,thirdCol);
        // Create an ObservableList from the List of Customers and set it as the TableView items
        ObservableList<Customer> queueObservableList = FXCollections.observableArrayList(queueCustomers);
        queueView.setItems(queueObservableList);

        // Set the title of the TableView using the queue name
        queueView.setId(queueName + " Queue");
    }

    @FXML
    public void updateFoodQueueView(){
        List<Customer> firstQueueCustomers = FoodCenter.getQueueCustomers(1);
        List<Customer> secondQueueCustomers = FoodCenter.getQueueCustomers(2);
        List<Customer> thirdQueueCustomers = FoodCenter.getQueueCustomers(3);

        setQueueView(firstQueueView, firstQueueCustomers, "First");
        setQueueView(secondQueueView, secondQueueCustomers, "Second");
        setQueueView(thirdQueueView, thirdQueueCustomers, "Third");
    }

    @FXML
    public void updateWaitingQueueView(){
        List<Customer> waitingQueueCustomers = FoodCenter.getQueueCustomers(0);
        setQueueView(waitingQueueCustomerStatus,waitingQueueCustomers,"Waiting");

    }

    @FXML
    public void updateAllQueuesView() {
        List<Customer> allFoodQueueCustomers = FoodCenter.getAllCustomers();
        List<Customer> waitingQueueCustomers = FoodCenter.getQueueCustomers(0);

        setQueueView(viewAllQueues,allFoodQueueCustomers,"Food");
        setQueueView(waitingQueueStatus,waitingQueueCustomers,"waitingCustomers");

    }

    private boolean isAllQueuesEmpty() {
         return FoodCenter.checkFoodQueues();
    }
}
