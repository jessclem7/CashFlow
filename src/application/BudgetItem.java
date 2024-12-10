package application;

public class BudgetItem {
    private final String itemName;
    private final double itemAmount;

    public BudgetItem(String itemName, double itemAmount) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemAmount() {
        return itemAmount;
    }
}
