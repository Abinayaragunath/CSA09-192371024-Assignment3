
public class StackTraceDemo {

    static void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Withdrawal amount must be positive"
            );
        }
        System.out.println("Withdrawal successful");
    }

    public static void main(String[] args) {
        System.out.println("ATM Transaction Started");

        withdraw(-500);

        System.out.println("ATM Transaction Completed");
    }
}
