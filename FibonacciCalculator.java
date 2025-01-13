import java.util.Scanner;

public class FibonacciCalculator {

    // Recursive method to calculate the nth Fibonacci term
    public static int fibonacci(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    // Main method to call the Fibonacci method and print the result
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the term (n) for the Fibonacci sequence: ");
        int n = scanner.nextInt();
        int result = fibonacci(n);
        System.out.println("\nThe " + n + "th term of the Fibonacci sequence is " + result + ".");
        scanner.close();
    }
}
