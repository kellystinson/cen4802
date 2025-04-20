<!-- index.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  * JSP page to display the To-Do List with Bootstrap styling.
  *
  * @author Kelly Stinson
  * @version 1.0
  * @date 2025-03-16
  --%>
<%-- Retrieve the to-do list from the request attribute --%>
<%@ page import="org.kstinson.TodoItem, java.util.List" %>
<%
    List<TodoItem> todoList = (List<TodoItem>) request.getAttribute("todoList");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>To-Do List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2 class="text-center">To-Do List</h2>

<div class="row justify-content-center">
    <div class="col-auto">

        <%-- Display success or error messages --%>
        <% if (message != null) { %>
        <div class="alert alert-info"><%= message %></div>
        <% } %>

        <table class="table table-bordered table-hover table-responsive">
            <thead class="table-dark">
            <tr>
                <th scope="col" class="col-md-1 text-center">#</th>
                <th scope="col" class="col-md-9">Task</th>
                <th scope="col" class="col-md-2 text-center">Actions</th>
            </tr>
            </thead>
            <tbody>
            <%-- Loop through todo items --%>
            <jsp:useBean id="todoApp" class="org.kstinson.TodoApp" scope="application"/>
            <% for (org.kstinson.TodoItem item : todoApp.getTodoItems()) { %>
            <tr>
                <th scope="col" class="text-center"><%= item.getId() %></th>
                <td><%= item.getDescription() %></td>
                <td class="text-center">
                    <form action="delete" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= item.getId() %>">
                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <form action="add" method="post" class="mt-3">
            <div class="input-group">
                <input type="text" name="description" class="form-control" placeholder="New Task" required>
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>