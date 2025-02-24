package org.kstinson;

import jakarta.persistence.*;

/**
 * Class representing a To-Do item.
 *
 * @author Kelly Stinson
 * @version 1.1
 * @since 2025-01-21
 */
@Entity
@Table(name = "todo_items")
class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private int id;

    @Column(name = "description", nullable = false) // Ensures column is not null
    private String description;

    public TodoItem() {}

    public TodoItem(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return id + ". " + description;
    }
}
