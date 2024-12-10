package application;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Estimator {

    @FXML
    private TableView<BudgetItem> budgetTable;
    
    @FXML
    private TableView<IncomeItem> incomeTable;

    private final ObservableList<IncomeItem> incomeItems = FXCollections.observableArrayList();
    

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField itemAmountField;

    @FXML
    private TextField incomeDateField;
    

    @FXML
    private TextField incomeAmountField;

    @FXML
    private TextField currentBalanceField;

    @FXML
    private TextField totalWithdrawalsField;

    @FXML
    private TextField totalDepositsField;

    @FXML
    private TextField endingBalanceField;

    private final ObservableList<BudgetItem> budgetItems = FXCollections.observableArrayList();
    
    private Stage stage;
	private Scene scene;

	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {
	    // Set up budget table columns
	    TableColumn<BudgetItem, String> itemColumn = new TableColumn<>("Item");
	    itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

	    TableColumn<BudgetItem, Double> amountColumn = new TableColumn<>("Amount ($)");
	    amountColumn.setCellValueFactory(new PropertyValueFactory<>("itemAmount"));

	    budgetTable.getColumns().addAll(itemColumn, amountColumn);
	    budgetTable.setItems(budgetItems);

	    // Set up income table columns
	    TableColumn<IncomeItem, String> incomeDateColumn = new TableColumn<>("Date");
	    incomeDateColumn.setCellValueFactory(new PropertyValueFactory<>("incomeDate"));

	    TableColumn<IncomeItem, Double> incomeAmountColumn = new TableColumn<>("Amount ($)");
	    incomeAmountColumn.setCellValueFactory(new PropertyValueFactory<>("incomeAmount"));

	    incomeTable.getColumns().addAll(incomeDateColumn, incomeAmountColumn);
	    incomeTable.setItems(incomeItems);

	    // Initialize fields
	    currentBalanceField.setText("0.00");
	    endingBalanceField.setText("0.00");

	    // Recalculate ending balance on load
	    calculateEndingBalance();

	    // Add listener to currentBalanceField
	    currentBalanceField.textProperty().addListener((observable, oldValue, newValue) -> {
	        calculateEndingBalance();
	    });
	}

    
	@FXML
	public void addIncome(ActionEvent event) {
	    String incomeDate = incomeDateField.getText();
	    String incomeAmountText = incomeAmountField.getText();

	    if (incomeDate.isEmpty() || incomeAmountText.isEmpty()) {
	        showAlert("Input Error", "Please fill out both income date and amount.");
	        return;
	    }

	    try {
	        double incomeAmount = Double.parseDouble(incomeAmountText);
	        incomeItems.add(new IncomeItem(incomeDate, incomeAmount));
	        incomeDateField.clear();
	        incomeAmountField.clear();
	        calculateTotals(); // Update totals
	    } catch (NumberFormatException e) {
	        showAlert("Input Error", "Income amount must be a valid number.");
	    }
	    calculateTotals(); 
	}

    
    @FXML
    public void addItem(ActionEvent event) {
        String itemName = itemNameField.getText();
        String itemAmountText = itemAmountField.getText();

        if (itemName.isEmpty() || itemAmountText.isEmpty()) {
            showAlert("Input Error", "Please fill out both item name and amount.");
            return;
        }

        try {
            double itemAmount = Double.parseDouble(itemAmountText);
            budgetItems.add(new BudgetItem(itemName, itemAmount));
            itemNameField.clear();
            itemAmountField.clear();
            calculateTotals(); // Update totals
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Amount must be a valid number.");
        }
        calculateTotals(); 
    }

    
    private void calculateTotals() {
        // Calculate total withdrawals
        double totalWithdrawals = budgetItems.stream()
            .mapToDouble(BudgetItem::getItemAmount)
            .sum();
        totalWithdrawalsField.setText(String.format("%.2f", totalWithdrawals));

        // Calculate total deposits
        double totalDeposits = incomeItems.stream()
            .mapToDouble(IncomeItem::getIncomeAmount)
            .sum();
        totalDepositsField.setText(String.format("%.2f", totalDeposits));

        // Recalculate ending balance
        calculateEndingBalance();
    }

    
    private void calculateEndingBalance() {
        try {
            // Retrieve and parse the current balance
            double currentBalance = currentBalanceField.getText().isEmpty() 
                ? 0.0 
                : Double.parseDouble(currentBalanceField.getText());

            // Calculate totals
            double totalWithdrawals = budgetItems.stream()
                .mapToDouble(BudgetItem::getItemAmount)
                .sum();

            double totalDeposits = incomeItems.stream()
                .mapToDouble(IncomeItem::getIncomeAmount)
                .sum();

            // Calculate ending balance
            double endingBalance = currentBalance + totalDeposits - totalWithdrawals;

            // Update the ending balance field
            endingBalanceField.setText(String.format("%.2f", endingBalance));
        } catch (NumberFormatException e) {
            // Display error if the user input is invalid
            showAlert("Input Error", "Please enter a valid number for the current balance.");
        }
    }




    @FXML
 	public void switchToHome(ActionEvent event) throws IOException {
 		
 		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);
 		stage.show();
 		
 	} // end swtichToHome
 	
 	// The following method controls the 'List' button
 	public void switchToList(ActionEvent event) throws IOException {
 		
 		Parent root = FXMLLoader.load(getClass().getResource("List.fxml"));
 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);
 		stage.show();
 		
 	} // end switchToList
 	
 	// The following method controls the 'Group' button
 	public void switchToGroup(ActionEvent event) throws IOException {
 		
 		Parent root = FXMLLoader.load(getClass().getResource("Group.fxml"));
 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);
 		stage.show();
 		
 	} // end switchToGroup
 	
 	// The following method controls the 'Bills' button
 		public void switchToEstimator(ActionEvent event) throws IOException {
 			
 			Parent root = FXMLLoader.load(getClass().getResource("Estimator.fxml"));
 			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 			scene = new Scene(root);
 			stage.setScene(scene);
 			stage.show();
 			
 		} // end switchToBills
 	
 	// The following method controls the 'Bills' button
 	public void switchToBills(ActionEvent event) throws IOException {
 		
 		Parent root = FXMLLoader.load(getClass().getResource("Bills.fxml"));
 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);
 		stage.show();
 		
 	} // end switchToList
 	
 	// The following method controls the 'Savings' button
 	public void switchToSavings(ActionEvent event) throws IOException {
 		
 		Parent root = FXMLLoader.load(getClass().getResource("Savings.fxml"));
 		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
 		scene = new Scene(root);
 		stage.setScene(scene);
 		stage.show();
 		
 	} // end switchToList
 	
	public void switchToEstimator1(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Estimator.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToBills
 	

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
