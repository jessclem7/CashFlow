package application;

public class IncomeItem {
    private String incomeDate;
    private double incomeAmount;

    public IncomeItem(String incomeDate, double incomeAmount) {
        this.incomeDate = incomeDate;
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }
}
