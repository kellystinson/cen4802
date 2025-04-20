package org.kstinson.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.kstinson.TodoApp;

/**
 * Servlet to handle adding a new to-do item.
 *
 * @author Kelly Stinson
 * @version 1.0
 * @date 2025-03-16
 */
@WebServlet("/add")
public class AddTodoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String description = request.getParameter("description");
        TodoApp todoApp = (TodoApp) getServletContext().getAttribute("todoApp");

        if (todoApp == null) {
            todoApp = new TodoApp();
            getServletContext().setAttribute("todoApp", todoApp);
        }

        todoApp.addTodoItem(description);
        request.setAttribute("message", "To-Do item added: " + description);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

