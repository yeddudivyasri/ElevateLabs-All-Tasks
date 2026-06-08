
import java.util.Scanner;

/**
 * Java Console Calculator
 * Supports: addition, subtraction, multiplication, division
 * Features: loop for multiple calculations, divide-by-zero handling
 */
public class Calculator {

    // ──────────────────────────────────────────────
    // Arithmetic Methods
    // ──────────────────────────────────────────────

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divides two numbers.
     * Throws ArithmeticException if divisor is zero.
     */
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Error: Division by zero is not allowed.");
        }
        return a / b;
    }

    // ──────────────────────────────────────────────
    // Display Helpers
    // ──────────────────────────────────────────────

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      Java Console Calculator     ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("----------------------------------");
        System.out.println("  Select an operation:");
        System.out.println("  1. Addition       (+)");
        System.out.println("  2. Subtraction    (-)");
        System.out.println("  3. Multiplication (*)");
        System.out.println("  4. Division       (/)");
        System.out.println("  5. Exit");
        System.out.println("----------------------------------");
        System.out.print("  Enter choice (1-5): ");
    }

    // ──────────────────────────────────────────────
    // Input Helper — safely reads a double
    // ──────────────────────────────────────────────

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.println("  [!] Invalid input. Please enter a number.");
                scanner.next(); // discard invalid token
            }
        }
    }

    // ──────────────────────────────────────────────
    // Main
    // ──────────────────────────────────────────────

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printBanner();

        boolean running = true;

        while (running) {
            printMenu();

            // Read menu choice
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("  [!] Invalid choice. Enter a number from 1 to 5.\n");
                scanner.next();
                continue;
            }

            if (choice == 5) {
                System.out.println("\n  Goodbye! Thanks for using the Calculator. 👋");
                running = false;
                break;
            }

            if (choice < 1 || choice > 4) {
                System.out.println("  [!] Invalid choice. Please enter 1-5.\n");
                continue;
            }

            // Read operands
            double num1 = readDouble(scanner, "  Enter first number  : ");
            double num2 = readDouble(scanner, "  Enter second number : ");

            double result = 0;
            String operationSymbol = "";

            try {
                switch (choice) {
                    case 1:
                        result = add(num1, num2);
                        operationSymbol = "+";
                        break;
                    case 2:
                        result = subtract(num1, num2);
                        operationSymbol = "-";
                        break;
                    case 3:
                        result = multiply(num1, num2);
                        operationSymbol = "×";
                        break;
                    case 4:
                        result = divide(num1, num2);
                        operationSymbol = "÷";
                        break;
                }

                // Format: remove trailing .0 for whole numbers
                String resultStr = (result == (long) result)
                        ? String.valueOf((long) result)
                        : String.valueOf(result);

                System.out.printf("%n  ✔  %.6g %s %.6g = %s%n%n",
                        num1, operationSymbol, num2, resultStr);

            } catch (ArithmeticException e) {
                System.out.println("\n  [!] " + e.getMessage() + "\n");
            }
        }

        scanner.close();
    }
}