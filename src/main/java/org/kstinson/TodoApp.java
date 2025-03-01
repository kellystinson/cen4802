package org.kstinson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * This class serves as the core of the To-Do application.
 * It provides functionalities to add, delete, and view to-do items,
 * storing them in a MySQL database using Hibernate.
 *
 * @author Kelly Stinson
 * @version 1.1
 * @since 2025-01-21
 */
import java.util.List;

class TodoApp {

    private SessionFactory sessionFactory;

    /**
     * Default constructor for normal application usage.
     * Initializes a real Hibernate SessionFactory.
     */
     public TodoApp() {
        // Configure Hibernate and map the TodoItem class
         Configuration configuration = new Configuration();
         configuration.configure("hibernate.cfg.xml");
         configuration.addAnnotatedClass(TodoItem.class);
         sessionFactory = configuration.buildSessionFactory();
    }

    /**
     * Constructor for dependency injection, used in unit tests with a mock SessionFactory.
     * @param sessionFactory The session factory to be injected.
     */
    public TodoApp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Adds a new To-Do item to the database.
     * @param description The description of the To-Do item.
     */
    public void addTodoItem(String description) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TodoItem item = new TodoItem(description);

        session.merge(item);
        transaction.commit();
        session.close();

        System.out.println("\nTo-Do item added: " + description + "\n");
    }

    /**
     * Deletes a To-Do item from the database based on the given id.
     * @param id The ID of the item to delete.
     */
    public void deleteTodoItem(int id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TodoItem item = session.get(TodoItem.class, id);

        if (item != null) {
            session.remove(item);
            transaction.commit();
            System.out.println("\nTo-Do item removed: " + item.getDescription() + "\n");
        } else {
            System.out.println("\nInvalid ID. No item deleted.\n");
        }
        session.close();
    }

    /**
     * Retrieves and displays all To-Do items from the database.
     */
    public void viewTodoItems() {

        Session session = sessionFactory.openSession();
        List<TodoItem> todoList = session.createQuery("from TodoItem", TodoItem.class).list();

        session.close();

        if (todoList.isEmpty()) {
            System.out.println("\nNo To-Do items available.\n");
        } else {
            System.out.println("\nTo-Do List:");
            for (TodoItem item : todoList) {
                System.out.println(item);
            }
            System.out.println();
        }
    }

    /**
     * Closes the Hibernate session factory on exit.
     */
    public void close() {
        sessionFactory.close();
    }
}
