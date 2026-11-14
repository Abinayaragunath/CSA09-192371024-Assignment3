
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATM {

    private static final int ACCOUNT_NUMBER = 192371024;
    private static double balance = 10000.00;

    // Custom unchecked exception
    static class InvalidWithdrawalException extends RuntimeException {
        InvalidWithdrawalException(String message) {
            super(message);
        }
    }

    // Custom unchecked exception
    static class InsufficientBalanceException extends RuntimeException {
        InsufficientBalanceException(String message) {
            super(message);
        }
    }

    public static void withdraw(int accountNumber, double amount)
            throws FileNotFoundException {

        try {
            // Simulate card-reader file validation
            File cardFile = new File("Input/card.txt");

            if (!cardFile.exists()) {
                throw new FileNotFoundException("Card reader file is missing.");
            }

            if (accountNumber != ACCOUNT_NUMBER) {
                throw new IllegalArgumentException("Invalid account number.");
            }

            if (amount <= 0) {
                throw new InvalidWithdrawalException(
                    "Withdrawal amount must be positive."
                );
            }

            if (amount > balance) {
                throw new InsufficientBalanceException(
                    "Insufficient account balance."
                );
            }

            balance -= amount;

            System.out.printf("Withdrawal successful: Rs. %.2f%n", amount);
            System.out.printf("Remaining balance: Rs. %.2f%n", balance);

        } finally {
            System.out.println("ATM transaction processing completed.");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("================================");
            System.out.println("       BANK ATM SIMULATION");
            System.out.println("================================");

            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();

            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();

            withdraw(accountNumber, amount);

        } catch (InputMismatchException e) {
            System.out.println("Input Error: Please enter numeric values.");

        } catch (InvalidWithdrawalException |
                 InsufficientBalanceException e) {
            System.out.println("Transaction Error: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("Account Error: " + e.getMessage());

        } catch (FileNotFoundException e) {
            System.out.println("File Error: " + e.getMessage());

        } finally {
            scanner.close();
            System.out.println("ATM session closed.");
        }
    }
}
