package org.kstinson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the TodoApp class using Mockito.
 */
class TodoAppTest {

    private TodoApp todoApp;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    void setUp() {

        // Mock Hibernate components
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        transaction = mock(Transaction.class);

        // Configure mocks
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);

        // Inject mocked SessionFactory into TodoApp
        todoApp = new TodoApp(sessionFactory);
    }

    @Test
    void testAddTodoItem() {
        // Mock session.merge() instead of session.save()
        when(session.merge(any(TodoItem.class))).thenReturn(new TodoItem("Buy groceries"));

        // Ensure transaction commits successfully
        doNothing().when(transaction).commit();

        // Execute the method
        todoApp.addTodoItem("Buy groceries");

        // Verify that session.merge() was called (instead of session.save())
        verify(session).merge(any(TodoItem.class));
        verify(transaction).commit();
    }

    @Test
    void testDeleteTodoItemValidId() {
        // Create a mock TodoItem
        TodoItem mockItem = new TodoItem("Sample Task");

        // Simulate session.get() returning the item
        when(session.get(TodoItem.class, 1)).thenReturn(mockItem);

        // Ensure remove() is executed properly
        doNothing().when(session).remove(mockItem);
        doNothing().when(transaction).commit();

        // Execute method
        todoApp.deleteTodoItem(1);

        // Verify correct interactions
        verify(session).get(TodoItem.class, 1);
        verify(session).remove(mockItem); // Ensure remove() happens
        verify(transaction).commit();
    }

    @Test
    void testDeleteTodoItemInvalidId() {
        when(session.get(TodoItem.class, 999)).thenReturn(null);

        todoApp.deleteTodoItem(999);

        verify(session, never()).delete(any());
        verify(transaction, never()).commit();
    }

    @Test
    void testViewTodoItems() {
        // Mock Query
        Query<TodoItem> mockQuery = mock(Query.class);
        List<TodoItem> mockList = List.of(new TodoItem("Task 1"), new TodoItem("Task 2"));

        // Configure session to return the mock query
        when(session.createQuery("from TodoItem", TodoItem.class)).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(mockList);

        // Execute the method
        todoApp.viewTodoItems();

        // Verify interactions
        verify(session).createQuery("from TodoItem", TodoItem.class);
        verify(mockQuery).list();
    }

    @AfterEach
    void tearDown() {
        todoApp.close();
    }
}
