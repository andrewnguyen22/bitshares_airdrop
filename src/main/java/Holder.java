public class Holder {
    private String name;
    private double value;
    private double new_balance;

    public Holder(String name, double value, double new_balance) {
        this.name = name;
        this.value = value;
        this.new_balance = new_balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getNew_balance() {
        return new_balance;
    }

    public void setNew_balance(double new_balance) {
        this.new_balance = new_balance;
    }
}
