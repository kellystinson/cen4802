package org.kstinson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.*;

/**
 * This class serves as the core of the To-Do application.
 * It provides functionalities to add, delete, and view to-do items,
 * storing them in a MySQL database using Hibernate.
 *
 * @author Kelly Stinson
 * @version 1.2
 * @since 2025-01-21
 */
class TodoApp {
    private static final Logger logger = Logger.getLogger(TodoApp.class.getName());
    private SessionFactory sessionFactory;
    private Properties dbProperties;

    static {
        try {
            LogManager.getLogManager().reset();

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
            
            FileHandler fileHandler = new FileHandler("todo-app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    /**
     * Default constructor for normal application usage.
     * Initializes a real Hibernate SessionFactory.
     */
    public TodoApp() {
        logger.info("Initializing To-Do Application and checking database connection...");

        // Configure Hibernate and map the TodoItem class
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(TodoItem.class);

        // Extract database connection properties from hibernate.cfg.xml
        dbProperties = configuration.getProperties();
        String dbUrl = dbProperties.getProperty("hibernate.connection.url");
        String dbUser = dbProperties.getProperty("hibernate.connection.username");
        String dbPassword = dbProperties.getProperty("hibernate.connection.password");

        // Check database connection
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            if (conn != null) {
                logger.info("Database connection successful.");
            } else {
                logger.severe("Database connection failed.");
            }
        } catch (SQLException e) {
            logger.severe("Error connecting to the database: " + e.getMessage());
        }

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
        logger.info("Attempting to add To-Do item: " + description);

        if (description == null || description.trim().isEmpty()) {
            logger.warning("Failed to add To-Do item: Description cannot be blank or only contain whitespace.");
            //throw new IllegalArgumentException("Description cannot be blank or only contain whitespace.");
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            TodoItem item = new TodoItem(description);
            session.merge(item);
            transaction.commit();
            logger.info("Successfully added To-Do item: " + description);
        } catch (Exception e) {
            transaction.rollback();
            logger.severe("Failed to add To-Do item: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Deletes a To-Do item from the database based on the given id.
     * @param id The ID of the item to delete.
     */
    public void deleteTodoItem(int id) {
        logger.info("Attempting to delete To-Do item with ID: " + id);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            TodoItem item = session.get(TodoItem.class, id);
            if (item != null) {
                session.remove(item);
                transaction.commit();
                logger.info("Successfully removed To-Do item: " + item.getDescription());
            } else {
                logger.warning("Invalid ID provided for deletion: " + id);
            }
        } catch (Exception e) {
            transaction.rollback();
            logger.severe("Failed to delete To-Do item: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Retrieves and displays all To-Do items from the database.
     */
    public void viewTodoItems() {
        logger.info("Fetching all To-Do items from database.");
        Session session = sessionFactory.openSession();
        List<TodoItem> todoList = session.createQuery("from TodoItem", TodoItem.class).list();

        session.close();

        if (todoList.isEmpty()) {
            logger.warning("No To-Do items available.");
        } else {
            logger.info("Fetched To-Do item(s): " + todoList.size());
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
        logger.info("Application closed and session factory shut down.");
    }
}
