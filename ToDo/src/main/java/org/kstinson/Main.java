/**
 * This program is a simple command-line To-Do List application.
 * Users can add, delete, and view their to-do items.
 * The program is modular, with separate classes for managing tasks and user interaction.
 * It now uses Hibernate to store, update, and delete data in a MySQL database.
 *
 * @author Kelly Stinson
 * @version 1.1
 * @since 2025-01-21
 */
package org.kstinson;

import java.util.Scanner;

/**
 * Main class with the command-line interface for the To-Do application.
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TodoApp todoApp = new TodoApp();

        while (true) {
            // Display menu options
            System.out.println("\nTo-Do List Application:");
            System.out.println("1. Add a To-Do item");
            System.out.println("2. Delete a To-Do item");
            System.out.println("3. View To-Do items");
            System.out.println("4. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("\nEnter the description of the To-Do item: ");
                    String description = scanner.nextLine();
                    todoApp.addTodoItem(description);
                    break;
                case 2:
                    System.out.print("\nEnter the ID of the To-Do item to delete: ");
                    int id = scanner.nextInt(); // Use direct ID lookup
                    todoApp.deleteTodoItem(id);
                    break;
                case 3:
                    todoApp.viewTodoItems();
                    break;
                case 4:
                    System.out.println("\nExiting the application. Goodbye!\n");
                    todoApp.close();
                    scanner.close();
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.\n");
            }
        }
    }
}