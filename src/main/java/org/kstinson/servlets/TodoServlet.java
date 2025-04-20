package org.kstinson.servlets;

import org.kstinson.TodoApp;
import org.kstinson.TodoItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Main servlet for handling To-Do list operations.
 *
 * @author Kelly Stinson
 * @version 1.0
 * @date 2025-03-16
 */
@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
    private TodoApp todoApp;

    @Override
    public void init() throws ServletException {
        // Initialize the TodoApp instance and store it in the servlet context
        todoApp = new TodoApp();
        getServletContext().setAttribute("todoApp", todoApp);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        // Retrieve the list of to-do items
        List<TodoItem> todoList = todoApp.getTodoItems();

        // Store the to-do list in request scope
        request.setAttribute("todoList", todoList);

        // Forward request to the JSP page for rendering
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

