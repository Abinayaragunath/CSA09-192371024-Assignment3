
import java.util.Scanner;

public class ATMWithoutExceptions {

    private static final int ACCOUNT_NUMBER = 192371024;
    private static double balance = 10000.00;

    public static int withdraw(int accountNumber, double amount) {

        if (accountNumber != ACCOUNT_NUMBER) {
            return -1;
        }

        if (amount <= 0) {
            return -2;
        }

        if (amount > balance) {
            return -3;
        }

        balance -= amount;
        return 0;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("   ATM WITHOUT EXCEPTIONS");
        System.out.println("================================");

        System.out.print("Enter account number: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Error: Enter a valid numeric account number.");
            scanner.close();
            return;
        }

        int accountNumber = scanner.nextInt();

        System.out.print("Enter withdrawal amount: ");

        if (!scanner.hasNextDouble()) {
            System.out.println("Error: Enter a valid numeric amount.");
            scanner.close();
            return;
        }

        double amount = scanner.nextDouble();

        int result = withdraw(accountNumber, amount);

        if (result == 0) {
            System.out.println("Withdrawal successful.");
            System.out.printf("Remaining balance: Rs. %.2f%n", balance);
        } else if (result == -1) {
            System.out.println("Error: Invalid account number.");
        } else if (result == -2) {
            System.out.println("Error: Withdrawal amount must be positive.");
        } else if (result == -3) {
            System.out.println("Error: Insufficient account balance.");
        }

        scanner.close();
        System.out.println("ATM session closed.");
    }
}
