package org.kstinson.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.kstinson.TodoApp;
import org.kstinson.TodoItem;

/**
 * Servlet to handle deleting a to-do item.
 *
 * @author Kelly Stinson
 * @version 1.0
 * @date 2025-03-16
 */
@WebServlet("/delete")
public class DeleteTodoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        TodoApp todoApp = (TodoApp) getServletContext().getAttribute("todoApp");
        String message;
        if (todoApp != null) {
            TodoItem item = todoApp.getTodoItemById(id);
            if (item != null) {
                todoApp.deleteTodoItem(id);
                message = "To-Do item removed: " + item.getDescription();
            } else {
                message = "Invalid ID. No item deleted.";
            }
        } else {
            message = "To-Do list is empty.";
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }
}
