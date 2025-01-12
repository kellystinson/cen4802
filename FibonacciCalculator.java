import java.util.Scanner;

/**
 * A class to calculate the nth term in the Fibonacci sequence using recursion.
 * Includes a main method to allow user input for the term number.
 *
 * @author Kelly Stinson
 * @version 1.0
 * @since 2025-01-12
 *
 * Includes a main method to allow user input for the term number.
 */
public class FibonacciCalculator {

    /**
     * Recursive method to calculate the nth Fibonacci term.
     *
     * @param n the term number in the Fibonacci sequence (non-negative integer)
     * @return the nth Fibonacci term
     */
    public static int fibonacci(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    /**
     * Main method to prompt the user for a term number and print the corresponding Fibonacci term.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the term (n) for the Fibonacci sequence: ");
        int n = scanner.nextInt();
        int result = fibonacci(n);
        System.out.println("The " + n + "th term of the Fibonacci sequence is " + result + ".");
        scanner.close();
    }
}
